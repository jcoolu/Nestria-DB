package application;

import javafx.scene.image.ImageView;

/**
 * Player class. (includes all Knights and Vikings)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int id;
    private String name;
    private int health;
    private int attack;
    private int defense;
    private String weapon;
    private String shield;
    private String goal;
    private String kingdom;
    private String tribe;
    private ImageView image;

    /**
     * Constructor for objects of class Player
     * i - id
     * n - name
     * h - health 
     * a - attack
     * d - defense 
     * w - weapon
     * s - shield
     * g - goal
     * k - kingdom
     * t - tribe
     * im - image (view)
     */
    public Player(int i, String n, int h, int a, int d, String w, String s, String g, String k, String t, ImageView im)
    {
        id =i;
        name = n;
        health = h;
        attack = a;
        defense = d;
        weapon = w;
        shield = s;
        goal = g;
        kingdom = k;
        tribe = t;
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
     * Returns weapon.
     */
    public String getWeapon() {
        return weapon;
    }

    /*
     * Returns shield.
     */
    public String getShield() {
        return shield;
    }

    /*
     * Returns goal.
     */
    public String getGoal() {
        return goal;
    }

    /*
     * Returns kingdom.
     */
    public String getKingdom() {
        return kingdom;
    }

    /*
     * Returns tribe.
     */
    public String getTribe() {
        return tribe;
    }

    /*
     * Returns image. (view)
     */
    public ImageView getImage() {
        return image;
    }
}
