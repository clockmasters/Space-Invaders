package spaceinvaders.game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
 * Classe responsável pelo loop, controle da lógica e inicialização de entidades
 * no jogo Um mediador(os métodos notifica por exemplo) será informado quando as
 * entidades dentro do jogo detectam eventos (Exemplo: alien morto, robo morto,
 * jogador morto) e tomará as ações aproriadas no jogo.
 *
 * @author douglas
 * @version 1-25/09/2016, 21:03:13
 * @languageOfComments: Portuguese
 *
 */
public class Game {
    /**
     * Declara se o jogo está rodando ou não.
     */
    private boolean jogoRodando;

    /**
     * Array para armazenamento das entidades existentes no jogo.
     */
    private ArrayList entidades;

    /**
     * Array para armazenamento das entidades a serem removidas na próxima
     * atualização de lógica do jogo.
     */
    private ArrayList removeLista;

    /**
     * Entidade para instaciação da nave no jogo.
     */
    private Entidade nave;

    /**
     * Tela principal para impressão do jogo.
     */
    public Display tela;

    /**
     * Velocidade de movimento horizontal da nave.
     */
    private double velocidadeDeMovimento;

    /**
     * Tempo decorrido, em milissegundos, desde o último tiro efetuado pelo
     * jogador.
     */
    private long ultimoTiro;

    /**
     * Intervalo, em milissegundos, que cada tiro efetuado pelo jogador deve ter
     * entre si.
     *
     */
    private long intervaloDeTiro;

    /**
     * Número de aliens no jogo atual.
     */
    private int alienCont;

    /**
     * Número de robôs no jogo atual.
     */
    private int roboCont;

    /**
     * Número total(aliens e robôs) de inimigos no jogo atual.
     */
    private int inimigoCont;

    /**
     * String para armazenar uma mensagem a ser exibida na tela.
     */
    private String mensagem;

    /**
     * Declara o estado de espera ou não para o ouvinte de eventos de
     * tecladteclado.TratadorDeEventoso.
     */
    private boolean esperarPorPressionarTecla;

    /**
     * Declara se a seta esquerda foi pressionada no teclado para o ouvinte de
     * eventos dos tecladoteclado.TratadorDeEventos.
     */
    private boolean esquerdaPressionada;

    /**
     * Declara se a seta direita foi pressionada no teclado para o ouvinte de
     * eventos dos tecladoteclado.TratadorDeEventos.
     */
    private boolean direitaPressionada;

    /**
     * Declara se o espaço(tiro) foi pressionado no teclado para o ouvinte de
     * eventos dos teclado(teclado.TratadorDeEventos).
     */
    private boolean tiroPressionado;

    /**
     * Declara a necessidade ou não de atualizaçaõ da lógica no loopAtual.
     */
    private boolean logicaNecessariaNesteLoop;
    
    /**
     * Pontuação do jogador. Cada inimigo morto adicionará um valor aqui.
     */
    private int score;
     /**
     * Nome do jogador. Não está sendo utilizado.
     */   
    private String nome_jogador;

    /**
     * Construtor da classe Game, incializa os atributos da classe, cria um
     * display para imprimir o jogo na tela, cria um ouvinte de eventos para as
     * ações do jogo, incializa chama o método para inicializar as entidades na
     * tela.
     *
     * @throws IOException
     */
    public Game() throws IOException {
        jogoRodando = true;
        entidades = new ArrayList();
        removeLista = new ArrayList();
        velocidadeDeMovimento = 300;
        ultimoTiro = 0;
        intervaloDeTiro = 500;
        score = 0;

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

    /**
     * Inicia um novo jogo, livre de todo dado antigo.
     *
     * @throws IOException
     */
    public void setNomeJogador(String nome)
    {
        this.nome_jogador = nome;
    }
    
    public String getNomeJogador()
    {
        return this.nome_jogador;
    }
    public void startGame() throws IOException {

        System.out.println("startGame");
        entidades.clear();
        initEntidades();
        
        esquerdaPressionada = false;
        direitaPressionada = false;
        tiroPressionado = false;
    }

    /**
     * Inicializa as entidades do jogo na tela(nave, robos, aliens) Cada
     * entidade será adicionada a uma lista geral de entidades chamada
     * 'entidades'.
     *
     * @throws IOException
     */
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

    /**
     * Altera o valor da logicaNecessaria no loop para true ou false,
     * notificando a necessidade de atualização da lógica do jogo.
     */
    public void atualizaLogica() {
        logicaNecessariaNesteLoop = true;
    }

    /**
     * Lista de entidades a serem removidas na próxima atualização de lógica do
     * jogo.
     *
     * @param entidade
     */
    public void removeEntidade(Entidade entidade) {
        removeLista.add(entidade);
    }

    /**
     * Notificação de que o jogador perdeu o jogo.
     */
    public void notificaMorte() {
        mensagem = "Ahh não! Eles te pegaram, quer tentar de novo?\n Sua pontuação é: " + this.score;
        esperarPorPressionarTecla = true;
    }

    /**
     * Notificação de que o jogador ganhou o jogo.
     */
    public void notificaVitoria() {
        mensagem = "Bom trabalho! Você Ganhou!\n Sua pontuação é : " + this.score;
        esperarPorPressionarTecla = true;
    }

    /**
     * Notifica que um alien foi morto, decrementa a quantidade de inimigos e de
     * aliens no jogo, incrementa a velocidade horizontal do resto da frota de
     * aliens. Cada alien gera 10 pontos.
     *
     * @param alien
     */
    public void notificaAlienMorto(Entidade alien) {
        int i;
        if (alien.getExplodiu()) {
            inimigoCont--;
            alienCont--;
            score = score + 10;
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

    /**
     * Notifica que um robô foi morto, decrementa a quantidade de inimigos e de
     * robôs no jogo, incrementa a velocidade horizontal do resto da frota de
     * robôs. Cada robõ gera 60 pontos.
     *
     * @param robo robo que foi morto
     */
    public void notificaRoboMorto(Entidade robo) {
        int i;

        if (robo.getExplodiu()) {
            inimigoCont--;
            roboCont--;
            score = score + 60;
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

    /**
     * Recebe o pedido de tiro e permite ou não ao jogador atirar no dado
     * momento em que foi chamado, considerando o tempo desde o último tiro.
     *
     * @throws IOException
     */
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

    /**
     * Loop principal do jogo, aqui atualiza-se os atributos de teclado e
     * atualiza-se o buffer gráfico na tela.
     *
     * @throws IOException
     */
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
