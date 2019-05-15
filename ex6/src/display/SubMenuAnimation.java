package display;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import instruments.BackGround;
import instruments.Block;
import instruments.Selection;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * the sub-menu animation.
 */
public class SubMenuAnimation implements Menu<Task<Void>> {
    private List<Selection> selections;
    private KeyboardSensor sensor;
    private boolean shouldStop;
    private Task<Void> status;
    private boolean isAlreadyPressed;

    /**
     * a constructor.
     * @param sensor a keyboard sensor
     */
    public SubMenuAnimation(KeyboardSensor sensor) {
        this.selections = new LinkedList<>();
        this.sensor = sensor;
        this.shouldStop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * In charge of enter new selection option to the menu.
     * @param key a key to press
     * @param message a message
     * @param returnVal the outcome of the selection
     */
    public void addSelection(String key, String message, Task<Void> returnVal) {
        this.selections.add(new Selection(key, message, returnVal));
    }

    /**
     * return the current selection of the user.
     * @return the current selection of the user.
     */
    public Task<Void> getStatus() {
        return this.status;
    }

    /**
     * In charge of enter new sub-menu to the menu.
     * @param key a key to press
     * @param message a message
     * @param subMenu the submenu
     */
    public void addSubMenu(String key, String message, Menu<Task<Void>> subMenu) {

    }

    /**
     * Draw sprites on the surface and more.
     *
     * @param d  draw-surface.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        List<Sprite> sprites = new LinkedList<>();
        Block block = new Block(new geometry.Rectangle(new Point(0, 0), 800,
                600), Color.GRAY);
        sprites.add(block);
        BackGround backGround = new BackGround(sprites);
        backGround.drawOn(d);
        d.setColor(Color.YELLOW);
        d.drawText(40, 40 , "Level Sets", 35);
        d.setColor(Color.GREEN);
        int i = d.getHeight() / 4;
        for (Selection s: this.selections) {
            d.drawText(100, i, "(" + s.getKey() + ") " + s.getMessage(), 30);
            i += 35;
        }
        for (Selection s: this.selections) {
            if (this.isAlreadyPressed && this.sensor.isPressed(s.getKey())) {
                return;
            }
            if (this.sensor.isPressed(s.getKey()) && !this.isAlreadyPressed) {
                this.status = s.getReturnVal();
                this.shouldStop = true;
                return;
            }
        }
        this.isAlreadyPressed = false;
    }

    /**
     * Return false if the game should keep running and true otherwise.
     *
     * @return false if the game should keep running and true otherwise.
     */
    public boolean shouldStop() {
        return this.shouldStop;
    }

    /**
     * restart the boolean members for re-use.
     */
    public void setShouldStop() {
        this.shouldStop = false;
        this.isAlreadyPressed = true;
    }
}
