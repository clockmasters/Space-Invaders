package spaceinvaders.game.entidades;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spaceinvaders.game.Game;
import spaceinvaders.game.sprites.GuardaSprite;

/**
 * Entidade representando tiros.
 *
 * @author Joao Victor Carneiro
 */
public class EntidadeTiro extends Entidade {

    /**
     * A velocidade vertical na qual o tiro se move
     */
    private double velocidade = -700;
    /**
     * O jogo no qual a entidade existe
     */
    private Game game;
    /**
     * True se o tiro acertou
     */
    private boolean acertou = false;

    /**
     * Cria um novo tiro
     *
     * @param game O jogo no qual o tiro foi criado
     * @param sprite O sprite que representa o tiro
     * @param x A posicao x inicial do tiro
     * @param y A posicao y inicial do tiro
     */
    public EntidadeTiro(Game game, String sprite, int x, int y) throws IOException {
        super(sprite, x, y);

        this.game = game;

        dy = velocidade;
    }

    /**
     * Pede para que o tiro se mova baseado no tempo decorrido.
     *
     * @param delta O tempo que passou desde o ultimo movimento.
     */
    @Override
    public void mover(long delta) {
        // continua com o movimento normal
        super.mover(delta);

        // se o tiro passa o limite da tela, exclui
        if (y < -100) {
            game.removeEntidade(this);
        }
    }

    /**
     * Notificacao de que esse tiro colidiu com outra entidade.
     *
     * @param outra a outra entidade com a qual colidiu.
     */
    @Override
    public void colidiuCom(Entidade outra) {

        // previne mortes depois que algo foi atingido
        if (acertou) {
            return;
        }

        // Se um alien for atingido, morre
        if (outra instanceof EntidadeAlien) {
            // remove as entidades envolvidas
            game.removeEntidade(this);

            //Substitui a entidade alien pela entidade explosao
            try {
                outra.setSprite(GuardaSprite.sprite_get().getSprite("spaceinvaders/game/sprites/img/sprExpl.png"));
            } catch (IOException ex) {
                Logger.getLogger(EntidadeTiro.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Declara a explosão para posterior remoção
            if (outra.getTangivel()) {
                outra.setExplodiu(true);
                outra.setTangivel(false);
                // Notifica o jogo que o alien foi morto
                game.notificaAlienMorto(outra);
            }
            acertou = true;
        }

        //Se um robo for atingido 2 vezes, morre
        if (outra instanceof EntidadeRobo) {
            //Remove as entidades envolvidas
            game.removeEntidade(this);
            if (((EntidadeRobo) outra).getVida() == 1) {

                //Substitui a entidade alien pela entidade explosao
                try {
                    outra.setSprite(GuardaSprite.sprite_get().getSprite("spaceinvaders/game/sprites/img/sprExpl.png"));
                } catch (IOException ex) {
                    Logger.getLogger(EntidadeTiro.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Declara a explosão para posterior remoção
                if (outra.getTangivel()) {
                    outra.setExplodiu(true);
                    outra.setTangivel(false);

                    //Notifica o robo morto
                    game.notificaRoboMorto(outra);
                }
            } else {
                ((EntidadeRobo) outra).setVida(((EntidadeRobo) outra).getVida() - 1);
                try {
                    outra.setSprite(GuardaSprite.sprite_get().getSprite("spaceinvaders/game/sprites/img/sprrobo_1.png"));
                } catch (IOException ex) {
                    Logger.getLogger(EntidadeTiro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            acertou = true;
        }
    }
}
