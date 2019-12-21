package application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import java.util.*;
import javafx.scene.control.TableView;
import javafx.collections.*;
import javafx.scene.image.ImageView;


/**
 * Write a description of class Weapon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Scenery
{
    private int id;
    private String region;
    private String effect;
    private ImageView image;

    /**
     * Constructor for objects of class Weapon
     */
    public Scenery(int i, String n, String e, ImageView b)
    {
        id = i;
        region = n;
        effect = e;
        image = b;
    }
    
    public int getId() {
        return id;
    }
    
    public String getRegion() {
        return region;
    }
    
    public String getEffect() {
        return effect;
    }
    
    public ImageView getImage() {
        return image;
    }
}
