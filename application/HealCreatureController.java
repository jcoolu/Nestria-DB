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
import javafx.collections.*;

/**
 * Controller for HealCreature.fxml. Heals creature by user entering their id and health increase. 
 */
public class HealCreatureController implements Initializable{

    @FXML private TextField EnterCreatureId;
    @FXML private TextField HealBy;
    @FXML private TextField CreatureName;
    @FXML private TextField CreatureId;
    @FXML private TextField CreatureHealth;
    private NestriaDB db = new NestriaDB();

    /*
     * When HealCreatureController is called.
     */
    public void initialize(URL url, ResourceBundle rb) {
        EnterCreatureId.setText("");
        HealBy.setText("");
        CreatureName.setText("");
        CreatureId.setText("");
        CreatureHealth.setText("");
    }

    /*
     * Heals creature with input of the creature's id and how
     * much health should be added to its current health.
     */
    public void heal() {
        Creature cr = db.healCreature(EnterCreatureId, HealBy);
        CreatureName.setText(cr.getName());
        CreatureId.setText(Integer.toString(cr.getId()));
        CreatureHealth.setText(Integer.toString(cr.getHealth()));
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
