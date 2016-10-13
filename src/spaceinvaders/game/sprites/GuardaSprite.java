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
/**
 *
 * @author Victor Palmeira
 * @version 1-25/09/2016, 10:51:25
 * @languageOfComments: Portuguese
 */

/**
 * Gerencia os Sprites usados no jogo. Carrega todos os Sprites
 * no cache, deixando-os prontos para futuro uso.
 * 
 * Baseado no tutorial de Kevin Glass
 */
public class GuardaSprite         
{
    /** É a única instância da classe */
    private static GuardaSprite unico = new GuardaSprite();
    /**
     * Faz o get da instância "unico"
     * @return instância "unico"
     */
    public static GuardaSprite sprite_get()
    {
        return unico;
    }
    /** Cache dos sprites, serve de referência para os Sprites */
    private HashMap sprites = new HashMap();
    
    /**
     * Coleta um Sprite do banco de Sprites
     * @param spr A referência da imagem para ser usada no Sprite
     * @return Uma instância Sprite contendo a imagem requisitada
     * @throws IOException 
     */
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
