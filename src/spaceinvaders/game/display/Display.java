package spaceinvaders.game.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

    public Display(String titulo, int alt, int larg) {
        this.alt = alt;
        this.larg = larg;
        this.titulo = titulo;

        criaDisplay();
    }

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
    }
}
