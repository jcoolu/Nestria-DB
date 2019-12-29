package application; 

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import java.util.*;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

/**
 * Controller for Arena.fxml. User (the player) can either attack or defend themselves from the enemy 
 * (the creature). 
 */
public class ArenaController implements Initializable{

    @FXML private TextField PlayerName;
    @FXML private TextField PlayerAttack;
    @FXML private TextField PlayerHealth;
    @FXML private TextField PlayerDefense;
    @FXML private TextField PlayerWeapon;
    @FXML private TextField PlayerShield;
    @FXML private TextField CreatureName;
    @FXML private TextField CreatureAttack;
    @FXML private TextField CreatureDefense;
    @FXML private TextField CreatureHealth;
    @FXML private ImageView PlayerPic;
    @FXML private ImageView CreaturePic;
    @FXML private ImageView BackgroundPic;
    private int creatureId;
    private int playerId;
    private NestriaDB db = new NestriaDB();
    private ArrayList<Creature> creatures;
    private ArrayList<Player> players;

    /** 
     * Go back to Main Menu
     */
    public void goBack(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close();
    }

    /*
     * When ArenaController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        setUp();
    }

    /*
     * Sets up creature and player. Both are randomized. Creature's health, name, attack, and defense are shown. 
     * The player's name, attack, defense, health, weapon, and shield are shown as well. 
     */
    @FXML
    public void setUp() {
        players = db.getPlayers();
        creatures = db.getCreatures();
        Random rand = new Random();
        creatureId = rand.nextInt(creatures.size()); 
        playerId = rand.nextInt(players.size());
        CreaturePic.setImage(db.getCreaturePic(creatures.get(creatureId).getId()));
        PlayerPic.setImage(db.getPlayerPic(players.get(playerId).getId()));
        try{
            int backgroundNumber = rand.nextInt(10) + 21; //21 - 30
        
            byte[] array = db.getPicture(backgroundNumber);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image backim = SwingFXUtils.toFXImage(b,null);
            BackgroundPic.setFitHeight(720);
            BackgroundPic.setFitWidth(1280);
            BackgroundPic.setImage(backim);
            setTextFields();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    /*
     * Both creature and player attack each other. 
     */
    public void attack() {    
        db.attack(creatures.get(creatureId), players.get(playerId));
        creatures.set(creatureId, db.getCreature(creatures.get(creatureId).getId()));
        players.set(playerId, db.getPlayer(players.get(playerId).getId()));
        setTextFields();
    }

    /*
     * Player defends themselves from creature's attack. 
     */
    public void defend() {
        db.defend(creatures.get(creatureId), players.get(playerId));
        players.set(playerId, db.getPlayer(players.get(playerId).getId()));
        setTextFields();
    }
    
    /*
     * Get's player's and creature's current info/stats from their respective array lists.
     */
    public void setTextFields() {
        PlayerName.setText(players.get(playerId).getName());
        PlayerAttack.setText(Integer.toString(players.get(playerId).getAttack()));
        PlayerHealth.setText(Integer.toString(players.get(playerId).getHealth()));
        PlayerDefense.setText(Integer.toString(players.get(playerId).getDefense()));
        PlayerWeapon.setText(players.get(playerId).getWeapon());
        PlayerShield.setText(players.get(playerId).getShield());
        CreatureName.setText(creatures.get(creatureId).getName());
        CreatureHealth.setText(Integer.toString(creatures.get(creatureId).getHealth()));
        CreatureAttack.setText(Integer.toString(creatures.get(creatureId).getAttack()));
        CreatureDefense.setText(Integer.toString(creatures.get(creatureId).getDefense()));
    }
    
}
