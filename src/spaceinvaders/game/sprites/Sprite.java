package spaceinvaders.game.sprites;

import java.awt.Graphics;
import java.awt.Image;
/**
 *
 * @author Victor Palmeira
 * @version 1-25/09/2016, 10:07:33
 * @languageOfComments: Portuguese
 */

/**
 * Um Sprite para ser mostrado na tela. A classe Sprite contém
 * apenas a imagem, não contendo informações sobre posição ou
 * velocidade. Isso no permite usar o mesmo Sprite para múltiplas
 * entidades simultaneamente. Por isso, não é necessário guardar 
 * várias cópias da mesma imagem.
 * 
 * As imagens utilizadas no jogo se localizam no mesmo
 * pacote da classe Sprite.
 * 
 * Baseado no tutorial de Kevin Glass
 */
public class Sprite 
{
    /**
     * Imagem a ser desenhada para o Sprite
     */
    private Image img;
    
    /**
     * Cria um novo Sprite para a imagem "img"
     * @param img A imagem a ser carregada
     */
    public Sprite(Image img)
    {
        this.img = img;
    }
    /**
     * Retorna a largura da imagem do Sprite
     * @return  largura (em pixels)
     */  
    public int getLargura()
    {
        return img.getWidth(null);
    }
    
    /**
     * Retorna a altura da imagem do Sprite
     * @return altura (em pixels)
     */
    public int getAltura()
    {
        return img.getHeight(null);
    }
    
    /**
     * Desenha o Sprite no contexto gráfico "grap" e
     * na posição especificada.
     * @param grap O contexto gráfico em questão
     * @param x A coordenada x onde o sprite será desenhado
     * @param y A coordenada y onde o sprite será desenhado
     */
    public void desenhar(Graphics grap, int x, int y)
    {
        grap.drawImage(img, x, y, null);
    }
}
