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
import java.io.IOException;

/**
 * Controller for HealHuman.fxml. Heals player by user entering their id and health increase. 
 */
public class HealHumanController implements Initializable{

    @FXML private TextField EnterPlayerId;
    @FXML private TextField HealBy;
    @FXML private TextField PlayerName;
    @FXML private TextField PlayerId;
    @FXML private TextField PlayerHealth;
    private NestriaDB db = new NestriaDB();

    /**
     * When HealHumanController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        EnterPlayerId.setText("");
        HealBy.setText("");
        PlayerName.setText("");
        PlayerId.setText("");
        PlayerHealth.setText("");
    }

    /*
     * Heals player with input of the player's id and how
     * much health should be added to its current health.
     */
    public void heal() {
        Player pl = db.healPlayer(EnterPlayerId, HealBy, PlayerName, PlayerId, PlayerHealth);
        PlayerName.setText(pl.getName());
        PlayerId.setText(Integer.toString(pl.getId()));
        PlayerHealth.setText(Integer.toString(pl.getHealth()));
    }

    /*
     * Go to HealPlayer.fxml
     */    
    public void goToHealScene(ActionEvent event) throws IOException  {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealPlayer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close();
    }
}
