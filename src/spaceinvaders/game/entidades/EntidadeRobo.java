package spaceinvaders.game.entidades;

import java.io.IOException;
import spaceinvaders.game.Game;

/**
 * Entidade que representa um dos robôs invasores.
 *
 * @author Joao Victor Carneiro
 */
public class EntidadeRobo extends Entidade {

    /**
     * A velocidade em que o robô se move horizontamente.
     */
    private double moveSpeed = 85;
    /**
     * A quantidade de tiros que o robô pode receber.
     */
    private int vida;
    /**
     * O jogo no qual a classe entidade existe.
     */
    private Game game;

    /**
     * Cria nova entidade alien.
     *
     * @param game O jogo no qual a entidade esta sendo criada.
     * @param ref O sprite que deve ser usado por esse robô.
     * @param x Localizacao x inicial desse robô.
     * @param y Localizacao y inicial desse robô.
     */
    public EntidadeRobo(Game game, String ref, int x, int y) throws IOException {
        super(ref, x, y);
        vida = 2;
        this.game = game;
        dx = -moveSpeed;
    }

    /**
     * Pede para que esse alien ande baseado no tempo corrido.
     *
     * @param delta O tempo que passou desde o ultimo movimento.
     */
    public void mover(long delta) {
        // se atingir o limite esquerdo da tela e estiver movendo para 
        // a esquerda, pede uma atualizacao na logica 
        if ((dx < 0) && (x < 10)) {
            game.atualizaLogica();
        }
        // se atingir o limite direito e continuar indo para a direita, 
        // tambem atualiza
        if ((dx > 0) && (x > 750)) {
            game.atualizaLogica();
        }

        // continua com o movimento normal
        super.mover(delta);
    }

    /**
     * Atualiza o a logica do jogo relativa aos aliens.
     */
    @Override
    public void fazLogica() {
        // inverte o movimento horizontal e abaixa a tela um pouco
        dx = -dx;
        y += 10;

        // se os aliens chegam na base da tela o jogador perde
        if (y > 570) {
            game.notificaMorte();
        }
        
        if (explodiu) {
            game.removeEntidade(this);
        }
    }

    /**
     * Notificacao de que esse alien colidiu com outra entidade.
     *
     * @param outra A outra entidade.
     */
    public void colidiuCom(Entidade outra) {
        // colisoes com robos nao sao descritas aqui
    }
    
    /**
     * Retorna a quantidade de vida atual do robô.
     * @return int contendo vida restante.
     */
    public int getVida() {
        return vida;
    }
    
    /**
     * Altera o valor de vida restante do robô.
     * @param vida int contendo vida a ser definida.
     */
    public void setVida(int vida) {
        this.vida = vida;
    }
}
