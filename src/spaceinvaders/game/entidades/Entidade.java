package spaceinvaders.game.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import spaceinvaders.game.sprites.GuardaSprite;
import spaceinvaders.game.sprites.Sprite;

/**
 * Uma entidade representa qualquer elemento que apareça no jogo. A entidade é
 * responsável por resolver colisões e movimentos baseada em um conjunto de
 * propriedades definidas tanto pela classe quanto externanmente. Detalhe: As
 * posições são do tipo double. Isso não significa que a entidade possa se mover
 * parcialmente em um único pixel, o objetivo é que não percamos a precisão com
 * a qual a entidade se move.
 *
 * @author douglas
 * @version 1-27/09/2016, 11:23:09
 * @languageOfComments: Portuguese
 */
public abstract class Entidade implements Tangivel{

    /**
     * Posição x da entidade.
     */
    protected double x;
    /**
     * Posição y da entidade.
     */
    protected double y;
    /**
     * Sprite associado a entidade.
     */
    protected Sprite sprite;
    /**
     * Velocidade horizontal da entidade.
     */
    protected double dx;
    /**
     * Velocidade vertical da entidade.
     */
    protected double dy;
    /**
     * Retângulo de resolução de colisão usado nesta entidade.
     */
    private Rectangle eu;
    /**
     * Retângulo de resolução de colisão usado na outra entidade que colidiu com
     * esta.
     */
    private Rectangle ele;

    /**
     * Se explodiu.
     */
    protected boolean explodiu;

    /**
     * Se é tangível.
     */
    protected boolean tangivel;

    /**
     * Constrói a entidade baseado em um sprite e uma localização.
     *
     * @param ref A referência, o caminho, da imagem carregada por esta entidade
     * @param x A localização horizontal desta entidade
     * @param y A localização vertical desta entidade
     * @throws java.io.IOException
     */
    public Entidade(String ref, int x, int y) throws IOException {
        eu = new Rectangle();
        ele = new Rectangle();
        explodiu = false;
        tangivel = true;
        sprite = GuardaSprite.sprite_get().getSprite(ref);
        this.x = x;
        this.y = y;
    }

    @Override
    public void mover(long delta) {
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    @Override
    public void setMovimentoHorizontal(double dx) {
        this.dx = dx;
    }

    @Override
    public void setMovimentoVertical(double dy) {
        this.dy = dy;
    }

    @Override
    public double getMovimentoHorizontal() {
        return dx;
    }

    @Override
    public double getMovimentoVertical() {
        return dy;
    }

    @Override
    public void desenha(Graphics g) {
        sprite.desenhar(g, (int) x, (int) y);
    }

    @Override
    public void fazLogica() {
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public boolean colideCom(Entidade outra) {
        eu.setBounds((int) x, (int) y, sprite.getLargura(), sprite.getAltura());
        ele.setBounds((int) outra.x, (int) outra.y, outra.sprite.getLargura(), outra.sprite.getAltura());

        return eu.intersects(ele);
    }

    @Override
    public abstract void colidiuCom(Entidade outra);

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public boolean getExplodiu() {
        return explodiu;
    }

    @Override
    public void setExplodiu(boolean explodiu) {
        this.explodiu = explodiu;
    }

    @Override
    public boolean getTangivel() {
        return tangivel;
    }

    @Override
    public void setTangivel(boolean tangivel) {
        this.tangivel = tangivel;
    }
}
