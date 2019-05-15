package operators;

import instruments.Block;

import java.util.Map;

/**
 * in charge of making blocks.
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;
    private int blocksStartX;
    private int currX;
    private int currY;
    private int blocksStartY;
    private int rowHeight;

    /**
     * a constructor.
     * @param spacers a list of spacers.
     * @param creators a map of block-creators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacers, Map<String, BlockCreator> creators) {
        this.spacerWidths = spacers;
        this.blockCreators = creators;
    }

    /**
     * update the position of 'x'.
     * @param advance the size of the movement.
     */
    public void setCurrX(int advance) {
        this.currX = this.currX + advance;
    }

    /**
     * reset 'x' position.
     */
    public void resetCurrX() {
        this.currX = this.blocksStartX;
    }

    /**
     * update the position of 'y'.
     */
    public void setCurrY() {
        this.currY = this.currY + this.rowHeight;
    }

    /**
     * a getter.
     * @return the current 'x' position
     */
    public int getCurrX() {
        return this.currX;
    }

    /**
     * a getter.
     * @return the current 'y' position
     */
    public int getCurrY() {
        return this.currY;
    }

    /**
     * adding qualities related to the block layout.
     * @param blocksX the initial 'x' position
     * @param blocksY the initial 'y' position
     * @param row the row-height
     */
    public void addDefinitions(int blocksX, int blocksY, int row) {
        this.blocksStartX = blocksX;
        this.blocksStartY = blocksY;
        this.currX = blocksStartX;
        this.currY = blocksStartY;
        this.rowHeight = row;
    }

    /**
     * returns true if 's' is a valid space symbol.
     * @param s a symbol
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     * @param s a symbol
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     * @param s a symbol
     * @param xpos 'x' position
     * @param ypos 'y' position
     * @return a block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s a symbol
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}