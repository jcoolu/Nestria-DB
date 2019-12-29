package application; 

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.Initializable;

/**
 * Controller for AddPlayer.fxml. User can either add a Knight or a Viking. 
 */
public class AddPlayerScene1Controller implements Initializable {
    @FXML private ImageView image;
    
    /*
     * When AddPlayerScene1Controller is called (from AddPlayer.fxml)
     */
    public void initialize(URL url, ResourceBundle rb) {
        Image im = new Image("\\application\\Images\\AddPlayerBackground.png");
        image.setImage(im);
    }

    /*
     * Go to AddKnightController.
     */
    public void goToAddKnight(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("AddKnight.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /*
     * Go to AddVikingController.
     */
    public void goToAddViking(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("AddViking.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /*
     * Go to main menu. 
     */
    public void goToMainMenu(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
