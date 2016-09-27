package spaceinvaders.game.teclado;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import spaceinvaders.game.Game;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 23:55:52
 * @languageOfComments: Portuguese
 */
public class TratadorDeEventos extends KeyAdapter {

    private int contTeclasPressionadas;
    private Game g;

    public TratadorDeEventos() {
        contTeclasPressionadas = 1;
    }

    public TratadorDeEventos(Game g) {
        contTeclasPressionadas = 1;
        this.g = g;
    }

    public void teclaPressionada(KeyEvent e) {
        if (g.getEsperarPorPressionarTecla()) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            g.setEsquerdaPressionada(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            g.setDireitaPressionada(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            g.setTiroPressionado(false);
        }
    }

    public void teclaLiberada(KeyEvent e) {
        if (g.getEsperarPorPressionarTecla()) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            g.setEsquerdaPressionada(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            g.setDireitaPressionada(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            g.setTiroPressionado(false);
        }
    }

    public void teclaDigitada(KeyEvent e) {
        if (g.getEsperarPorPressionarTecla()) {
            if (contTeclasPressionadas == 1) {
                g.setEsperarPorPressionarTecla(false);
                g.startGame();
                contTeclasPressionadas = 0;
            } else {
                contTeclasPressionadas++;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);//Não sei se vai funcionar, visto que é dado por um obj desta classe
        }
    }

    public void setContTeclasPressionadas(int contTeclasPressionadas) {
        this.contTeclasPressionadas = contTeclasPressionadas;
    }

    public int getContTeclasPressionadas() {
        return contTeclasPressionadas;
    }
}
