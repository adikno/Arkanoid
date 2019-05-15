package display;

import biuoop.DrawSurface;
import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import instruments.BackGround;
import instruments.Block;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * In charge of representing a 'pause-screen'.
 */
public class PauseScreen implements Animation {

    /**
     * A constructor.
     */
    public PauseScreen() {
    }

    /**
     * Representing a 'pauses-screen' until the user presses space-key.
     * @param d draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        List<Sprite> sprites = new LinkedList<>();
        Block block = new Block(new Rectangle(new Point(0, 0), 800, 600),
                Color.GRAY);
        sprites.add(block);
        Circle circle = new Circle(new Point(800 / 2, 600 / 2),
                100, Color.white, true);
        sprites.add(circle);
        circle = new Circle(new Point(800 / 2, 600 / 2),
                100, Color.black, false);
        sprites.add(circle);
        block = new Block(new Rectangle(new Point(800 / 2 - 50, 600 / 2 - 60),
                40, 120), Color.black);
        sprites.add(block);
        block = new Block(new Rectangle(new Point(800 / 2 + 10, 600 / 2 - 60),
                40, 120), Color.black);
        sprites.add(block);
        BackGround backGround = new BackGround(sprites);
        backGround.drawOn(d);
        d.setColor(Color.black);
        d.drawText(240, d.getHeight() / 4 * 3, "Press space to continue", 32);
    }

    /**
     * Return false if the game should keep running and true otherwise.
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() { return false; }

    /**
     * Prepare boolean members for reuse.
     */
    public void setShouldStop() {

    }
}