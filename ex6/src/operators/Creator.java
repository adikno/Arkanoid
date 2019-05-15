package operators;

import geometry.Point;
import geometry.Rectangle;
import instruments.Block;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * a block creator.
 */
public class Creator implements BlockCreator {
    private int height;
    private int width;
    private int hitPoints;
    private List<Color> colors;
    private Color stroke;
    private List<Image> images;
    private List<Object> fill;

    /**
     * a constructor.
     * @param definitions a map of qualities related to the current type of block
     * @param defaultDef a map of default qualities
     */
    public Creator(Map<String, Object> definitions, Map<String, Object> defaultDef) {
        this.images = new LinkedList<>();
        this.colors = new LinkedList<>();
        this.fill = new LinkedList<>();
        this.stroke = null;

        try {
            if (definitions.containsKey("height")) {
                this.height = (int) definitions.get("height");
            } else {
                this.height = (int) defaultDef.get("height");
            }
            if (definitions.containsKey("width")) {
                this.width = (int) definitions.get("width");
            } else {
                this.width = (int) defaultDef.get("width");
            }
            if (definitions.containsKey("hit_points")) {
                this.hitPoints = (int) definitions.get("hit_points");
            } else {
                this.hitPoints = (int) defaultDef.get("hit_points");
            }
            if (definitions.containsKey("fillImg")) {
                this.images.add((Image) definitions.get("fillImg"));
            } else if (definitions.containsKey("fillColor")) {
                this.colors.add((Color) definitions.get("fillColor"));
            } else if (defaultDef.containsKey("fillImg")) {
                this.images.add((Image) defaultDef.get("fillImg"));
            } else if (defaultDef.containsKey("fillColor")) {
                this.colors.add((Color) defaultDef.get("fillColor"));
            }
            if (definitions.containsKey("strokeColor")) {
                this.stroke = (Color) definitions.get("strokeColor");
            } else if (defaultDef.containsKey("strokeColor")) {
                this.stroke = (Color) defaultDef.get("strokeColor");
            }
            for (int i = 1; i <= this.hitPoints; i++) {
                if (definitions.containsKey("fill-" + String.valueOf(i) + "Img")) {
                    this.images.add((Image) definitions.get("fill-" + String.valueOf(i) + "Img"));
                } else if (defaultDef.containsKey("fill-" + String.valueOf(i) + "Img")) {
                    this.images.add((Image) defaultDef.get("fill-" + String.valueOf(i) + "Img"));
                }
                if (definitions.containsKey("fill-" + String.valueOf(i) + "Color")) {
                    this.colors.add((Color) definitions.get("fill-" + String.valueOf(i) + "Color"));
                } else if (defaultDef.containsKey("fill-" + String.valueOf(i) + "Color")) {
                    this.colors.add((Color) defaultDef.get("fill-" + String.valueOf(i) + "Color"));
                }
            }
            if (!this.images.isEmpty() && !this.colors.isEmpty()) {
                if (definitions.containsKey("fillImg")) {
                    this.fill.add(definitions.get("fillImg"));
                } else if (definitions.containsKey("fillColor")) {
                    this.fill.add(definitions.get("fillColor"));
                } else if (defaultDef.containsKey("fillImg")) {
                    this.fill.add(definitions.get("fillImg"));
                } else if (defaultDef.containsKey("fillColor")) {
                    this.fill.add(definitions.get("fillColor"));
                }
                for (int i = 1; i <= this.hitPoints; i++) {
                    if (definitions.containsKey("fill-" + String.valueOf(i) + "Img")) {
                        this.fill.add(definitions.get("fill-" + String.valueOf(i) + "Img"));
                    } else if (defaultDef.containsKey("fill-" + String.valueOf(i) + "Img")) {
                        this.fill.add(defaultDef.get("fill-" + String.valueOf(i) + "Img"));
                    }
                    if (definitions.containsKey("fill-" + String.valueOf(i) + "Color")) {
                        this.fill.add(definitions.get("fill-" + String.valueOf(i) + "Color"));
                    } else if (defaultDef.containsKey("fill-" + String.valueOf(i) + "Color")) {
                        this.fill.add(defaultDef.get("fill-" + String.valueOf(i) + "Color"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Some of the definitions are missing or incompatible.");
            System.exit(0);
        }
    }

    /**
     * Create a block at the specified location.
     * @param xpos the 'x' position
     * @param ypos the 'y' position
     * @return a block
     */
    public Block create(int xpos, int ypos) {
        Block b = null;
        if (!this.images.isEmpty() && !this.colors.isEmpty()) {
            b = new Block(new Rectangle(new Point(xpos, ypos), this.width, this.height), this.fill, this.hitPoints);
        } else if (!this.images.isEmpty()) {
            b = new Block(new Rectangle(new Point(xpos, ypos), this.width, this.height), this.images.get(0),
                    this.hitPoints);
            if (this.images.size() > 1) {
                b.setAdditionalImg(this.images);
            }
        } else {
            b = new Block(new Rectangle(new Point(xpos, ypos), this.width, this.height), this.colors.get(0),
                    this.hitPoints);
            if (this.colors.size() > 1) {
                b.setAdditionalColor(this.colors);
            }
        }
        if (this.stroke != null) {
            b.setBorder(this.stroke);
        }
        return b;
    }
}
