package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;//Se o fechar janela da classe display
import java.awt.event.WindowEvent;//não funcionar, usar métodos desses pacotes

import java.util.ArrayList;

import spaceinvaders.game.display.Display;
import spaceinvaders.game.entidades.Entidade;
import spaceinvaders.game.teclado.TratadorDeEventos;
import spaceinvaders.game.entidades.EntidadeTiro;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 21:03:13
 * @languageOfComments: Portuguese
 */
public class Game {

    private BufferStrategy estrategia;//
    private boolean jogoRodando;
    private ArrayList entidades;
    private ArrayList removeLista;
    private Entidade nave;//
    private double velocidadeDeMovimento;
    private long ultimoTiro;
    private long intervaloDeTiro;
    private int allienCont;

    private String mensagem;
    private boolean esperarPorPressionarTecla;
    private boolean esquerdaPressionada;
    private boolean direitaPressionada;
    private boolean tiroPressionado;
    private boolean logicaNecessariaNesteLoop;

    public Game() {
        jogoRodando = true;
        entidades = new ArrayList();
        removeLista = new ArrayList();
        velocidadeDeMovimento = 300;
        ultimoTiro = 0;
        intervaloDeTiro = 500;

        mensagem = "";
        esperarPorPressionarTecla = true;
        esquerdaPressionada = false;
        direitaPressionada = false;
        tiroPressionado = false;
        logicaNecessariaNesteLoop = false;

        Display tela = new Display("Space Invaders", 600, 800);

        tela.addKeyListener(new TratadorDeEventos(this));
        tela.requestFocus();//Não sei pra que serve
        tela.setIgnoreRepaint(true);//Não sei pra que serve
        tela.createBufferStrategy(2);//Não sei pra que serve
        estrategia = tela.getBufferStrategy();//Não sei pra que serve

        initEntidades();
    }

    public void startGame() {
        entidades.clear();
        initEntidades();

        esquerdaPressionada = false;
        direitaPressionada = false;
        tiroPressionado = false;
    }

    private void initEntidades() {
        int linha, col;
        nave = new EntidadeNave(this, "game/sprites/nave.gif");
        entidades.add(nave);

        allienCont = 0;
        for (linha = 0; linha < 5; linha++) {
            for (col = 0; col < 5; col++) {
                Entidade alien = new EntidadeAlien("this", "game/sprites/allien.gif");
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
        esperarPorPressionarTecla = true;
    }

    public void notificaVitoria() {
        mensagem = "Bom trabalho! Você Ganhou!";
    }

    public void notificaAlienMorto() {
        int i;
        allienCont--;
        if (allienCont == 0) {
            notificaVitoria();
        }

        for (i = 0; i < entidades.size(); i++) {
            Entidade entidade = (Entidade) entidades.get(i);

            if (entidade instanceof EntidadeAlien) {
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
        EntidadeTiro tiro = new EntidadeTiro(this, "game/sprites/tiro.gif", nave.getX());
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

            if (!esperarPorPressionarTecla) {
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
                    entidade.fazLogica();
                }
                logicaNecessariaNesteLoop = false;
            }

            if (esperarPorPressionarTecla) {
                g.setColor(Color.white);
                g.drawString(mensagem, (800 - g.getFontMetrics().stringWidth(mensagem)) / 2, 250);
                g.drawString("Pressione qualquer Tecla", (800 - g.getFontMetrics().stringWidth("Pressione Qualquer Tecla")) / 2, 300);
            }

            g.dispose();
            estrategia.show();

            nave.setMovimentoHorizontal(0);
            if ((esquerdaPressionada) && (!direitaPressionada)) {
                nave.setMovimentoHorizontal(-velocidadeDeMovimento);
            } else if ((!esquerdaPressionada) && (direitaPressionada)) {
                nave.setMovimentoHorizontal(velocidadeDeMovimento);
            }

            if (tiroPressionado) {
                tentaAtirar();
            }

            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    public void setJogoRodando(boolean jogoRodando) {
        this.jogoRodando = jogoRodando;
    }

    public boolean getJogoRodando() {
        return jogoRodando;
    }

    public void setEsperarPorPressionarTecla(boolean esperarPorPressionarTecla) {
        this.esperarPorPressionarTecla = esperarPorPressionarTecla;
    }

    public boolean getEsperarPorPressionarTecla() {
        return esperarPorPressionarTecla;
    }

    public void setEsquerdaPressionada(boolean esquerdaPressionada) {
        this.esquerdaPressionada = esquerdaPressionada;
    }

    public boolean getEsquerdaPressionada() {
        return esquerdaPressionada;
    }

    public void setDireitaPressionada(boolean direitaPressionada) {
        this.direitaPressionada = direitaPressionada;
    }

    public boolean getDireitaPressionada() {
        return direitaPressionada;
    }

    public void setTiroPressionado(boolean tiroPressionado) {
        this.tiroPressionado = tiroPressionado;
    }

    public boolean getTiroPressionado() {
        return tiroPressionado;
    }

    public void setLogicaNecessariaNesteLoop(boolean logicaNecessariaNesteLoop) {
        this.logicaNecessariaNesteLoop = logicaNecessariaNesteLoop;
    }

    public boolean getLogicaNecessariaNesteLoop() {
        return logicaNecessariaNesteLoop;
    }

    public void setAllienCont(int allienCont) {
        this.allienCont = allienCont;
    }

    public int getAllienCont() {
        return allienCont;
    }

    public void setUltimoTiro(long ultimoTiro) {
        this.ultimoTiro = ultimoTiro;
    }

    public long getUltimoTiro() {
        return ultimoTiro;
    }

    public void setIntervaloDeTiro(long intervaloDeTiro) {
        this.intervaloDeTiro = intervaloDeTiro;
    }

    public long getIntervaloDeTiro() {
        return intervaloDeTiro;
    }

    public void setVelocidadeDeMovimento(double velocidadeDeMovimento) {
        this.velocidadeDeMovimento = velocidadeDeMovimento;
    }

    public double getVelocidadeDeMovimento() {
        return velocidadeDeMovimento;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setEntidades(ArrayList entidades) {
        this.entidades = entidades;
    }

    public ArrayList getEntidades() {
        return entidades;
    }

    public void setRemoveLista(ArrayList removeLista) {
        this.removeLista = removeLista;
    }

    public ArrayList getRemoveLista() {
        return removeLista;
    }
}
