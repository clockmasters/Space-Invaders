package spaceinvaders.game;

import java.io.IOException;

import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import spaceinvaders.game.audio.Audio;

import spaceinvaders.game.display.Display;
import spaceinvaders.game.entidades.Entidade;
import spaceinvaders.game.entidades.EntidadeAlien;
import spaceinvaders.game.entidades.EntidadeNave;
import spaceinvaders.game.entidades.EntidadeRobo;
import spaceinvaders.game.teclado.TratadorDeEventos;
import spaceinvaders.game.entidades.EntidadeTiro;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 21:03:13
 * @languageOfComments: Portuguese
 */
public class Game {

    private boolean jogoRodando;
    private ArrayList entidades;
    private ArrayList removeLista;
    private Entidade nave;
    private Display tela;
    private double velocidadeDeMovimento;
    private long ultimoTiro;
    private long intervaloDeTiro;
    private int alienCont;
    private int roboCont;
    private int inimigoCont;

    private String mensagem;
    private boolean esperarPorPressionarTecla;
    private boolean esquerdaPressionada;
    private boolean direitaPressionada;
    private boolean tiroPressionado;
    private boolean logicaNecessariaNesteLoop;

    public Game() throws IOException {
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

        tela = new Display("Space Invaders da Zueira!", 600, 800);
        tela.getPainel().addKeyListener(new TratadorDeEventos(this));

        initEntidades();
    }

    public void startGame() throws IOException {

        System.out.println("startGame");
        entidades.clear();
        initEntidades();

        esquerdaPressionada = false;
        direitaPressionada = false;
        tiroPressionado = false;
    }

    private void initEntidades() throws IOException {
        System.out.println("initEntities");
        int linha, col;
        nave = new EntidadeNave(this, "spaceinvaders/game/sprites/img/sprnave.gif", 370, 550);
        entidades.add(nave);

        alienCont = 0;
        roboCont = 0;

        for (col = 0; col < 5; col++) {
            for (linha = 0; linha < 11; linha++) {
                if (col <= 1) {
                    Entidade robo = new EntidadeRobo(this, "spaceinvaders/game/sprites/img/sprrobo.png", 100 + (linha * 50), (50) + col * 30);
                    entidades.add(robo);
                    roboCont++;
                } else {
                    Entidade alien = new EntidadeAlien(this, "spaceinvaders/game/sprites/img/spralien.gif", 100 + (linha * 50), (50) + col * 30);
                    entidades.add(alien);
                    alienCont++;
                }
            }
        }

        inimigoCont = alienCont + roboCont;
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
        esperarPorPressionarTecla = true;
    }

    public void notificaAlienMorto(Entidade alien) {
        int i;
        if (alien.getExplodiu()) {
            inimigoCont--;
            alienCont--;
        }
        if (inimigoCont == 0) {
            notificaVitoria();
        }

        for (i = 0; i < entidades.size(); i++) {
            Entidade entidade = (Entidade) entidades.get(i);

            if (entidade instanceof EntidadeAlien) {
                //aumenta em 2%
                entidade.setMovimentoHorizontal(entidade.getMovimentoHorizontal() * 1.05);
            }
        }
    }

    public void notificaRoboMorto(Entidade robo) {
        int i;

        if (robo.getExplodiu()) {
            inimigoCont--;
            roboCont--;
        }
        if (inimigoCont == 0) {
            notificaVitoria();
        }

        for (i = 0; i < entidades.size(); i++) {
            Entidade entidade = (Entidade) entidades.get(i);

            if (entidade instanceof EntidadeRobo) {
                //aumenta em 2%
                entidade.setMovimentoHorizontal(entidade.getMovimentoHorizontal() * 1.05);
            }
        }
    }

    public void tentaAtirar() throws IOException {
        if (System.currentTimeMillis() - ultimoTiro < intervaloDeTiro) {
            return;
        }

        ultimoTiro = System.currentTimeMillis();
        System.out.println("Espaço - Atira");
        EntidadeTiro tiro = new EntidadeTiro(this, "spaceinvaders/game/sprites/img/sprtiro.gif", nave.getX() + 10, nave.getY() - 10);
        Audio.playEfeito("src/spaceinvaders/game/audio/laserBlast.wav");
        entidades.add(tiro);
    }

    public void gameLoop() throws IOException {
        int i, j;
        long tempoUltimoLoop = System.currentTimeMillis();
        long delta;

        System.out.println("gameLoop");

        while (jogoRodando) {

            delta = System.currentTimeMillis() - tempoUltimoLoop;
            tempoUltimoLoop = System.currentTimeMillis();

            tela.criaContextoGrafico(this);

            if (!esperarPorPressionarTecla) {
                for (i = 0; i < entidades.size(); i++) {
                    Entidade entidade = (Entidade) entidades.get(i);
                    entidade.mover(delta);
                }
            }

            for (i = 0; i < entidades.size(); i++) {
                Entidade entidade = (Entidade) entidades.get(i);
                entidade.desenha(tela.getContextoGrafico());
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
                tela.imprimeMensagem(this, mensagem);
                if (inimigoCont == 55) {
                    mensagem = "Pressione qualquer Tecla";
                }
            }

            tela.desenhaGraficos();

            nave.setMovimentoHorizontal(0);
            if ((esquerdaPressionada) && (!direitaPressionada)) {
                System.out.println("Esquerda");
                nave.setMovimentoHorizontal(-velocidadeDeMovimento);
            } else if ((!esquerdaPressionada) && (direitaPressionada)) {
                System.out.println("Direita");
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

    public void setAlienCont(int alienCont) {
        this.alienCont = alienCont;
    }

    public int getAlienCont() {
        return alienCont;
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
