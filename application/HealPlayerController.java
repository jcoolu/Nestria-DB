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

/**
 * Controller for HealPlayer.fxml. User can choose to heal either a player or creature.
 */
public class HealPlayerController implements Initializable {
    
    @FXML private ImageView image;
    
    /*
     * When HealPlayer.fxml is called, this is initialized. 
     */
    public void initialize(URL url, ResourceBundle rb) {
        Image im = new Image("\\application\\Images\\HealCharacters.png");
        image.setImage(im);
    }
    
    /*
     * Go to HealCreatureController
     */
    public void goToHealCreature(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealCreature.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    
    /*
     * Go to HealHumanController
     */
    public void goToHealPlayer(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealHuman.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();  
    }
    
    /*
     * Go to MainMenuController
     */
    public void goToMainMenu(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
