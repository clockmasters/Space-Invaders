package spaceinvaders;

import java.io.IOException;
import spaceinvaders.game.Game;
import spaceinvaders.game.teclado.TratadorDeEventos;

/**
 *
 * @author douglas
 * @version 1- 25/09/2016, 19:36:41
 * @languageOfComments: Portuguese
 */
public class Launcher {

    public static void main(String[] args) throws IOException{
        Game spaceInvaders = new Game();
        spaceInvaders.gameLoop();
    }

}
