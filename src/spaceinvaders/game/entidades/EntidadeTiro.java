package spaceinvaders.game.entidades;

import spaceinvaders.game.Game;

/**
 * Entidade representando o tiro
 * 
 * @author Joao Victor Carneiro
 */
public class EntidadeTiro extends Entidade {
	/** A velocidade vertical na qual o tiro se move */
	private double velocidade = -300;
	/** O jogo no qual a entidade existe */
	private Game game;
	/** True se o tiro acertou */
	private boolean acertou = false;
	
	/**
	 * Cria um novo tiro
	 * 
	 * @param game O jogo no qual o tiro foi criado
	 * @param sprite O sprite que representa o tiro 
	 * @param x A posicao x inicial do tiro
	 * @param y A posicao y inicial do tiro
	 */
	public EntidadeTiro(Game game,String sprite,int x,int y) {
		super(sprite,x,y);
		
		this.game = game;
		
		dy = velocidade;
	}

	/**
	 * Pede para que o tiro se move baseado no tempo corrido
	 * 
	 * @param delta O tempo que passou desde o ultimo movimento
	 */
	public void mover(long delta) {
		// continua com o movimento normal
		super.mover(delta);
		
		// se o tiro passa o limite da tela, exclui
		if (y < -100) {
			game.removeEntidade(this);
		}
	}
	
	/**
	 * Notificacao de que esse tiro colidiu com outra entidade
	 * 
	 * @param outra a outra entidade com a qual colidiu
	 */
	public void colidiuCom(Entidade outra) {
		// previne mortes depois que algo foi atingido
		if (acertou) {
			return;
		}
		
		// Se um alien for atingido, morre
		if (outra instanceof EntidadeAlien) {
			// remove as entidades envolvidas
			game.removeEntidade(this);
			game.removeEntidade(outra);
			
			// Notifica o jogo que o alien foi morto
			game.notificaAlienMorto();
			acertou = true;
		}
	}
}