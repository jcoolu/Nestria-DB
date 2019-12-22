package application;

import javafx.scene.image.Image;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
    private Image image;

    /**
     * Constructor for objects of class Player
     */
    public Player(int i, String n, int h, int a, int d, String w, String s, String g, String k, String t, Image im)
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getShield() {
        return shield;
    }

    public String getGoal() {
        return goal;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getTribe() {
        return tribe;
    }

    public Image getImage() {
        return image;
    }
}
