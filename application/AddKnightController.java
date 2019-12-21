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
import javafx.scene.control.ComboBox;
import javafx.collections.*;

public class AddKnightController implements Initializable{
    ObservableList<String> options = FXCollections.observableArrayList("Destroyer", "Steel Death",
            "Spikes", "Burn Baby Burn", "Skyward Sword", "Dagger", "Bass Booster", "Katana", "Dragon Sword",
            "Flicker");

    ObservableList<String> shields = FXCollections.observableArrayList("Destiny", "Norton Shield",
            "Protector", "Unknown", "Bubble Power", "Worthless", "Boneyard Shield", "Grassland", "I Like Turtles",
            "MalwareBytes");

    ObservableList<String> kingdoms = FXCollections.observableArrayList("Storm Dragons", "Ivoryfield",
            "Bellowthorn", "Winged Thunder", "Boudlerroses", "Waverns", "Templars of the Forest", "Righteous Paladins", "Hackers",
            "Broken Dynasty");

    ObservableList<String> goals = FXCollections.observableArrayList("Defeat Scaulding", "Defeat Vexos",
            "Defeat Wyverns", "Defeat Templars of the Forest", "Defeat Skylanders", "Defeat three dragons", "Defeat ten skeletons", 
            "Defeat five trolls", "Defeat eight wolves",
            "Defeat Hackers");

    @FXML private TextField KnightId;
    @FXML private TextField KnightName;
    @FXML private TextField KnightAttack;
    @FXML private TextField KnightHealth;
    @FXML private TextField KnightDefense;
    @FXML private ComboBox KnightWeapon = new ComboBox(options);
    @FXML private ComboBox KnightShield = new ComboBox(shields);
    @FXML private ComboBox KnightKingdom = new ComboBox(kingdoms);
    @FXML private ComboBox Goal = new ComboBox(goals);
    @FXML private ImageView BackgroundPic;
    @FXML private ImageView Status;
    @FXML private TextField statusText;

    private NestriaDB db = new NestriaDB();
    /**
     * When AddKnightController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        KnightWeapon.getItems().removeAll(KnightWeapon.getItems());
        KnightWeapon.getItems().addAll(options);
        KnightWeapon.getSelectionModel().select(options.get(0));
        KnightShield.getItems().removeAll(KnightShield.getItems());
        KnightShield.getItems().addAll(shields);
        KnightShield.getSelectionModel().select(shields.get(0));
        KnightKingdom.getItems().removeAll(KnightKingdom.getItems());
        KnightKingdom.getItems().addAll(kingdoms);
        KnightKingdom.getSelectionModel().select(kingdoms.get(0));
        Goal.getItems().removeAll(Goal.getItems());
        Goal.getItems().addAll(goals);
        statusText.setText("");
        Goal.getSelectionModel().select(goals.get(0));
    }

    public void setUpText() {
        KnightId.setText("");
        KnightName.setText("");
        KnightAttack.setText("");
        KnightHealth.setText("");
        KnightDefense.setText("");
    }

    public void addKnight() {
        String getShield = KnightShield.getSelectionModel().getSelectedItem().toString();
        int shieldNum = 1;
        switch(getShield) {
            case "Destiny":
            shieldNum = 1;
            break;
            case "Norton Shield":
            shieldNum = 2;
            break;
            case "Protector":
            shieldNum = 3;
            break;
            case "Unknown":
            shieldNum = 4;
            break;
            case "Bubble Power":
            shieldNum = 5;
            break;
            case "Worthless":
            shieldNum = 6;
            break;
            case "Boneyard Shield":
            shieldNum = 7;
            break;
            case "Grassland":
            shieldNum = 8;
            break;
            case "I Like Turtles":
            shieldNum = 9;
            break;
            case "MalwareBytes":
            shieldNum = 10;
            break;
        }

        String getWeapon = KnightWeapon.getSelectionModel().getSelectedItem().toString();
        int weaponNum = 1;
        switch(getWeapon) {
            case "Destroyer":
            weaponNum = 1;
            break;
            case "Steel Death":
            weaponNum = 2;
            break;
            case "Spikes":
            weaponNum = 3;
            break;
            case "Burn Baby Burn":
            weaponNum = 4;
            break;
            case "Skyward Sword":
            weaponNum = 5;
            break;
            case "Dagger":
            weaponNum = 6;
            break;
            case "Bass Booster":
            weaponNum = 7;
            break;  
            case "Katana":
            weaponNum = 8;
            break;
            case "Dragon Sword":
            weaponNum = 9;
            break;
            case "Flicker":
            weaponNum = 10;
            break;
        }

        String getKingdom = KnightKingdom.getSelectionModel().getSelectedItem().toString();
        int kingdomNum = 1;
        switch(getKingdom) {
            case "Storm Dragons":
            kingdomNum = 1;
            break;
            case "Ivoryfield":
            kingdomNum = 2;
            break;
            case "Bellowthorn":
            kingdomNum = 3;
            break;
            case "Winged Thunder":
            kingdomNum = 4;
            break;
            case "Boulderroses":
            kingdomNum = 5;
            break;
            case "Waverns":
            kingdomNum = 6;
            break;
            case "Templars of the Forest":
            kingdomNum = 7;
            break;
            case "Righteous Paladins":
            kingdomNum = 8;
            break;
            case "Hackers":
            kingdomNum = 9;
            break;
            case "Broken Dynasty":
            kingdomNum = 10;
            break;
        }

        String getGoal = Goal.getSelectionModel().getSelectedItem().toString();
        int goalNum = 1;
        switch(getGoal) {
            case "Defeat Scaulding":
            goalNum = 1;
            break;
            case "Defeat Vexos":
            goalNum = 2;
            break;
            case "Defeat Wyverns":
            goalNum = 3;
            break;  
            case "Defeat Templars of the Forest":
            goalNum = 4;
            break;
            case "Defeat Skylanders":
            goalNum = 5;
            break;
            case "Defeat three dragons":
            goalNum = 6;
            break;
            case "Defeat ten skeletons":
            goalNum = 7;
            break;
            case "Defeat five trolls":
            goalNum = 8;
            break;
            case "Defeat eight wolves":
            goalNum = 9;
            break;
            case "Defeat Hackers":
            goalNum = 10;
            break;
        }

        boolean result = db.addKnight(KnightId, KnightName, KnightAttack, KnightDefense, KnightHealth, weaponNum, shieldNum, kingdomNum, goalNum);
        if(result == true) {
            try {
                Image image = new Image(new FileInputStream("application\\Images\\Success.png"));
                Status.setImage(image);
                statusText.setText("Added Knight Successfully");
            }
            catch (FileNotFoundException e){ 
            }

            setUpText(); //clears fields    
        }
        else{ 
            try {
                Image image = new Image(new FileInputStream("application\\Images\\Error.png"));
                Status.setImage(image);
                statusText.setText("Error: Did not add Knight Successfully. " +
                    "Check to make sure id is unique AND/OR id, attack, defense, and health are integers.");
            }
            catch (FileNotFoundException e){ 
            }
            setUpText(); //clears fields    
        }
    }

    public void goToAddScene(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("AddPlayerScene1.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
