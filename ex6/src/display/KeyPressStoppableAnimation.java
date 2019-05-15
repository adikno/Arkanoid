package display;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * in charge of union KeyPressStoppableAnimation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String endkey;
    private Animation animation;
    private Boolean isAlreadyPressed;
    private Boolean stop;

    /**
     * a constructor.
     * @param sensor a keyboard sensor
     * @param key a key should be pressed in order to stop the animation
     * @param animation the stop-able animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.animation = animation;
        this.keyboard = sensor;
        this.endkey = key;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * prepare the boolean members for reuse.
     */
    public void setShouldStop() {
        this.stop = false;
        if (this.keyboard.isPressed(this.endkey)) {
            this.isAlreadyPressed = true;
        } else {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * Draw sprites on the surface and more.
     *
     * @param d  draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.isAlreadyPressed && this.keyboard.isPressed(this.endkey)) {
            return;
        }
        if (this.isAlreadyPressed && !this.keyboard.isPressed(this.endkey)) {
            this.isAlreadyPressed = false;
            return;
        }
        if (this.keyboard.isPressed(this.endkey)) {
            this.stop = true;
        }
    }

    /**
     * Return false if the game should keep running and true otherwise.
     *
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
