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
 * Controller for HealHuman.fxml. Heals player by user entering their id and health increase. 
 */
public class HealHumanController extends MainMenuController implements Initializable{

    @FXML private TextField EnterPlayerId;
    @FXML private TextField HealBy;
    @FXML private TextField PlayerName;
    @FXML private TextField PlayerId;
    @FXML private TextField PlayerHealth;
    @FXML private TableView<Player> players = new TableView<Player>();
    @FXML private TableColumn<Player, Integer> idColumn;
    @FXML private TableColumn<Player, String> nameColumn;
    @FXML private TableColumn<Player, Integer> healthColumn;
    @FXML private TextField searchId;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("health"));
        players.setItems(db.viewPlayers());
    }

    /*
     * Heals player with input of the player's id and how
     * much health should be added to its current health.
     */
    public void heal() {
        //get player from textfield
        Player pl = db.healPlayer(EnterPlayerId, HealBy);
        if(pl == null) {
            PlayerName.setText("N/A");
            PlayerHealth.setText("N/A");
            PlayerId.setText("N/A");
        }
        else {
            PlayerName.setText(pl.getName());
            PlayerId.setText(Integer.toString(pl.getId()));
            PlayerHealth.setText(Integer.toString(pl.getHealth()));
        }
        //refresh table
        players.setItems(db.viewPlayers());
    }
}
