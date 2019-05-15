package game;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import operators.Collidable;
import operators.CollisionInfo;

import java.util.LinkedList;

/**
 * @author Adi Knobel 209007087
 * @version "2.0, 17/04/18"
 */
public class GameEnvironment {
    /**
     * maximal distance between two points.
     */
    public static final int MAX_DISTANCE = 800;

    private LinkedList<Collidable> list;

    /**
     * This function is a constructor of class GameEnvironment.
     */
    public GameEnvironment() {
        this.list = new LinkedList<>();
    }

    /**
     * add the given collidable to the environment.
     * @param c a collidable object
     */
    public void addCollidable(Collidable c) {
        this.list.add(c);
    }

    /**
     * Removing a collidable object from the environment.
     * @param c the collidable object.
     */
    public void removeCollidable(Collidable c) {
        this.list.remove(c);
    }

    /**
     * draws all the collidable objects in the environment.
     * @param d a draw surface
     */
    public void drawEnvironment(DrawSurface d) {
        LinkedList<Collidable> collidables = new LinkedList<>(this.list);
        for (Collidable coll: collidables) {
            coll.getCollisionRectangle().drawRec(d);
        }
    }

    /**
     * If the ball will not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     * @param trajectory a line starting at current location of the ball
     *                   and ending where the velocity will take the ball if no collisions will occur
     * @return information about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        int i = 0;
        CollisionInfo collInf = null;
        double minDistance = MAX_DISTANCE;
        //walk through all the collidable objects and checks whether the ball will collide with them.
        for (Object obj: this.list) {
            Point p = trajectory.closestIntersectionToStartOfLine(this.list.get(i).getCollisionRectangle());
            //if there is an intersection point - checks whether it the closest one
            if (p != null) {
                double d = trajectory.getStart().distance(p);
                if (d < minDistance) {
                    minDistance = d;
                    collInf = new CollisionInfo(p, this.list.get(i));
                }
            }
            i++;
        }
        return collInf;
    }
}
