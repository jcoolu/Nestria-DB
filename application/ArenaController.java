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

    public void goBack(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * When ArenaController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        setUp();
    }

    @FXML
    public void setUp() {
        db.setUp();
        setTextFields();

        playerId = db.getPlayerId()-1;
        creatureId = db.getCreatureId()-1;
        try{
            Random rand = new Random();
            int backgroundNumber = rand.nextInt(10) + 21; //21 - 30
        
            byte[] array = db.getPicture(backgroundNumber);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image backim = SwingFXUtils.toFXImage(b,null);
            BackgroundPic.setFitHeight(720);
            BackgroundPic.setFitWidth(1280);
            BackgroundPic.setImage(backim);

            int creatureNum = rand.nextInt(23) + 51; //51 - 74
            array = db.getPicture(creatureNum);
            bis = new ByteArrayInputStream(array);
            b = ImageIO.read(bis);
            Image creatureim = SwingFXUtils.toFXImage(b, null);
            CreaturePic.setImage(creatureim);

            int playerNum = rand.nextInt(20) + 31; // 31 - 50
            array = db.getPicture(playerNum);
            bis = new ByteArrayInputStream(array);
            b = ImageIO.read(bis);
            Image playerim = SwingFXUtils.toFXImage(b, null);
            PlayerPic.setImage(playerim);
        }
        catch(Exception e) {

        }
    }

    public void attack() {    
        db.attack();
        setTextFields();
    }

    public void defend() {
        db.defend();
        setTextFields();
    }

    public void setTextFields() {
        PlayerName.setText(db.getplayername());
        PlayerAttack.setText(db.getplayerattack());
        PlayerHealth.setText(db.getplayerhealth());
        PlayerDefense.setText(db.getplayerdefense());
        PlayerWeapon.setText(db.getplayerweapon());
        PlayerShield.setText(db.getplayershield());
        CreatureName.setText(db.getcreaturename());
        CreatureHealth.setText(db.getcreaturehealth());
        CreatureAttack.setText(db.getcreatureattack());
        CreatureDefense.setText(db.getcreaturedefense());
    }
}
