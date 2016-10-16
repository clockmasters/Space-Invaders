package spaceinvaders.game.display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import spaceinvaders.game.Game;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 21:31:35
 * @languageOfComments: Portuguese
 */
public class Display extends Canvas {

    private JFrame frame;
    private JPanel painel;
    private String titulo;
    private int alt, larg;
    private BufferStrategy estrategia;
    private Graphics2D contextoGrafico;

    /**
     * Construtor da classe Display, inicializa os atributos e chama o método
     * cria Display
     *
     * @param titulo
     * @param alt
     * @param larg
     */
    public Display(String titulo, int alt, int larg) {
        this.alt = alt;
        this.larg = larg;
        this.titulo = titulo;

        criaDisplay();
    }

    /**
     * Cria a Janela para o jogo e Estratégia para alocação de memória dos
     * gráficos
     */
    public void criaDisplay() {
        frame = new JFrame(titulo);

        painel = (JPanel) frame.getContentPane();
        painel.setPreferredSize(new Dimension(larg, alt));
        painel.setLayout(null);

        setBounds(0, 0, larg, alt);
        painel.add(this);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel.setFocusable(true);
        painel.requestFocusInWindow();
        painel.setIgnoreRepaint(true);

        createBufferStrategy(2);//Onde vamos armazenar nosso contexto gráfico
        estrategia = getBufferStrategy();
    }

    /**
     * Cria o contexto gráfico para que possamos desenhar as entidades na tela
     *
     * @param game
     */
    public void criaContextoGrafico(Game game) {
        contextoGrafico = (Graphics2D) estrategia.getDrawGraphics();//Cria o contexto gráfico
        contextoGrafico.setColor(Color.black);//Define a cor de fundo utilizada com o contexto gráfico na tela
        contextoGrafico.fillRect(0, 0, larg, alt);//Define o tamamnho do espaço no contexto gráfico preenchido pela cor
    }

    /**
     * Desvincula o contexto gráfico da tela liberando recursos do sistema e
     * desenha os últimos gráficos armazenados no buffer de estratégia
     *
     */
    public void desenhaGraficos() {
        contextoGrafico.dispose();
        estrategia.show();
    }

    /**
     * Imprime uma mensagem na tela de acordo com o contexto gráfico
     *
     * @param mensagem Mensagem a ser exibida na tela
     * @param game Jogo no qual o contexto gráfico atua
     */
    public void imprimeMensagem(Game game, String mensagem) {
        contextoGrafico.setColor(Color.white);
        contextoGrafico.drawString(mensagem, (800 - contextoGrafico.getFontMetrics().stringWidth(mensagem)) / 2, 250);
        contextoGrafico.drawString(mensagem, (800 - contextoGrafico.getFontMetrics().stringWidth(mensagem)) / 2, 300);
    }

    public void setLarg(int larg) {
        this.larg = larg;
    }

    public int getLargCont() {
        return larg;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public int getAlt() {
        return alt;
    }

    public void setAllienCont(String titulo) {
        this.titulo = titulo;
    }

    public String getAllienCont() {
        return titulo;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPainel() {
        return painel;
    }

    public void setPainel(JPanel painel) {
        this.painel = painel;
    }

    public BufferStrategy getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(BufferStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public Graphics2D getContextoGrafico() {
        return contextoGrafico;
    }

    public void setContextoGrafico(Graphics2D contextoGrafico) {
        this.contextoGrafico = contextoGrafico;
    }
}
