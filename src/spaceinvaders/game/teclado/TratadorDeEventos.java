package spaceinvaders.game.teclado;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;//Devido a excessão de teclado IOException
import java.util.logging.Logger;//Devido a excessão de teclado IOException

import spaceinvaders.game.Game;

/**
 *
 * @author douglas
 * @version 1-25/09/2016, 23:55:52
 * @languageOfComments: Portuguese
 */
public class TratadorDeEventos extends KeyAdapter {

    private int contTeclasPressionadas;
    private Game game;
    //private boolean pausa;

    public TratadorDeEventos() {
        contTeclasPressionadas = 1;
        //pausa = false;
    }

    public TratadorDeEventos(Game game) {
        contTeclasPressionadas = 1;
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.getEsperarPorPressionarTecla()) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.setEsquerdaPressionada(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.setDireitaPressionada(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.setTiroPressionado(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (game.getEsperarPorPressionarTecla()) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.setEsquerdaPressionada(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.setDireitaPressionada(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.setTiroPressionado(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.print("Tecla Digitada: ");

        if (game.getEsperarPorPressionarTecla()) {
            if (contTeclasPressionadas == 1) {
                game.setEsperarPorPressionarTecla(false);
                try {
                    game.startGame();
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

    public void setContTeclasPressionadas(int contTeclasPressionadas) {
        this.contTeclasPressionadas = contTeclasPressionadas;
    }

    public int getContTeclasPressionadas() {
        return contTeclasPressionadas;
    }
}
