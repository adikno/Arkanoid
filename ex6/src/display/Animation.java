package display;

import biuoop.DrawSurface;

/**
 * In charge of running the game.
 */
public interface Animation {
    /**
     * Draw sprites on the surface and more.
     * @param d draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Return false if the game should keep running and true otherwise.
     * @return false if the game should keep running and true otherwise.
     */
    boolean shouldStop();

    /**
     * Prepare boolean members for reuse.
     */
    void setShouldStop();
}