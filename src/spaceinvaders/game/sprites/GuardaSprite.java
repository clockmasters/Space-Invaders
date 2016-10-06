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
    
    private HashMap sprites = new HashMap();
    
    public Sprite getSprite(String spr) throws IOException
    {
        //Se ja tivermos o sprite registrado
        //Retorna a verão existente
        if(sprites.get(spr) != null)
        {
            return (Sprite) sprites.get(spr);
        }
        //Caso contrário, pega o sprite não registrado e registra
        BufferedImage sourceImage = null;
        
        try
        {
            URL url = this.getClass().getClassLoader().getResource(spr);
            if (url == null) 
            {
		fail("Não encontrado: " + spr);
            }
            sourceImage = ImageIO.read(url);
        } 
        catch(IOException e)
        {
            fail("Falha ao carregar: " + spr);
        }
        
        //Agora é criada a imagem para guardar o Sprite
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
        image.getGraphics().drawImage(sourceImage,0,0,null);
        //Cria o Sprite, e por ele no Cache
        Sprite sprite = new Sprite(image);
	sprites.put(spr,sprite);
        
        return sprite;
    }
        //Tratamento de Exception
	private void fail(String msg)
        {
		System.err.println(msg);
		System.exit(0);
	}   
}
