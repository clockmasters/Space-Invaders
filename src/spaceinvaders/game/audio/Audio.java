package spaceinvaders.game.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
//import sun.audio.AudioStream;

/**
 *
 * @author douglas
 * @version 1-15/10/2016, 22:18:49
 * @languageOfComments: Portuguese
 */
public class Audio {

    public static void playEfeito(String caminho) throws IOException {
        try {
            caminho = new File(caminho).getAbsolutePath();
            FileInputStream input = new FileInputStream(caminho);
            AudioStream efeito = new AudioStream(input);
            AudioPlayer.player.start(efeito);

        } catch (IOException e) {
            fail("Falha ao carregar: " + caminho);
        }
    }

    private static void fail(String msg) {
        System.err.println(msg);
        System.exit(0);
    }
}
