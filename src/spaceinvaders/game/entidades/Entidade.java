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
public abstract class Entidade {

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
     * Constrói a entidade baseado em um sprite e uma localização
     *
     * @param ref A referência, o caminho, da imagem carregada por esta entidade
     * @param x A localização horizontal desta entidade
     * @param y A localização vertical desta entidade
     */
    public Entidade(String ref, int x, int y) throws IOException {
        eu = new Rectangle();
        ele = new Rectangle();
        sprite = GuardaSprite.sprite_get().getSprite(ref);
        this.x = x;
        this.y = y;
    }

    /**
     * Faz com que a Entidade se mova baseada em uma certa quantidade de tempo
     * passada
     *
     * @param delta A quantidade de tempo que passou em milisegundos
     */
    public void mover(long delta) {
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    /**
     * Altera a velocidade horizontal desta entidade.
     *
     * @param dx Nova velocidade horizontal desta entidade(pixels/s)
     */
    public void setMovimentoHorizontal(double dx) {
        this.dx = dx;
    }

    /**
     * Altera a velocidade vertical desta entidade.
     *
     * @param dy Nova velocidade vertical desta entidade(pixels/s)
     */
    public void setMovimentoVertical(double dy) {
        this.dy = dy;
    }

    /**
     * Retorna a velocidade horizontal desta entidade.
     *
     * @return Velocidade horizontal desta entidade(pixels/s)
     */
    public double getMovimentoHorizontal() {
        return dx;
    }

    /**
     * Retorna a velocidade vertical desta entidade.
     *
     * @return Velocidade vertical desta entidade(pixels/s)
     */
    public double getMovimentoVertical() {
        return dy;
    }

    /**
     * Desenha a entidade para o contexto gráfico fornecido.
     *
     * @param g o contexto gráfico fornecido no qual irá desenhar a entidade
     */
    public void desenha(Graphics g) {
        sprite.desenhar(g, (int) x, (int) y);
    }

    /**
     * Lógica associada com a entidade. Não tem uso ainda
     *
     */
    public void fazLogica() {
    }

    /**
     * Retorna a posição x desta unidade.
     *
     * @return Posição em inteiro x desta entidade
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Retorna a posição y desta entidade.
     *
     * @return Posição em inteiro y desta entidade
     */
    public int getY() {
        return (int) y;
    }

    /**
     * Checa se esta entidade colidiu com uma outra
     *
     * @param outra A outra entidade com a qual deseja-se verificar a colisão
     * @return Verdade se esta entidade colide com a outra
     */
    public boolean colideCom(Entidade outra) {
        eu.setBounds((int) x, (int) y, sprite.getLargura(), sprite.getAltura());
        ele.setBounds((int) outra.x, (int) outra.y, outra.sprite.getLargura(), outra.sprite.getAltura());

        return eu.intersects(ele);
    }

    /**
     * Notificação de que esta entidade colidiu com outra.
     *
     * @param outra A entidade com a qual esta colidiu
     *
     */
    public abstract void colidiuCom(Entidade outra);
}
