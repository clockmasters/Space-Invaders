package spaceinvaders.game.sprites;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class GuardaSprite         
{
    private static GuardaSprite unico = new GuardaSprite();
    
    public static GuardaSprite sprite_get()
    {
        return unico;
    }
    
    /*private HashMap sprites = new HashMap();
        Vendo ainda o propósito disso       */
    
    
}
