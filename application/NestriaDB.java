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
     * Defend method for Arena. Player is the only one being attacked by creature.
     * Updates health based on its own defense and the creature's attack.
     */
    public void defend(Creature cr, Player pl) {
        PreparedStatement stmt = null;  
        String sql;
        int creaturedamage = 0;
        if(cr.getAttack() > pl.getDefense()) {
            creaturedamage = cr.getAttack() - pl.getDefense();
        }
        else {
        }
        int health = pl.getHealth()-creaturedamage;
        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {

            sql = "UPDATE Player SET Health = ? WHERE Id = ? ;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,health); 
            stmt.setInt(2,pl.getId());

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        finally {
            try {stmt.close();}
            catch (Exception err) {;}
            try {conn.rollback();}
            catch (Exception err) {;}
            try{
                stmt.close();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**\
     * Affects both player and creature health. Used in Arena. 
     */      
    public void attack(Creature cr, Player pl) {
        PreparedStatement stmt = null;  
        String sql;
        int playerdamage = pl.getHealth() - cr.getAttack();
        int creaturedamage = cr.getHealth() - pl.getAttack();

        // Return if the database is closed.
        if (!isopen) {
            return;
            //return null;
        }

        try {

            sql = "UPDATE Player SET Health = ? WHERE Id = ? ;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,playerdamage); //giving error
            stmt.setInt(2,pl.getId());

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();

            // Create a PreparedStatement for the query.
            sql = "UPDATE Creature SET Health = ? WHERE Id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,creaturedamage);
            stmt.setInt(2,cr.getId());

            // Execute the update.
            stmt.executeUpdate();
            stmt.close();
            conn.commit();

        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        finally {
            try {stmt.close();}
            catch (Exception err) {
                System.out.println(err);}
            try {conn.rollback();}
            catch (Exception err) {System.out.println(err);}
            try{
                stmt.close();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Used in Heal Player
     * Heals player by how many health is entered in TextField.
     */
    public Player healPlayer(TextField enter, TextField heal, TextField name, TextField id, TextField health) {
        PreparedStatement stmt = null;  
        String sql;
        ResultSet rset = null;
        Player pl = getPlayer(Integer.parseInt(enter.getText()));
        int playerhealth = pl.getHealth() + Integer.parseInt(heal.getText());

        // Return if the database is closed.
        if (!isopen) {
            return null;
        }

        try {
            sql = "UPDATE Player SET Health = ? WHERE Id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,playerhealth); //giving error
            stmt.setInt(2,pl.getId());

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();
            try {stmt.close();}
            catch (Exception err) {;}
            try {conn.rollback();}
            catch (Exception err) {;}

            pl = getPlayer(pl.getId());
            try{
                stmt.close();
                rset.close();
                conn.commit();
            }
            catch (Exception e) {

            }
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}
        return pl;
    }

    /**
     * Used in Heal Creature
     * Heals creature by how many health is entered in TextField.
     */
    public Creature healCreature(TextField enter, TextField heal) {
        PreparedStatement stmt = null;  
        String sql;
        ResultSet rset = null;
        Creature cr = getCreature(Integer.parseInt(enter.getText()));
        int creaturehealth = cr.getHealth() + Integer.parseInt(heal.getText());
        // Return if the database is closed.
        if (!isopen) {
            return null;
        }

        try {
            sql = "UPDATE Creature SET Health = ? WHERE Id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,creaturehealth); 
            stmt.setInt(2,cr.getId());

            // Execute the update.
            stmt.executeUpdate();

            stmt.close();
            conn.commit();
            try {stmt.close();}
            catch (Exception err) {;}
            try {conn.rollback();}
            catch (Exception err) {;}

            cr = getCreature(cr.getId());

            try{
                stmt.close();
                rset.close();
                conn.commit();
            }
            catch (Exception e) {

            }
        } catch (Exception e) {
            System.out.printf("%s%n", e.getMessage());

        }
        try {stmt.close();}
        catch (Exception err) {}
        return cr;
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
            stmt.close();
            conn.commit();

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
            stmt.close();
            conn.commit();

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
            try{
                stmt.close();
                rset.close();
                conn.commit();
            }
            catch (Exception e) {

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
            try{
                stmt.close();
                rset.close();
                conn.commit();
            }
            catch (Exception e) {

            }
        }
        catch (Exception e) {
            return null;
        }
        return table;
    }

    /**
     * Used in ViewPlayer. Views all players in DB. (Added characters DO NOT have a picture/image.
     */
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> table = new ArrayList<Player>();

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
            "LEFT JOIN PlayerPics ON PlayerPics.Player = Player.Id LEFT JOIN Picture ON Picture.Id = PlayerPics.Picture WHERE Picture.ImageFile IS NOT NULL;";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

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
                array = rset.getBytes(11);

                table.add(new Player(id, name, health, attack, defense, weapon, shield, goal, kingdom, tribe, im));
            }
            conn.commit();
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        finally {
            try{
                stmt.close();
                rset.close();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        return table;
    }

    public ArrayList<Creature> getCreatures() {
        ArrayList<Creature> table = new ArrayList<Creature>();

        PreparedStatement stmt = null;
        ResultSet rset = null;
        int id, health, attack, defense;
        String name, sql, weapon, behavior, size, habitat, dragonColor, 
        dragonSpecies, wingspan, trollColor, trollSpecies, wolfColor, wolfSpecies;
        Image image = null;
        ImageView im = new ImageView();
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
            "= Picture.Id WHERE Picture.ImageFile IS NOT NULL";

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

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
                trollColor =
                rset.getString(13);
                trollSpecies = rset.getString(14);
                wolfColor = rset.getString(15);
                wolfSpecies = rset.getString(16);

                array = rset.getBytes(17);

                table.add(new Creature(id, name, health, behavior, attack, defense, size, habitat,
                        dragonColor, dragonSpecies, wingspan, weapon, trollColor, trollSpecies, wolfColor, wolfSpecies, im)); 
            }
            conn.commit();
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        finally {
            try{
                stmt.close();
                rset.close();
            }
            catch (Exception e) {
                System.out.println(e);
            }
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
            try{
                stmt.close();
                rset.close();
                conn.commit();
            }
            catch (Exception e) {

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
        try{
            stmt.close();
            rset.close();
            conn.commit();
        }
        catch (Exception e) {

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
            try{
                stmt.close();
                rset.close();
                conn.commit();
            }
            catch (Exception e) {

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
        try{
            stmt.close();
            rset.close();
        }
        catch (Exception e) {

        }
        return array;
    }

    public Player getPlayer(int idm) {
        Player pl = null;

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
            "LEFT JOIN PlayerPics ON PlayerPics.Player = Player.Id LEFT JOIN Picture ON Picture.Id = PlayerPics.Picture WHERE Picture.ImageFile IS NOT NULL AND Player.Id = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idm);
            rset = stmt.executeQuery();

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
                array = rset.getBytes(11);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);

                pl = new Player(id, name, health, attack, defense, weapon, shield, goal, kingdom, tribe, im = new ImageView(image));
            }
            conn.commit();
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        finally {
            try{
                stmt.close();
                rset.close();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        return pl;    
    }

    public Creature getCreature(int idm) {

        PreparedStatement stmt = null;
        ResultSet rset = null;
        Creature cr = null;
        byte[] array;
        Image image = null;
        ImageView im = null;
        int id, health, attack, defense;
        String name, sql, weapon, behavior, size, habitat, dragonColor, 
        dragonSpecies, wingspan, trollColor, trollSpecies, wolfColor, wolfSpecies;
        if (!isopen) return null;

        try {
            sql = "SELECT Creature.id, Creature.Name, Creature.Health, Creature.Behavior, " +
            "Creature.Attack, Creature.Defense, Creature.Size, Creature.Habitat, Dragon.Color " + 
            "AS [Dragon Color], Dragon.Species AS [Dragon Species], Dragon.Wingspan AS Wingspan, " + 
            "Skeleton.Weapon AS Weapon,  Troll.Color AS [Troll Color], Troll.Species AS  " +
            "[Troll Species], Wolf.Color AS [Wolf Color], Wolf.Species AS [Wolf Species], " + 
            "Picture.ImageFile FROM Creature LEFT JOIN Dragon ON Dragon.id = Creature.id  " +
            "LEFT JOIN Skeleton ON Skeleton.id = Creature.id LEFT JOIN Troll ON Troll.id =  " +
            "Creature.id LEFT JOIN Wolf ON Wolf.id = Creature.id INNER JOIN CreaturePics ON  " +
            "Creature.Id = CreaturePics.Creature INNER JOIN Picture ON CreaturePics.Picture " +
            "= Picture.Id WHERE Picture.ImageFile IS NOT NULL AND Creature.Id = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idm);

            rset = stmt.executeQuery();

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
                trollColor =
                rset.getString(13);
                trollSpecies = rset.getString(14);
                wolfColor = rset.getString(15);
                wolfSpecies = rset.getString(16);

                array = rset.getBytes(17);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);

                cr = new Creature(id, name, health, behavior, attack, defense, size, habitat,
                    dragonColor, dragonSpecies, wingspan, weapon, trollColor, trollSpecies, wolfColor, wolfSpecies,im = new ImageView(image)); 
            }
            conn.commit();
        }
        catch (Exception e) {
            System.out.print(e);
            return null;
        }
        try{
            stmt.close();
            rset.close();
        }
        catch (Exception e) {

        }
        return cr;
    }
    
    public Image getCreaturePic(int idm) {
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        Creature cr = null;
        String sql;
        byte[] array;
        Image image = null;
        if (!isopen) return null;

        try {
            sql = "SELECT Picture.ImageFile FROM CreaturePics INNER JOIN Picture ON CreaturePics.Picture " +
            "= Picture.Id WHERE CreaturePics.Creature = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idm);

            rset = stmt.executeQuery();

            while (rset.next()) {
                array = rset.getBytes(1);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);


            }
            conn.commit();
        }
        catch (Exception e) {
            System.out.print(e);
            return null;
        }
        try{
            stmt.close();
            rset.close();
        }
        catch (Exception e) {

        }
        return image;
    }
    
    public Image getPlayerPic(int idm) {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        Creature cr = null;
        String sql;
        byte[] array;
        Image image = null;
        if (!isopen) return null;

        try {
            sql = "SELECT Picture.ImageFile FROM PlayerPics INNER JOIN Picture ON PlayerPics.Picture " +
            "= Picture.Id WHERE PlayerPics.Player = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,idm);

            rset = stmt.executeQuery();

            while (rset.next()) {
                array = rset.getBytes(1);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                image = SwingFXUtils.toFXImage(b,null);
            }
            conn.commit();
        }
        catch (Exception e) {
            System.out.print(e);
            return null;
        }
        try{
            stmt.close();
            rset.close();
        }
        catch (Exception e) {

        }
        return image;
    }
}

