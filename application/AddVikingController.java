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
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.ComboBox;
import javafx.collections.*;

/**
 * Controller for AddViking.fxml. User can add a Viking to the DB (id HAS to be unique)
 */
public class AddVikingController  extends MainMenuController implements Initializable{
    private ObservableList<String> options = FXCollections.observableArrayList("Destroyer", "Steel Death",
            "Spikes", "Burn Baby Burn", "Skyward Sword", "Dagger", "Bass Booster", "Katana", "Dragon Sword",
            "Flicker");

    private ObservableList<String> shields = FXCollections.observableArrayList("Destiny", "Norton Shield",
            "Protector", "Unknown", "Bubble Power", "Worthless", "Boneyard Shield", "Grassland", "I Like Turtles",
            "MalwareBytes");

    private ObservableList<String> tribes = FXCollections.observableArrayList("Scaulding", "Gharnia",
            "Vexos", "The Companions", "Flarnians", "Wyverns", "The Vikislyn", "The Dragonians", "Cyase",
            "Skylanders");

    private ObservableList<String> goals = FXCollections.observableArrayList("Defeat Scaulding", "Defeat Vexos",
            "Defeat Wyverns", "Defeat Templars of the Forest", "Defeat Skylanders", "Defeat three dragons", "Defeat ten skeletons", 
            "Defeat five trolls", "Defeat eight wolves",
            "Defeat Hackers");

    @FXML private TextField VikingId;
    @FXML private TextField VikingName;
    @FXML private TextField VikingAttack;
    @FXML private TextField VikingHealth;
    @FXML private TextField VikingDefense;
    @FXML private ComboBox VikingWeapon = new ComboBox(options);
    @FXML private ComboBox VikingShield = new ComboBox(shields);
    @FXML private ComboBox VikingTribe = new ComboBox(tribes);
    @FXML private ComboBox Goal = new ComboBox(goals);
    @FXML private ImageView BackgroundPic;
    @FXML private ImageView Status;
    @FXML private TextField StatusText;
    private NestriaDB db = new NestriaDB();
    /**
     * When AddVikingController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        VikingWeapon.getItems().removeAll(VikingWeapon.getItems());
        VikingWeapon.getItems().addAll(options);
        VikingWeapon.getSelectionModel().select(options.get(0));
        VikingShield.getItems().removeAll(VikingShield.getItems());
        VikingShield.getItems().addAll(shields);
        VikingShield.getSelectionModel().select(shields.get(0));
        VikingTribe.getItems().removeAll(VikingTribe.getItems());
        VikingTribe.getItems().addAll(tribes);
        VikingTribe.getSelectionModel().select(tribes.get(0));
        Goal.getItems().removeAll(Goal.getItems());
        Goal.getItems().addAll(goals);
        Goal.getSelectionModel().select(goals.get(0));
        StatusText.setText("");
    }

    /*
     * Clears textfields. 
     */
    public void setUpText() {
        VikingId.setText("");
        VikingName.setText("");
        VikingAttack.setText("");
        VikingHealth.setText("");
        VikingDefense.setText("");
    }

    /*
     * Adds a viking to the DB.
     */
    public void addViking() {
        String getShield = VikingShield.getSelectionModel().getSelectedItem().toString();
        int shieldNum=1;
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

        String getWeapon = VikingWeapon.getSelectionModel().getSelectedItem().toString();
        int weaponNum=1;
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

        String getTribe = VikingTribe.getSelectionModel().getSelectedItem().toString();
        int tribeNum=1;
        switch(getTribe) {
            case "Scaulding":
            tribeNum = 1;
            break;
            case "Gharnia":
            tribeNum = 2;
            break;
            case "Vexos":
            tribeNum = 3;
            break;
            case "The Companions":
            tribeNum = 4;
            break;
            case "Flarnians":
            tribeNum = 5;
            break;
            case "Wyverns":
            tribeNum = 6;
            break;
            case "The Vikislyn":
            tribeNum = 7;
            break;
            case "The Dragonians":
            tribeNum = 8;
            break;
            case "Cyase":
            tribeNum = 9;
            break;
            case "Skylanders":
            tribeNum = 10;
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

        boolean result = db.addViking(VikingId, VikingName, VikingAttack, VikingDefense, VikingHealth, weaponNum, shieldNum, tribeNum, goalNum);
        if(result == true) {
            try {
                Image image = new Image(new FileInputStream("application\\Images\\Success.png"));
                Status.setImage(image);
                StatusText.setText("Added Viking Successfully");
            }
            catch (FileNotFoundException e){ 
            }

            setUpText(); //clears fields    
        }
        else{ 
            try {
                Image image = new Image(new FileInputStream("application\\Images\\Error.png"));
                Status.setImage(image);
                StatusText.setText("Error: Did not add Viking. Check to make sure " +
                    "id is a unique integer, and attack, defense, and health are valid integers.");
            }
            catch (FileNotFoundException e){ 
            }
            setUpText(); //clears fields    
        }
    }
}
