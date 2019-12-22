package application; 

import javafx.scene.control.TextInputControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import java.util.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

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
    NestriaDB db = new NestriaDB();
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

    /**
     * When ArenaController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        setUp();
    }

    @FXML
    public void setUp() {
        players = db.getPlayers();
        creatures = db.getCreatures();
        Random rand = new Random();
        creatureId = rand.nextInt(creatures.size()); 
        playerId = rand.nextInt(players.size());
        try{
            int backgroundNumber = rand.nextInt(10) + 21; //21 - 30
        
            byte[] array = db.getPicture(backgroundNumber);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image backim = SwingFXUtils.toFXImage(b,null);
            BackgroundPic.setFitHeight(720);
            BackgroundPic.setFitWidth(1280);
            BackgroundPic.setImage(backim);

            CreaturePic.setImage(creatures.get(creatureId).getImage());
            PlayerPic.setImage(players.get(playerId).getImage());
            setTextFields();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void attack() {    
        db.attack(creatures.get(creatureId), players.get(playerId));
        creatures.set(creatureId, db.getCreature(creatures.get(creatureId).getId()));
        players.set(playerId, db.getPlayer(players.get(playerId).getId()));
        setTextFields();
    }

    public void defend() {
        db.defend(creatures.get(creatureId), players.get(playerId));
        players.set(playerId, db.getPlayer(players.get(playerId).getId()));
        setTextFields();
    }

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
