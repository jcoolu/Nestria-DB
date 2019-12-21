package application;

import javafx.scene.image.ImageView;

/**
 * Write a description of class Creature here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Creature
{
    private int id;
    private String name;
    private int health;
    private String behavior;
    private int attack;
    private int defense;
    private String size;
    private String habitat;
    private String dragonColor;
    private String dragonSpecies;
    private String wingspan;
    private String weapon;
    private String trollColor;
    private String trollSpecies;
    private String wolfColor;
    private String wolfSpecies;
    private ImageView image;
    
    
    public Creature(int i, String n, int h, String b, int a, int d, String s, String ha, 
    String dc, String ds, String wing, String w, String tc, String ts, String wc, String ws, ImageView im )
    {
        id = i;
        name = n;
        health = h;
        behavior = b;
        attack = a;
        defense = d;
        size = s;
        habitat = ha;
        dragonColor = dc;
        dragonSpecies = ds;
        wingspan = wing;
        weapon = w;
        trollColor = tc;
        trollSpecies = ts;
        wolfColor = wc;
        wolfSpecies = ws;
        image = im;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public String getBehavior() {
        return behavior;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public String getSize() {
        return size;
    }
    
    public String getHabitat() {
        return habitat;
    }
    
    public String getDragonColor() {
        return dragonColor;
    }
    
    public String getDragonSpecies() {
        return dragonSpecies;
    }
    
    public String getWingspan() {
        return wingspan;
    }
    
    public String getWeapon() {
        return weapon;
    }
    
    public String getTrollColor() {
        return trollColor;
    }
    
    public String getTrollSpecies() {
        return trollSpecies;
    }
    
    public String getWolfColor() {
        return wolfColor;
    }
    
    public String getWolfSpecies() {
        return wolfSpecies;
    }
    
    public ImageView getImage() {
        return image;
    }
}
