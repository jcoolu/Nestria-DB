
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



/**
 * Write a description of class Weapon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weapon
{
    private int id;
    private String name;
    private int attack;
    private ImageView image;

    /**
     * Constructor for objects of class Weapon
     */
    public Weapon(int i, String n, int a, ImageView im)
    {
        id = i;
        name = n;
        attack = a;
        image = im;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public ImageView getImage() {
        return image;
    }
}
