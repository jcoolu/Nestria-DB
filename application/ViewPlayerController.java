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
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.ComboBox;
import javafx.collections.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewPlayerController implements Initializable{

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

    private NestriaDB db = new NestriaDB();

    /**
     * When AddVikingController is called
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

    public TableView getPlayerTable() {
        return players;
    }

    public void goToViewInfo(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewInfo.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
