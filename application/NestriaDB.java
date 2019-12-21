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
import javafx.scene.image.*;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class NestriaDB {
    private Connection conn;
    private boolean isopen;

    //Variables used in Arena
    private String sql, playername, playerdefense, playerattack, playerweapon, playershield, playerhealth;
    private String creaturename, creatureattack, creaturedefense, creaturehealth;
    int playerid, creatureid;

    //Variables used in HealPlayer/HealCreature
    private String idtext, nametext, healthtext;
    private String creatureidtext, creaturenametext, creaturehealthtext;

    public NestriaDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Nestria.db");
            conn.createStatement().execute("PRAGMA foreign_keys = ON"); /** Turn on FOREIGN KEYS */
            conn.setAutoCommit(false);
        }
        catch(Exception e){conn = null;}
        isopen = (conn != null);
    }

    // Test whether the database is open.
    public boolean isOpen() {return isopen;}

    // Close the database connection.
    public void close() {
        if (!isopen) return;
        try {conn.close();}
        catch (Exception e) {}
        isopen = false;
        conn = null;
    }

    public Connection getConnection() {
        return conn;
    }

    /** 
     * SetUp method for Arena. Takes random integer to decide the enemy/player. Then using the two integers, 
     * takes a creature and player from DB.
     */
    public void setUp() {
        Random rand = new Random();
        int number = rand.nextInt(20) + 1;
        playerid = number;

        PreparedStatement stmt = null;
        ResultSet rset = null;

        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {
            //ArenaController a = new ArenaController();
            // Create a PreparedStatement for the query.
            sql = "SELECT Player.Name," +
            "Player.Attack," +
            "Player.Defense," +
            "Player.Health," +
            "Weapon.Name AS Weapon," +
            "Shield.Name AS Shield " +
            "FROM Player " +
            "INNER JOIN " +
            "Weapon ON Weapon.id = Player.Weapon " +
            "INNER JOIN " +
            "Shield ON Shield.Id = Player.Shield " +
            "WHERE Player.id = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,number);
            // Execute the query and print the result set.
            rset = stmt.executeQuery();
            while (rset.next()) {
                playername = rset.getString(1);
                playerattack = rset.getString(2);
                playerdefense = rset.getString(3);
                playerhealth = rset.getString(4);
                playerweapon = rset.getString(5);
                playershield = rset.getString(6);
            }
            stmt.close();
            conn.commit();

            // FOR CREATURE

            // Create a PreparedStatement for the query.
            sql = "SELECT Creature.Name, Creature.Attack, Creature.Defense," +
            "Creature.Health FROM Creature WHERE Creature.id = ?;";

            stmt = conn.prepareStatement(sql);

            number = rand.nextInt(24) + 1;
            creatureid = number; 

            stmt.setInt(1,number);
            // Execute the query and print the result set.
            rset = stmt.executeQuery();
            while (rset.next()) {
                creaturename = rset.getString(1);
                creatureattack = rset.getString(2);
                creaturedefense = rset.getString(3);
                creaturehealth = rset.getString(4);
            }
            stmt.close();
            conn.commit();

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}
        try {conn.rollback();}
        catch (Exception err) {}
    }

    /**
     * Defend method for Arena. Player is the only one being attacked by creature.
     * Updates health based on its own defense and the creature's attack.
     */
    public void defend() {
        PreparedStatement stmt = null;  
        String sql;
        int health;
        int creaturedamage;
        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {

            health = Integer.parseInt(playerhealth) + Integer.parseInt(playerdefense);

            sql = "UPDATE Player SET Health = ? WHERE Id = ? ;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,health); 
            stmt.setInt(2,playerid);

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();

            int playerdamage;
            playerdamage = health - Integer.parseInt(creatureattack);

            // Create a PreparedStatement for the query.
            sql = "UPDATE Player SET Health = ? WHERE Id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,playerdamage);
            stmt.setInt(2,playerid);

            // Execute the update.
            stmt.executeUpdate();

            conn.commit();

            playerhealth = Integer.toString(playerdamage);

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}    
    }

    /**\
     * Affects both player and creature health. Used in Arena. 
     */      
    public void attack() {
        PreparedStatement stmt = null;  
        String sql;
        int playerdamage;
        int creaturedamage;

        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {

            playerdamage = Integer.parseInt(creatureattack);
            playerdamage = Integer.parseInt(playerhealth) - playerdamage;

            sql = "UPDATE Player SET Health = ? WHERE Id = ? ;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,playerdamage); //giving error
            stmt.setInt(2,playerid);

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();

            creaturedamage = Integer.parseInt(playerattack);
            creaturedamage = Integer.parseInt(creaturehealth) - creaturedamage;

            // Create a PreparedStatement for the query.
            sql = "UPDATE Creature SET Health = ? WHERE Id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,creaturedamage);
            stmt.setInt(2,creatureid);

            // Execute the update.
            stmt.executeUpdate();

            conn.commit();

            creaturehealth = Integer.toString(creaturedamage);
            playerhealth = Integer.toString(playerdamage);

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}
    }

    /**
     * Used in Heal Player
     * Heals player by how many health is entered in TextField.
     */
    public void healPlayer(TextField enter, TextField heal, TextField name, TextField id, TextField health) {
        PreparedStatement stmt = null;  
        String sql;
        ResultSet rset = null;
        int playerhealth;

        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {

            int getPlayerId = Integer.parseInt(enter.getText());
            int healer = Integer.parseInt(heal.getText());

            sql = "UPDATE Player SET Health = Health+? WHERE Id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,healer); //giving error
            stmt.setInt(2,getPlayerId);

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();

            //ArenaController a = new ArenaController();
            // Create a PreparedStatement for the query.
            sql = "Select Player.Id, Player.Name, Player.Health FROM Player WHERE Id = ?;";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1,getPlayerId);
            // Execute the query and print the result set.
            rset = stmt.executeQuery();
            while (rset.next()) {
                idtext = rset.getString(1);
                nametext = rset.getString(2);
                healthtext = rset.getString(3);
            }

            stmt.close();
            conn.commit();

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}
    }

    /**
     * Used in Heal Creature
     * Heals creature by how many health is entered in TextField.
     */
    public void healCreature(TextField enter, TextField heal) {
        PreparedStatement stmt = null;  
        String sql;
        ResultSet rset = null;
        int playerhealth;

        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {

            int getPlayerId = Integer.parseInt(enter.getText());
            int healer = Integer.parseInt(heal.getText());

            sql = "UPDATE Creature SET Health = Health+? WHERE Id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,healer); //giving error
            stmt.setInt(2,getPlayerId);

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();

            // Create a PreparedStatement for the query.
            sql = "Select Creature.Id, Creature.Name, Creature.Health FROM Creature WHERE Id = ?;";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1,getPlayerId);
            // Execute the query and print the result set.
            rset = stmt.executeQuery();
            while (rset.next()) {
                creatureidtext = rset.getString(1);
                creaturenametext = rset.getString(2);
                creaturehealthtext = rset.getString(3);
            }

            stmt.close();
            conn.commit();

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}
    }

    /**
     * Used in AddKnight. Adds a Knight to DB. Returns true or false.
     */
    public boolean addKnight(TextField id, TextField name, TextField attack, TextField defense, TextField health, int weapon, int shield, int kingdom, int goal) {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql, nameTxt, attackTxt, defenseTxt, healthTxt;
        boolean result = false;

        // Return if the database is closed.
        if (!isopen) return result;

        try {

            int knightId = Integer.parseInt(id.getText());

            // Create a PreparedStatement for the update.
            sql = "INSERT INTO Player(id,Name, Health, Attack, Defense, Weapon, Shield," 
            + "Goal) VALUES (?,?,?,?,?,?,?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, knightId);
            stmt.setString(2, name.getText());
            stmt.setString(3, health.getText());
            stmt.setString(4, attack.getText());
            stmt.setString(5, defense.getText());
            stmt.setInt(6, weapon);
            stmt.setInt(7, shield);
            stmt.setInt(8, goal);

            // Execute the update
            stmt.executeUpdate();

            sql = "INSERT INTO Knight(id, Kingdom) VALUES (?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, knightId);
            stmt.setInt(2, kingdom);

            stmt.close();
            conn.commit();
            result = true;

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

            try {stmt.close();}
            catch (Exception err) {;}
            try {conn.rollback();}
            catch (Exception err) {;}
        }
        return result;
    }

    /**
     * Used in AddViking. Adds a Viking to DB. Returns true or false.
     */
    public boolean addViking(TextField id, TextField name, TextField attack, TextField defense, TextField health, int weapon, int shield, int tribe, int goal) {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql, nameTxt, attackTxt, defenseTxt, healthTxt;
        boolean result = false;

        // Return if the database is closed.
        if (!isopen) return result;

        try {

            int vikingId = Integer.parseInt(id.getText());

            // Create a PreparedStatement for the update.
            sql = "INSERT INTO Player(id,Name, Health, Attack, Defense, Weapon, Shield," 
            + "Goal) VALUES (?,?,?,?,?,?,?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vikingId);
            stmt.setString(2, name.getText());
            stmt.setString(3, health.getText());
            stmt.setString(4, attack.getText());
            stmt.setString(5, defense.getText());
            stmt.setInt(6, weapon);
            stmt.setInt(7, shield);
            stmt.setInt(8, goal);

            // Execute the update
            stmt.executeUpdate();

            sql = "INSERT INTO Viking(id, Tribe) VALUES (?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vikingId);
            stmt.setInt(2, tribe);

            stmt.close();
            conn.commit();
            result = true;

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

            try {stmt.close();}
            catch (Exception err) {;}
            try {conn.rollback();}
            catch (Exception err) {;}
        }
        return result;
    }

    /**
     * Used in ViewPlayer. Views all players in DB. (Added characters DO NOT have a picture/image.
     */
    public ObservableList<Player> viewPlayers() {
        ObservableList<Player> table = FXCollections.observableArrayList();

        PreparedStatement stmt = null;
        ResultSet rset = null;
        int id, health, attack, defense;
        String name, sql, weapon, shield, goal, tribe, kingdom;

        ImageView im = null;  
        Image image = null;
        byte[] array;

        if (!isopen) return null;
        try {
            sql = "SELECT Player.id, Player.Name, Player.Health, Player.Attack, " +
            "Player.Defense, Goal.Objective, Weapon.Name AS Weapon, Shield.Name AS Shield, Kingdom.Name AS Kingdom, " +
            "Tribe.Name AS Tribe, Picture.ImageFile FROM Player LEFT JOIN Knight ON Knight.id = Player.id LEFT JOIN " + 
            "Viking ON Viking.id = Player.id LEFT JOIN Goal ON Player.Goal = Goal.id LEFT JOIN " +
            "Shield ON Player.Shield = Shield.id LEFT JOIN Weapon ON Player.Weapon = Weapon.Id LEFT JOIN " +
            "Kingdom ON Kingdom.id = Knight.Kingdom LEFT JOIN Tribe ON Tribe.id = Viking.Tribe " +
            "LEFT JOIN PlayerPics ON PlayerPics.Player = Player.Id LEFT JOIN Picture ON Picture.Id = PlayerPics.Picture;";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            
            int i = 0;
            
            while (rset.next()) {
                id = rset.getInt(1);
                name = rset.getString(2);
                health = rset.getInt(3);
                attack = rset.getInt(4);
                defense = rset.getInt(5);
                goal = rset.getString(6);
                shield = rset.getString(7);
                weapon = rset.getString(8);
                kingdom = rset.getString(9);
                tribe = rset.getString(10);
                if(i>19) {
                    table.add(new Player(id, name, health, attack, defense, weapon, shield, goal, kingdom, tribe, null));
                    i++;
                }
                else{
                    array = rset.getBytes(11);
                    ByteArrayInputStream bis = new ByteArrayInputStream(array);
                    BufferedImage b = ImageIO.read(bis);
                    image = SwingFXUtils.toFXImage(b,null);

                    table.add(new Player(id, name, health, attack, defense, weapon, shield, goal, kingdom, tribe, im = new ImageView(image)));
                    im.setFitHeight(200);
                    im.setFitWidth(150);
                    i++;
                }
            }

        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return table;
    }

    /**
     * Used in ViewCreature. Views all creatures in DB. 
     */
    public ObservableList<Creature> viewCreatures() {
        ObservableList<Creature> table = FXCollections.observableArrayList();

        PreparedStatement stmt = null;
        ResultSet rset = null;
        int id, health, attack, defense;
        String name, sql, weapon, behavior, size, habitat, dragonColor, 
        dragonSpecies, wingspan, trollColor, trollSpecies, wolfColor, wolfSpecies;
        ImageView im = null;
        Image image = null;
        byte[] array;

        if (!isopen) return null;

        try {
            sql = "SELECT Creature.id, Creature.Name, Creature.Health, Creature.Behavior, " +
            "Creature.Attack, Creature.Defense, Creature.Size, Creature.Habitat, Dragon.Color " +
            "AS [Dragon Color], Dragon.Species AS [Dragon Species], Dragon.Wingspan AS Wingspan," +
            "Skeleton.Weapon AS Weapon,  Troll.Color AS [Troll Color], Troll.Species AS " +
            "[Troll Species], Wolf.Color AS [Wolf Color], Wolf.Species AS [Wolf Species], " +
            "Picture.ImageFile FROM Creature LEFT JOIN Dragon ON Dragon.id = Creature.id " +
            "LEFT JOIN Skeleton ON Skeleton.id = Creature.id LEFT JOIN Troll ON Troll.id = " +
            "Creature.id LEFT JOIN Wolf ON Wolf.id = Creature.id INNER JOIN CreaturePics ON " +
            "Creature.Id = CreaturePics.Creature INNER JOIN Picture ON CreaturePics.Picture " +
            "= Picture.Id;";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            int i = 0;

            while (rset.next()) {
                id = rset.getInt(1);
                name = rset.getString(2);
                health = rset.getInt(3);
                behavior = rset.getString(4);
                attack = rset.getInt(5);
                defense = rset.getInt(6);
                size = rset.getString(7);
                habitat = rset.getString(8);
                dragonColor = rset.getString(9);
                dragonSpecies = rset.getString(10);
                wingspan = rset.getString(11);
                weapon = rset.getString(12);
                trollColor = rset.getString(13);
                trollSpecies = rset.getString(14);
                wolfColor = rset.getString(15);
                wolfSpecies = rset.getString(16);
                if(i > 24
                ) {
                    table.add(new Creature(id, name, health, behavior, attack, defense, size, habitat,
                            dragonColor, dragonSpecies, wingspan, weapon, trollColor, trollSpecies, wolfColor, wolfSpecies, null)); 
                }
                else{
                    array = rset.getBytes(17);
                    ByteArrayInputStream bis = new ByteArrayInputStream(array);
                    BufferedImage b = ImageIO.read(bis);
                    image = SwingFXUtils.toFXImage(b,null);

                    table.add(new Creature(id, name, health, behavior, attack, defense, size, habitat,
                            dragonColor, dragonSpecies, wingspan, weapon, trollColor, trollSpecies, wolfColor, wolfSpecies, im = new ImageView(image))); 
                    im.setFitHeight(200);
                    im.setFitWidth(150);
                    i++; 
                }
            }

        }
        catch (Exception e) {
            return null;
        }
        return table;
    }

    /**
     * Used in ViewWeapon. Views all weapons in DB. 
     */
    public ObservableList<Weapon> viewWeapons() {
        ObservableList<Weapon> table = FXCollections.observableArrayList();

        PreparedStatement stmt = null;
        ResultSet rset = null;
        int id, attack;
        String name, sql;

        ImageView im = null;
        Image image = null;
        byte[] array;

        if (!isopen) return null;

        try {
            sql = "SELECT Weapon.id, Weapon.Name, Weapon.Attack, Picture.ImageFile " +
            "FROM Weapon INNER JOIN Picture ON Weapon.Picture = Picture.Id;";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt(1);
                name = rset.getString(2);
                attack = rset.getInt(3);
                array = rset.getBytes(4);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);                

                table.add(new Weapon(id, name,attack, im = new ImageView(image))); 
                im.setFitHeight(400);
                im.setFitWidth(200);
            }

        }
        catch (Exception e) {
            return null;
        }
        return table;
    }

    /**
     * Used in ViewShield. Views all shields in DB. 
     */
    public ObservableList<Shield> viewShields() {
        ObservableList<Shield> table = FXCollections.observableArrayList();

        PreparedStatement stmt = null;
        ResultSet rset = null;
        int id, defense;
        String name, sql;

        Image image = null;
        ImageView im = null;
        byte[] array;

        if (!isopen) return null;

        try {
            sql = "SELECT Shield.id, Shield.Name, Shield.Defense, " + 
            "Picture.ImageFile FROM Shield INNER JOIN Picture ON Shield.Picture = Picture.Id;";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt(1);
                name = rset.getString(2);
                defense = rset.getInt(3);
                array = rset.getBytes(4);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);

                table.add(new Shield(id, name,defense, im = new ImageView(image))); 
                im.setFitHeight(400);
                im.setFitWidth(200);
            }

        }
        catch (Exception e) {
            return null;
        }
        return table;
    }

    /**
     * Used in ViewScenery. Views all scenery in DB. 
     */
    public ObservableList<Scenery> viewScenery() {
        ObservableList<Scenery> table = FXCollections.observableArrayList();

        PreparedStatement stmt = null;
        ResultSet rset = null;
        int id;
        String name, sql, effect;
        Image image = null;
        ImageView im;
        byte[] array;

        if (!isopen) return null;

        try {
            sql = "SELECT Scenery.id, Scenery.region, Scenery.effect, Picture.ImageFile " +
            "FROM Scenery INNER JOIN Picture ON Scenery.Background = Picture.Id;";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

            while (rset.next()) {
                id = rset.getInt(1);
                name = rset.getString(2);
                effect = rset.getString(3);
                array = rset.getBytes(4);

                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);

                table.add(new Scenery(id, name,effect, im = new ImageView(image))); 
                im.setFitHeight(400);
                im.setFitWidth(600);

            }

        }
        catch (Exception e) {
            return null;
        }
        return table;
    }

    /**
     * Used in ViewScenery. Views all scenery in DB. 
     */
    public byte[] getPicture(int id) {

        PreparedStatement stmt = null;
        ResultSet rset = null;
        String name, sql, effect;
        Blob blob = null;
        byte[] array = {};

        if (!isopen) return null;

        try {
            sql = "SELECT Picture.ImageFile FROM Picture WHERE Picture.Id = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);

            rset = stmt.executeQuery();

            array = rset.getBytes(1);

        }
        catch (Exception e) {
            System.out.print(e);
            return null;
        }
        return array;
    }

    public String getidtext() {
        return idtext;
    }

    public String getnametext() {
        return nametext;
    }

    public String gethealtext() {
        return healthtext;
    }

    public String getCreatureid() {
        return creatureidtext;
    }

    public String getCreaturename() {
        return creaturenametext;
    }

    public String getCreaturehealth() {
        return creaturehealthtext;
    }

    public String getplayername() {
        return playername;
    }

    public String getplayerattack() {
        return playerattack;
    }

    public String getplayerdefense() {
        return playerdefense;
    }

    public String getplayerhealth() {
        return playerhealth;
    }

    public String getplayerweapon() {
        return playerweapon;
    }

    public String getplayershield() {
        return playershield;
    }

    public String getcreaturename() {
        return creaturename;
    }

    public String getcreatureattack() {
        return creatureattack;
    }

    public String getcreaturedefense() {
        return creaturedefense;
    }

    public String getcreaturehealth() {
        return creaturehealth;
    }

    public int getPlayerId() {
        return playerid;
    }

    public int getCreatureId() {
        return creatureid;
    }

}

