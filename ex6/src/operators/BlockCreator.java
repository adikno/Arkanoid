package operators;

import instruments.Block;

/**
 * in charge of creation blocks.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos the 'x' position
     * @param ypos the 'y' position
     * @return a block
     */
     Block create(int xpos, int ypos);
}
