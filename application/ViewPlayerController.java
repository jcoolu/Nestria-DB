package application; 

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.util.*;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for ViewPlayers.fxml. User can view all players in DB.
 */
public class ViewPlayerController extends MainMenuController implements Initializable {

    @FXML private TableView<Player> players = new TableView<>();
    @FXML private TableColumn<Player,Integer> idColumn;
    @FXML private TableColumn<Player, String> nameColumn;
    @FXML private TableColumn<Player, Integer> healthColumn;
    @FXML private TableColumn<Player, Integer> attackColumn;
    @FXML private TableColumn<Player, Integer> defenseColumn;
    @FXML private TableColumn<Player,String> goalColumn;
    @FXML private TableColumn<Player, String> weaponColumn;
    @FXML private TableColumn<Player, String> shieldColumn;
    @FXML private TableColumn<Player, String> kingdomColumn;
    @FXML private TableColumn<Player, String> tribeColumn;
    @FXML private TableColumn<Player, ImageView> imageColumn;

    /**
     * When ViewPlayerController is called. User can view ALL players (Vikings and Knights)
     */
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("health"));
        attackColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("attack"));
        defenseColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("defense"));
        goalColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("goal"));
        weaponColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("weapon"));
        shieldColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("shield"));
        kingdomColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("kingdom"));
        tribeColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("tribe"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Player, ImageView>("image"));
        players.setItems(db.viewPlayers());
    }

    /** 
     * Returns players table.
     */
    public TableView getPlayerTable() {
        return players;
    }
}
