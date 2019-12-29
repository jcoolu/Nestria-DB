package application; 

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import java.util.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import javafx.scene.control.ComboBox;
import java.net.URL;
import javafx.fxml.Initializable;

/**
 * Controller for ViewInfo.fxml. User can view options that chooses what items/characters they want to view
 * in the DB.
 */
public class ViewInfoController implements Initializable{

    @FXML private ImageView image;
    private NestriaDB db = new NestriaDB();

    /**
     * When AddKnightController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        Image im = new Image("\\application\\Images\\ViewInfoBackground.png");
        image.setImage(im);
    }

    public void goToViewPlayer(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewPlayers.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show(); 
    }

    public void goToViewCreature(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewCreatures.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToViewWeapon(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewWeapons.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToViewShield(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewShields.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToViewScenery(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewScenery.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToMainMenu(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
