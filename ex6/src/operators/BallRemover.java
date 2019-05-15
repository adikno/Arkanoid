package operators;

import game.GameLevel;
import instruments.Ball;
import instruments.Block;

/**
 * A BallRemover is in charge of removing balls from the game as well as keeping count of the number of blocks
 * that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * A constructor of BallRemover class.
     * @param game the game.
     * @param removedBalls a counter represents the number of the remaining balls.
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the being-hit block
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (this.remainingBalls.getValue() == 1) {
            beingHit.removeHitListener(this.game);
        }
        this.remainingBalls.decrease(1);
        hitter.removeFromGame(this.game);
    }
}