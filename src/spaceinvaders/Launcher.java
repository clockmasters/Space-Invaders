package spaceinvaders;

import spaceinvaders.game.Game;

/**
 *
 * @author douglas
 * @version 1- 25/09/2016, 19:36:41
 * @languageOfComments: Portuguese
 */
public class Launcher {

    public static void main(String[] args) {
        Game spaceInvaders = new Game();
        spaceInvaders.gameLoop();
    }

}
