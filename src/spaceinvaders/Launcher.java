package spaceinvaders;

import java.io.IOException;
import spaceinvaders.game.Game;
import spaceinvaders.game.Intro;

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
        Game space = new Game();
        space.gameLoop();
        /*Criamos uma janela de intrudução ao jogo, porém
        por algum motivo o display do jogo em si deixa de
        funcionar quanto utilizada junto dela.*/
        /*Intro intro = new Intro();*/
    }

}
