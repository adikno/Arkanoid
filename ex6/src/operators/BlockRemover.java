package operators;

import game.GameLevel;
import instruments.Block;
import instruments.Ball;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * A constructor of class BlockRemover.
     * @param game the game
     * @param removedBlocks the remaining blocks counter
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * Blocks that are hit and reach 0 hit-points should be removed from the game.
     * @param beingHit the being-hit block
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //subtract one from the potentially number-of-hits.
        if (beingHit.getNumOfHits() != -1) {
            beingHit.setNumOfHits(beingHit.getNumOfHits() - 1);
            //Block that reach 0 hit-points removed from the game
            if (beingHit.getNumOfHits() == 0) {
                this.remainingBlocks.decrease(1);
                beingHit.removeFromGame(this.game);
            }
        }
    }
}