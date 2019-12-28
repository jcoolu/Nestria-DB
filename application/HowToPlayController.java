package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.util.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Controller for HowToPlay.fxml. User is presented with information on the
 * tabs presented in the main menu (Enter Arena, Add Player, Heal Player, 
 * View Info, etc...)
 */
public class HowToPlayController 
{
    /**
     * Returns to main menu. 
     */
    public void goToMainMenu(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
