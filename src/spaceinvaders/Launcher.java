package spaceinvaders;

import java.io.IOException;
import spaceinvaders.game.Game;

/**
 * Classe principal do aplicativo, responsável por criar a instância do classe
 * Game e iniciar o loop principal do jogo.
 *
 * @author douglas
 * @version 1- 25/09/2016, 19:36:41
 * @languageOfComments: Portuguese
 */
public class Launcher {

    /**
     * Main da classe, responsável por instanciar a classe Game e inicializar o
     * loop principal do jogo.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Game spaceInvaders = new Game();
        spaceInvaders.gameLoop();
    }

}
