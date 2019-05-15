import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

/**
 * @author Adi Knobel 209007087
 * @version "1.0, 30/03/18"
 */
public class BouncingBallAnimation {
    /**The function runs a bouncing ball animation.
     * @param args an array of parameters to main
     */
    public static void main(String[] args) {
        GUI gui = new GUI("title", Frame.DEFAULT_WIDTH, Frame.DEFAULT_HEIGHT);
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        //creates a random point inside the borders.
        Random rand = new Random(); // create a random-number generator
        int x = rand.nextInt(Frame.DEFAULT_WIDTH - 1) + 1; // get integer in range 1-800
        int y = rand.nextInt(Frame.DEFAULT_HEIGHT - 1) + 1; // get integer in range 1-600
        Point p = new Point(x, y);
        Ball ball = new Ball(p, 30, java.awt.Color.BLACK);
        //set an arbitrary velocity to the ball
        int angle = rand.nextInt(359) + 1; // get integer in range 1-360
        ball.setVelocity(Velocity.fromAngleAndSpeed(angle, 20));
        Frame frame = new Frame(0, 0, Frame.DEFAULT_WIDTH, Frame.DEFAULT_HEIGHT, Color.BLACK);
        ball.setFrame(frame);
        //draws the ball on the surface, shows the surface, waits for 50 milliseconds and so on to create an animation
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
