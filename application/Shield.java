package application;

import javafx.scene.image.ImageView;

/**
 * Shield class. A shield has an id, name, defense, and image (view).
 */
public class Shield
{
    private int id;
    private String name;
    private int defense;
    private ImageView image;

    /*
     * Constructor for objects of class Shield.
     * i - id
     * n - name
     * d - defense
     * im - image (view)
     */
    public Shield(int i, String n, int d, ImageView im)
    {
        id = i;
        name = n;
        defense = d;
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
     * Returns defense.
     */
    public int getDefense() {
        return defense;
    }

    /*
     * Returns image. (view)
     */
    public ImageView getImage() {
        return image;
    }

}
