package spaceinvaders.game.entidades;

import java.awt.Graphics;
import spaceinvaders.game.sprites.Sprite;

/**
 * Interface contendo todos os métodos a serem utilizados por classes de 
 * Entidade.
 * 
 * @author João Victor Carneiro
 */
public interface Tangivel {
    
    /**
     * Faz com que a Entidade se mova baseada em uma certa quantidade de tempo
     * passada.
     *
     * @param delta A quantidade de tempo que passou em milisegundos.
     */
    public abstract void mover(long delta);
    
    /**
     * Altera a velocidade horizontal desta entidade.
     *
     * @param dx Nova velocidade horizontal desta entidade(pixels/s).
     */    
    public abstract void setMovimentoHorizontal(double dx);
    
    /**
     * Altera a velocidade vertical desta entidade.
     *
     * @param dy Nova velocidade vertical desta entidade(pixels/s).
     */    
    public abstract void setMovimentoVertical(double dy);
    
    /**
     * Retorna a velocidade horizontal desta entidade.
     *
     * @return Velocidade horizontal desta entidade(pixels/s).
     */    
    public abstract double getMovimentoHorizontal();
    
    /**
     * Retorna a velocidade vertical desta entidade.
     *
     * @return Velocidade vertical desta entidade(pixels/s).
     */    
    public abstract double getMovimentoVertical();
    
    /**
     * Desenha a entidade para o contexto gráfico fornecido.
     *
     * @param g o contexto gráfico fornecido no qual irá desenhar a entidade.
     */    
    public abstract void desenha(Graphics g);
    
    /**
     * Lógica associada com a entidade.
     *
     */ 
    public abstract void fazLogica();
    
    /**
     * Retorna a posição x desta unidade.
     *
     * @return Posição em inteiro x desta entidade.
     */    
    public abstract int getX();
    
    /**
     * Retorna a posição y desta entidade.
     *
     * @return Posição em inteiro y desta entidade.
     */    
    public abstract int getY();
    
    /**
     * Checa se esta entidade colidiu com uma outra.
     *
     * @param outra A outra entidade com a qual deseja-se verificar a colisão
     * @return Verdade se esta entidade colide com a outra.
     */    
    public abstract boolean colideCom(Entidade outra);
    
    /**
     * Notificação de que esta entidade colidiu com outra.
     *
     * @param outra A entidade com a qual esta colidiu.
     *
     */    
    public abstract void colidiuCom(Entidade outra);
    
    /**
     * Retorna o sprite desta entidade.
     * @return Atributo sprite de Classe Sprite dessa entidade.
     */
    public abstract Sprite getSprite();
    
    /**
     * Altera o atributo Sprite dessa entidade.
     * @param sprite O sprite contendo a nova imagem.
     */
    public abstract void setSprite(Sprite sprite);
    
    /**
     * Retorna o estado de explodido da entidade.
     * @return boolean de estado explodido.
     */
    public abstract boolean getExplodiu();
    
    /**
     * Altera o estado de explodido de uma entidade.
     * @param explodiu Estado boolean a ser definido.
     */
    public abstract void setExplodiu(boolean explodiu);
    
    /**
     * Retorna se a entidade é tangível.
     * @return boolean contendo o estado de tangibilidade.
     */
    public abstract boolean getTangivel();
    
    /**
     * Altera a tangibilidade da entidade.
     * @param tangivel Estado boolean de tangibilidade a ser definido.
     */
    public abstract void setTangivel(boolean tangivel);
}
