package spaceinvaders.game.entidades;

import java.io.IOException;
import spaceinvaders.game.Game;

/**
 * Uma entidade que representa um dos aliens invasores.
 * 
 * @author Joao Victor Carneiro
 */
public class EntidadeAlien extends Entidade {
	/** A velocidade em que o alien se move horizontamente */
	private double moveSpeed = 75;
	/** O jogo no qual a classe entidade existe */
	private Game game;
	
	/**
	 * Cria nova entidade alien
	 * 
	 * @param game O jogo no qual a entidade esta sendo criada
	 * @param ref O sprite que deve ser usado por esse alien
	 * @param x Localizacao x inicial desse alien
	 * @param y Localizacao y inicial desse alien
	 */
	public EntidadeAlien(Game game,String ref,int x,int y) throws IOException {
		super(ref,x,y);
		
		this.game = game;
		dx = -moveSpeed;
	}

	/**
	 * Pede para que esse alien ande baseado no tempo corrido
	 * 
	 * @param delta O tempo que passou desde o ultimo movimento
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
	 * Atualiza o a logica do jogo relativa aos aliens
	 */
	public void fazLogica() {
		// inverte o movimento horizontal e abaixa a tela um pouco
		dx = -dx;
		y += 10;
		
		// se os aliens chegam na base da tela o jogador perde
		if (y > 570) {
			game.notificaMorte();
		}
	}
	
	/**
	 * Notificacao de que esse alien colidiu com outra entidade
	 * 
	 * @param outra a outra entidade
	 */
	public void colidiuCom(Entidade outra) {
		// colisoes com alien nao sao descritas aqui
	}
}