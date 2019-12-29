package application;

import java.util.*;
import javafx.scene.image.ImageView;

/**
 * Scenery class. Creates a Scenery which has an id (integer), region (string), 
 * effect (string), and an image. 
 */
public class Scenery
{
    private int id; 
    private String region;
    private String effect;
    private ImageView image;

    /*
     * Constructor for objects of class Scenery.
     * i - id of Scenery
     * n - region of Scenery
     * e - effect of Scenery
     * b - image of Scenery (view)
     */
    public Scenery(int i, String n, String e, ImageView b)
    {
        id = i;
        region = n;
        effect = e;
        image = b;
    }
    
    /*
     * Returns id.
     */
    public int getId() {
        return id;
    }
    
    /*
     * Returns region. 
     */
    public String getRegion() {
        return region;
    }
    
    /*
     * Returns effect.
     */
    public String getEffect() {
        return effect;
    }
    
    /*
     * Returns image.(view)
     */
    public ImageView getImage() {
        return image;
    }
}
