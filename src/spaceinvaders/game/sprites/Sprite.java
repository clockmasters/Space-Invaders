package spaceinvaders.game.sprites;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite 
{
    private Image img;
    
    //Construtor
    public Sprite(Image img)
    {
        this.img = img;
    }
    
    public int getLargura()
    {
        return img.getWidth(null);
    }
    
    public int getAltura()
    {
        return img.getHeight(null);
    }
    
    //Desenha o sprite na tela no ponto (x,y)
    public void desenhar(Graphics grap, int x, int y)
    {
        grap.drawImage(img, x, y, null);
    }
}
