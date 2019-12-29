package application;

import javafx.scene.image.ImageView;

/**
 * Creature class. (includes all creatures: dragon, troll, skeleton, wolf)
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
    
    /*
     * Constructor for objects of class Creature.
     * i - id
     * n - name
     * h - health 
     * b - behavior
     * a - attack
     * d - defense
     * s - size
     * ha - habitat
     * dc - dragon color
     * ds - dragon species
     * wing - wingspan
     * w - weapon
     * tc - troll color
     * ts - troll species
     * wc - wolf color
     * es - wolf species
     * im - image (view)
     */
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
     * Returns health.
     */
    public int getHealth() {
        return health;
    }
    
    /*
     * Returns behavior.
     */
    public String getBehavior() {
        return behavior;
    }
    
    /*
     * Returns attack.
     */
    public int getAttack() {
        return attack;
    }
    
    /*
     * Returns defense.
     */
    public int getDefense() {
        return defense;
    }
    
    /*
     * Returns size.
     */
    public String getSize() {
        return size;
    }
    
    /*
     * Returns habitat.
     */
    public String getHabitat() {
        return habitat;
    }
    
    /*
     * Returns dragon color.
     */
    public String getDragonColor() {
        return dragonColor;
    }
    
    /*
     * Returns dragon species.
     */
    public String getDragonSpecies() {
        return dragonSpecies;
    }
    
    /*
     * Returns wingspan.
     */
    public String getWingspan() {
        return wingspan;
    }
    
    /*
     * Returns weapon.
     */
    public String getWeapon() {
        return weapon;
    }
    
    /*
     * Returns troll color.
     */
    public String getTrollColor() {
        return trollColor;
    }
    
    /*
     * Returns troll species.
     */
    public String getTrollSpecies() {
        return trollSpecies;
    }
    
    /*
     * Returns wolf color.
     */
    public String getWolfColor() {
        return wolfColor;
    }
    
    /*
     * Returns wolf species.
     */
    public String getWolfSpecies() {
        return wolfSpecies;
    }
    
    /*
     * Returns image. (view)
     */
    public ImageView getImage() {
        return image;
    }
}
