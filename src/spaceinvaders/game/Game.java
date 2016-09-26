package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import java.util.ArrayList;

import spaceinvaders.game.display.Display;
import spaceinvaders.game.teclado.TratadorDeEventos;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 21:03:13
 * @languageOfComments: Portuguese
 */
public class Game {

    private BufferStrategy estrategia;
    private boolean jogoRodando;
    private ArrayList entidades;
    private ArrayList removeLista;
    private Entidade nave;
    private double velocidadeMovimento;
    private long ultimoTiro;
    private long intervaloDeTiro;
    private int allienCont;

    private String mensagem;
    private boolean esperarPorTeclaPress;
    private boolean teclaEsquerda;
    private boolean teclaDireita;
    private boolean teclaTiro;
    private boolean logicaNecessariaNesteLoop;

    public Game() {
        jogoRodando = true;
        entidades = new ArrayList();
        removeLista = new ArrayList();
        velocidadeMovimento = 300;
        ultimoTiro = 0;
        intervaloDeTiro = 500;

        mensagem = "";
        esperarPorTeclaPress = true;
        teclaEsquerda = false;
        teclaDireita = false;
        teclaTiro = false;
        logicaNecessariaNesteLoop = false;

        Display tela = new Display("Space Invaders", 600, 800);

        tela.addKeyListener(new TratadorDeEventos());//Substituir o nome KeyInputHandler por PT-BR
        tela.requestFocus();
        tela.setIgnoreRepaint(true);
        tela.createBufferStrategy(2);
        estrategia = tela.getBufferStrategy();

        initEntidades();
    }

    private void startGame() {
        entidades.clear();
        initEntidades();

        teclaEsquerda = false;
        teclaDireita = false;
        teclaTiro = false;
    }

    private void initEntidades() {
        int linha, col;
        nave = new ShipEntity(this, "sprites/nave.gif");
        entidades.add(nave);

        allienCont = 0;
        for (linha = 0; linha < 5; linha++) {
            for (col = 0; col < 5; col++) {
                Entidade alien = new AllienEntity("this", "sprites/allien.gif");
                entidades.add(alien);
                allienCont++;
            }
        }
    }

    public void atualizaLogica() {
        logicaNecessariaNesteLoop = true;
    }

    public void removeEntidade(Entidade entidade) {
        removeLista.add(entidade);
    }

    public void notificaMorte() {
        mensagem = "Ahh não! Eles te pegaram, quer tentar de novo?";
        esperarPorTeclaPress = true;
    }

    public void notificaVitoria() {
        mensagem = "Bom trabalho! Você Ganhou!";
    }

    public void notificaAllienMorto() {
        int i;
        allienCont--;
        if (allienCont == 0) {
            notificaVitoria();
        }

        for (i = 0; i < entidades.size(); i++) {
            Entidade entidade = (Entidade) entidades.get(i);

            if (entidade instanceof AllienEntity) {
                //aumenta em 2%
                entidade.setMovimentoHorizontal(entidade.getMovimentoHorizontal() * 1.02);
            }
        }
    }

    public void tentaAtirar() {
        if (System.currentTimeMillis() - ultimoTiro < intervaloDeTiro) {
            return;
        }

        ultimoTiro = System.currentTimeMillis();
        ShotEntity tiro = new ShotEntity(this, "sprites/tiro.gif", nave.getX());
        entidades.add(tiro);
    }

    public void gameLoop() {
        int i, j;
        long tempoUltimoLoop = System.currentTimeMillis();
        long delta;

        while (jogoRodando) {
            delta = System.currentTimeMillis() - tempoUltimoLoop;
            tempoUltimoLoop = System.currentTimeMillis();

            Graphics2D g = (Graphics2D) estrategia.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            if (!esperarPorTeclaPress) {
                for (i = 0; i < entidades.size(); i++) {
                    Entidade entidade = (Entidade) entidades.get(i);
                    entidade.move(delta);
                }
            }

            for (i = 0; i < entidades.size(); i++) {
                Entidade entidade = (Entidade) entidades.get(i);
                entidade.desenha(g);
            }

            for (i = 0; i < entidades.size(); i++) {
                for (j = i + 1; j < entidades.size(); j++) {
                    Entidade eu = (Entidade) entidades.get(i);
                    Entidade ele = (Entidade) entidades.get(j);
                    if (eu.colideCom(ele)) {
                        eu.colidiuCom(ele);
                        ele.colidiuCom(eu);
                    }
                }
            }

            entidades.removeAll(removeLista);
            removeLista.clear();

            if (logicaNecessariaNesteLoop) {
                for (i = 0; i < entidades.size(); i++) {
                    Entidade entidade = (Entidade) entidades.get(i);
                    entidade.facaLogica();
                }
                logicaNecessariaNesteLoop = false;
            }

            if (esperarPorTeclaPress) {
                g.setColor(Color.white);
                g.drawString(mensagem, (800 - g.getFontMetrics().stringWidth(mensagem)) / 2, 250);
                g.drawString("Press any key", (800 - g.getFontMetrics().stringWidth("Press any key")) / 2, 300);
            }

            g.dispose();
            estrategia.show();

            nave.setMovimentoHorizontal(0);
            if ((teclaEsquerda) && (!teclaDireita)) {
                nave.setMovimentoHorizontal(-velocidadeMovimento);
            } else if ((!teclaEsquerda) && (teclaDireita)) {
                nave.setMovimentoHorizontal(velocidadeMovimento);
            }

            if (teclaTiro) {
                tentaAtirar();
            }
            
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }
}
