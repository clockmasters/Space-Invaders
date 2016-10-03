package spaceinvaders.game.entidades;

import spaceinvaders.game.Game;

/**
 * A entidade que representa a nave do jogador
 * 
 * @author Joao Victor Carneiro
 */
public class EntidadeNave extends Entidade {
	/** O jogo no qual a entidade existe */
	private Game game;
	
	/**
	 * Cria uma nova entidade para representar a nave do jogador
	 *  
	 * @param game O jogo no qual a entidade vai ser criada
	 * @param ref A referencia ao sprite da nave
	 * @param x Posicao x inicial da nave
	 * @param y Posicao y inicial da nave
	 */
	public EntidadeNave(Game game,String ref,int x,int y) {
		super(ref,x,y);
		
		this.game = game;
	}
	
	/**
	 * Pede para que a nave se move baseado no tempo corrido
	 * 
	 * @param delta Tempo passado desde o ultimo movimento(ms)
	 */
	public void mover(long delta) {
		// se esta se movendo para esquerda mas atinge o limite esquerdo
		// da tela, nao se move
		if ((dx < 0) && (x < 10)) {
			return;
		}
		// esta se movendo para a direita no limite diteito dda tela,
		// nao se move
		if ((dx > 0) && (x > 750)) {
			return;
		}
		
		super.mover(delta);
	}
	
	/**
	 * Notificacao de que a nave do jogador colidiu com algo
	 * 
	 * @param outra A entidade com a qual a nave colidiu
	 */
        
	public void colidiuCom(Entidade outra) {
		// se for um alien, notifica o jogo que o jogador morreu
		if (outra instanceof EntidadeAlien) {
			game.notificaMorte();
		}
	}
}