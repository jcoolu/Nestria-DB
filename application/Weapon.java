
package application;

import javafx.scene.image.ImageView;

/**
 * Weapon class. A weapon has an id (integer), name (string), attack (integer), and image.
 */
public class Weapon
{
    private int id;
    private String name;
    private int attack;
    private ImageView image;

    /*
     * Constructor for objects of class Weapon
     * i - id
     * n - name
     * a - attack
     * im - image (view)
     */
    public Weapon(int i, String n, int a, ImageView im)
    {
        id = i;
        name = n;
        attack = a;
        image = im;
    }
    
    /*
     * Returns id.
     */
    public int getId() {
        return id;
    }
    
    /*
     * Returns name.
     */
    public String getName() {
        return name;
    }
    
    /*
     * Returns attack.
     */
    public int getAttack() {
        return attack;
    }
    
    /*
     * Returns image. (view)
     */
    public ImageView getImage() {
        return image;
    }
}
