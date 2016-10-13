package spaceinvaders.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//import spaceinvaders.game.display.Display;
import spaceinvaders.game.entidades.Entidade;
import spaceinvaders.game.entidades.EntidadeAlien;
import spaceinvaders.game.entidades.EntidadeNave;
import spaceinvaders.game.entidades.EntidadeRobo;
//import spaceinvaders.game.teclado.TratadorDeEventos;
import spaceinvaders.game.entidades.EntidadeTiro;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 21:03:13
 * @languageOfComments: Portuguese
 */
public class Game extends Canvas {

    private BufferStrategy estrategia;//
    private boolean jogoRodando;
    private ArrayList entidades;
    private ArrayList removeLista;
    private Entidade nave;//
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

        //Display tela = new Display("Space Invaders", 600, 800);
        //tela.getPainel().addKeyListener(new TratadorDeEventos());
        //tela.requestFocus();//Não sei pra que serve
        //tela.setIgnoreRepaint(true);//Não sei pra que serve
        //tela.createBufferStrategy(2);//Não sei pra que serve
        //tela.getPainel().addKeyListener(new TratadorDeEventos(this));
        //estrategia = tela.getBufferStrategy();//Não sei pra que serve
        JFrame frame = new JFrame("Space Invaders!");
        JPanel painel = (JPanel) frame.getContentPane();
        //Canvas canvas = new Canvas();

        painel.setPreferredSize(new Dimension(800, 600));
        painel.setLayout(null);

        setBounds(0, 0, 800, 600);
        painel.add(this);

        setIgnoreRepaint(true);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel.requestFocusInWindow();
        painel.setFocusable(true);
        painel.addKeyListener(new TratadorDeEventos());

        addKeyListener(new TratadorDeEventos());

        //requestFocus();
        createBufferStrategy(2);
        estrategia = getBufferStrategy();

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
        nave = new EntidadeNave(this, "spaceinvaders/game/sprites/sprnave.gif", 370, 550);
        entidades.add(nave);

        alienCont = 0;
        roboCont = 0;

        for (col = 0; col < 5; col++) {
            for (linha = 0; linha < 11; linha++) {
                if (col <= 1) {
                    Entidade robo = new EntidadeRobo(this, "spaceinvaders/game/sprites/sprrobo.png", 100 + (linha * 50), (50) + col * 30);
                    entidades.add(robo);
                    roboCont++;
                } else {
                    Entidade alien = new EntidadeAlien(this, "spaceinvaders/game/sprites/spralien.gif", 100 + (linha * 50), (50) + col * 30);
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
        EntidadeTiro tiro = new EntidadeTiro(this, "spaceinvaders/game/sprites/sprtiro.gif", nave.getX() + 10, nave.getY() - 10);
        entidades.add(tiro);
    }

    public void gameLoop() throws IOException {
        int i, j;
        long tempoUltimoLoop = System.currentTimeMillis();
        long delta;

        System.out.println("gameLoop");

        while (jogoRodando) {
            //System.out.println("Jogo Rodando: " + jogoRodando);
            //System.out.println("Esperar por Tecla: " + esperarPorPressionarTecla);

            delta = System.currentTimeMillis() - tempoUltimoLoop;
            tempoUltimoLoop = System.currentTimeMillis();

            Graphics2D g = (Graphics2D) estrategia.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            if (!esperarPorPressionarTecla) {
                for (i = 0; i < entidades.size(); i++) {
                    Entidade entidade = (Entidade) entidades.get(i);
                    entidade.mover(delta);
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

    private class TratadorDeEventos extends KeyAdapter {

        private int contTeclasPressionadas;
        private boolean pausa;

        public TratadorDeEventos() {
            contTeclasPressionadas = 1;
            pausa = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("Tecla Pressionada");
            if (getEsperarPorPressionarTecla()) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                setEsquerdaPressionada(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                setDireitaPressionada(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                setTiroPressionado(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("Tecla Liberada");

            if (getEsperarPorPressionarTecla()) {
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                setEsquerdaPressionada(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                setDireitaPressionada(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                setTiroPressionado(false);
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.print("Tecla Digitada: ");

            if (getEsperarPorPressionarTecla()) {
                if (contTeclasPressionadas == 1) {
                    setEsperarPorPressionarTecla(false);
                    try {
                        startGame();
                    } catch (IOException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    contTeclasPressionadas = 0;
                } else {
                    contTeclasPressionadas++;
                }
            }

            if (e.getKeyChar() == 27) {
                System.out.println("Escape - Sair");
                System.exit(0);//Não sei se vai funcionar, visto que é dado por um obj desta classe
            } else if ((e.getKeyChar() == 80) || (e.getKeyChar() == 112)) {
                System.out.println("P - Pausa");
                //setJogoRodando(pausa);
                //pausa = !pausa;
            }
        }

        /**
         *
         * @param contTeclasPressionadas
         */
        public void setContTeclasPressionadas(int contTeclasPressionadas) {
            this.contTeclasPressionadas = contTeclasPressionadas;
        }

        public int getContTeclasPressionadas() {
            return contTeclasPressionadas;
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
