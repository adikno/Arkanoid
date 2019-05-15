package instruments;

import biuoop.DrawSurface;
import display.Sprite;
import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import operators.Collidable;
import operators.HitListener;
import operators.HitNotifier;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adi Knobel 209007087
 * @version "1.0, 17/04/18"
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle block;
    private Color fill;
    private Color border;
    private List<Color> colors;
    private List<Image> images;
    private int numOfHits;
    private int initialNumOfHits;
    private List<HitListener> hitListeners;
    private Image img;
    private List<Object> filling;

    /**
     * This function is a constructor of class Block.
     * @param rec a rectangle object holds details about the size and the place of the block
     * @param c the color of the block
     */
    public Block(Rectangle rec, Color c) {
        this.block = rec;
        this.fill = c;
        this.numOfHits = -1;
        this.hitListeners = new ArrayList<>();
        this.img = null;
        this.border = null;
        this.filling = new LinkedList<>();
        this.images = new LinkedList<>();
        this.colors = new LinkedList<>();
    }

    /**
     * This function is a constructor of class Block.
     * @param rec a rectangle object holds details about the size and the place of the block
     * @param img the image of the block
     */
    public Block(Rectangle rec, Image img) {
        this.block = rec;
        this.img = img;
        this.border = null;
        this.fill = null;
        this.numOfHits = -1;
        this.hitListeners = new ArrayList<>();
        this.filling = new LinkedList<>();
        this.images = new LinkedList<>();
        this.colors = new LinkedList<>();
    }

    /**
     * This function is a constructor of class Block.
     * @param rec a rectangle object holds details about the size and the place of the block
     * @param c the color of the block
     * @param numOfHits the number of hits till the block will be removed
     */
    public Block(Rectangle rec, Color c, int numOfHits) {
        this.block = rec;
        this.fill = c;
        this.hitListeners = new ArrayList<>();
        this.img = null;
        this.border = null;
        this.numOfHits = numOfHits;
        this.initialNumOfHits = numOfHits;
        this.filling = new LinkedList<>();
        this.images = new LinkedList<>();
        this.colors = new LinkedList<>();
    }

    /**
     * This function is a constructor of class Block.
     * @param rec a rectangle object holds details about the size and the place of the block
     * @param img the image of the block
     * @param numOfHits the number of hits till the block will be removed
     */
    public Block(Rectangle rec, Image img, int numOfHits) {
        this.block = rec;
        this.img = img;
        this.border = null;
        this.fill = null;
        this.hitListeners = new ArrayList<>();
        this.numOfHits = numOfHits;
        this.initialNumOfHits = numOfHits;
        this.filling = new LinkedList<>();
        this.images = new LinkedList<>();
        this.colors = new LinkedList<>();
    }

    /**
     * This function is a constructor of class Block.
     * @param rec a rectangle object holds details about the size and the place of the block
     * @param fill a lists of changing fillings of the block
     * @param numOfHits the number of hits till the block will be removed
     */
    public Block(Rectangle rec, List<Object> fill, int numOfHits) {
        this.block = rec;
        this.filling = fill;
        this.img = null;
        this.border = null;
        this.fill = null;
        this.hitListeners = new ArrayList<>();
        this.numOfHits = numOfHits;
        this.initialNumOfHits = numOfHits;
        this.images = new LinkedList<>();
        this.colors = new LinkedList<>();
    }

    /**
     * adding new fillings for the block.
     * @param c a lists of changing fillings of the block
     */
    public void setAdditionalColor(List<Color> c) {
        this.colors = c;
        this.images = null;
    }

    /**
     * adding new fillings for the block.
     * @param imgs a lists of changing fillings of the block
     */
    public void setAdditionalImg(List<Image> imgs) {
        this.images = imgs;
        this.colors = new LinkedList<>();
    }

    /**
     * a setter.
     * @param color a color for the block
     */
    public void setBorder(Color color) {
        this.border = color;
    }

    /**
     * a getter.
     * @return the width of the block
     */
    public double getWidth() {
        return this.block.getWidth();
    }

    /**
     * sets the maximal number of hits the block can absorb.
     * @param num the maximal number of hits the block can absorb
     */
    public void setNumOfHits(int num) {
        this.numOfHits = num;
    }

    /**
     * Return the current number of hits the block can absorb.
     * @return the current number of hits the block can absorb.
     */
    public int getNumOfHits() {
        return this.numOfHits;
    }

    /**
     * Return the "collision shape" of the object.
     * @return the "collision shape" of the object
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * checks whether the collision point is a corner of the block.
     * if so treat the ball's velocity accordingly.
     * @param collisionPoint the collision point
     * @param currentVelocity the current velocity of the ball
     * @return a new velocity the ball should gets
     */
    public Velocity checkCorners(Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        /*If the collision point is the upper-left corner of the block -
          if the ball is coming from upper-left - change the dx and the dy,
          otherwise - change only one of them.
          Do the same for the next corners.
         */
        if (collisionPoint.equals(this.block.getUpperLeft())) {
            if (currentVelocity.getDx() > 0 && currentVelocity.getDy() > 0) {
                return new Velocity(-newDx, -newDy);
            } else if (currentVelocity.getDx() < 0) {
                return new Velocity(newDx, -newDy);
            }
            return new Velocity(-newDx, newDy);
        }
        if (collisionPoint.equals(this.block.getUpperRight())) {
            if (currentVelocity.getDx() < 0 && currentVelocity.getDy() > 0) {
                return new Velocity(-newDx, -newDy);
            } else if (currentVelocity.getDx() > 0) {
                return new Velocity(newDx, -newDy);
            }
            return new Velocity(-newDx, newDy);
        }
        if (collisionPoint.equals(this.block.getLowerRight())) {
            if (currentVelocity.getDx() < 0 && currentVelocity.getDy() < 0) {
                return new Velocity(-newDx, -newDy);
            } else if (currentVelocity.getDx() > 0) {
                return new Velocity(newDx, -newDy);
            }
            return new Velocity(-newDx, newDy);
        }
        if (collisionPoint.equals(this.block.getLowerLeft())) {
            if (currentVelocity.getDx() > 0 && currentVelocity.getDy() < 0) {
                return new Velocity(-newDx, -newDy);
            } else if (currentVelocity.getDx() < 0) {
                return new Velocity(newDx, -newDy);
            }
            return new Velocity(-newDx, newDy);
        }
        //if the collision point isn't one of the corners - return null
        return null;
    }

    /**
     * calculates the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * (while considering a possible deviation of Line.DEVIATION in the calculation of the collision point).
     * @param collisionPoint the collision point
     * @param currentVelocity the current velocity of the ball
     * @param hitter the Ball that's doing the hitting.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        //checks if the collision occur at a corner of the block
        Velocity temp = this.checkCorners(collisionPoint, currentVelocity);
        if (temp != null) {
            return temp;
        }
        //if the intersection is on the left side of the block and the ball's direction is to the intersection
        //else if the intersection is on the right side of the block and the ball's direction is to the intersection
        if (collisionPoint.getX() - Line.DEVIATION <= this.block.getUpperLeft().getX()
                && collisionPoint.getY() > this.block.getUpperLeft().getY()
                && collisionPoint.getY() < this.block.getLowerLeft().getY() && currentVelocity.getDx() > 0) {
            newDx *= -1;
        } else if (collisionPoint.getX() + Line.DEVIATION >= this.block.getUpperRight().getX()
                && collisionPoint.getY() > this.block.getUpperRight().getY()
                && collisionPoint.getY() < this.block.getLowerRight().getY() && currentVelocity.getDx() < 0) {
            newDx *= -1;
        }
        //if the intersection is on the top side of the block and the ball's direction is to the intersection
        //else if the intersection is on the bottom side of the block and the ball's direction is to the intersection
        if (collisionPoint.getY() - Line.DEVIATION <= this.block.getUpperLeft().getY()
                && collisionPoint.getX() > this.block.getUpperLeft().getX()
                && collisionPoint.getX() < this.block.getUpperRight().getX() && currentVelocity.getDy() > 0) {
            newDy *= -1;
        } else if (collisionPoint.getY() + Line.DEVIATION >= this.block.getUpperLeft().getY()
                && collisionPoint.getX() > this.block.getLowerLeft().getX()
                && collisionPoint.getX() < this.block.getLowerRight().getX() && currentVelocity.getDy() < 0) {
            newDy *= -1;
        }
        this.notifyHit(hitter);
        return new Velocity(newDx, newDy);
    }

    /**
     * draw the sprite to the screen in accordance to the current numOfHits and the instructions in the file.
     * @param d a draw surface
     */
    public void drawOn(DrawSurface d) {
        if (this.numOfHits == -1 && this.fill != null) {
            d.setColor(this.fill);
            this.block.fillRec(d);
        } else if (!this.filling.isEmpty()) {
            if (this.filling.get(this.numOfHits - 1) instanceof Color) {
                d.setColor((Color) this.filling.get(this.numOfHits - 1));
                this.block.fillRec(d);
            } else {
                d.drawImage((int) this.block.getUpperLeft().getX(),
                        (int) this.block.getUpperLeft().getY(), (Image) this.filling.get(this.numOfHits - 1));
            }
        } else if (!this.colors.isEmpty()) {
            d.setColor(this.colors.get(this.numOfHits - 1));
            this.block.fillRec(d);
        } else if (!this.images.isEmpty()) {
            d.drawImage((int) this.block.getUpperLeft().getX(),
                    (int) this.block.getUpperLeft().getY(), this.images.get(this.numOfHits - 1));
        } else if (this.fill != null) {
            d.setColor(this.fill);
            this.block.fillRec(d);
        } else {
            d.drawImage((int) this.block.getUpperLeft().getX(), (int) this.block.getUpperLeft().getY(), this.img);
        }
        if (this.border != null) {
            d.setColor(this.border);
            this.block.drawRec(d);
        }
    }

    /** notify the sprite that time has passed.
     * @param dt specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }

    /**
     * adds the block to the game.
     * @param game a game object.
     */
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Removing the block from the game.
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        this.removeHitListener(game);
        this.numOfHits = this.initialNumOfHits;
        this.hitListeners = new LinkedList<>();
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Adding a hit-listener to the block.
     * @param hl new hit-listener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removing a hit-listener from the block.
     * @param hl old hit-listener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notify all the hit-listeners that a hit event happened.
     * @param hitter the Ball that's doing the hitting.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
