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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for HealCreature.fxml. Heals creature by user entering their id and health increase. 
 */
public class HealCreatureController extends MainMenuController implements Initializable{

    @FXML private TextField EnterCreatureId;
    @FXML private TextField HealBy;
    @FXML private TextField CreatureName;
    @FXML private TextField CreatureId;
    @FXML private TextField CreatureHealth;
    @FXML private TableView<Creature> creatures = new TableView<Creature>();
    @FXML private TableColumn<Creature, Integer> idColumn;
    @FXML private TableColumn<Creature, String> nameColumn;
    @FXML private TableColumn<Creature, Integer> healthColumn;
    @FXML private TextField searchId;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<Creature, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<Creature, Integer>("health"));
        creatures.setItems(db.viewCreatures());
    }

    /*
     * Heals creature with input of the creature's id and how
     * much health should be added to its current health.
     */
    public void heal() {
        // get Creature
        Creature cr = db.healCreature(EnterCreatureId, HealBy);
        if(cr == null) {
            CreatureName.setText("N/A");
            CreatureHealth.setText("N/A");
            CreatureId.setText("N/A");
        }
        else{
            CreatureName.setText(cr.getName());
            CreatureId.setText(Integer.toString(cr.getId()));
            CreatureHealth.setText(Integer.toString(cr.getHealth()));
        }
        // refresh table
        creatures.setItems(db.viewCreatures());
    }
}
