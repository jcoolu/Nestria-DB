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

public class ViewCreatureController implements Initializable{

    @FXML private TableView<Creature> creatures = new TableView<>();
    @FXML private TableColumn<Creature,Integer> idColumn;
    @FXML private TableColumn<Creature, String> nameColumn;
    @FXML private TableColumn<Creature, Integer> healthColumn;
    @FXML private TableColumn<Creature, String> behaviorColumn;
    @FXML private TableColumn<Creature, Integer> attackColumn;
    @FXML private TableColumn<Creature, Integer> defenseColumn;
    @FXML private TableColumn<Creature, String> sizeColumn;
    @FXML private TableColumn<Creature, String> habitatColumn;
    @FXML private TableColumn<Creature, String> dragonColorColumn;
    @FXML private TableColumn<Creature, String> dragonSpeciesColumn;
    @FXML private TableColumn<Creature, String> wingspanColumn;
    @FXML private TableColumn<Creature, String> weaponColumn;
    @FXML private TableColumn<Creature, String> trollColorColumn;
    @FXML private TableColumn<Creature, String> trollSpeciesColumn;
    @FXML private TableColumn<Creature, String> wolfColorColumn;
    @FXML private TableColumn<Creature, String> wolfSpeciesColumn;
    @FXML private TableColumn<Creature, ImageView> imageColumn;

    NestriaDB db = new NestriaDB();

    /**
     * When AddVikingController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Creature, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<Creature, Integer>("health"));
        behaviorColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("behavior"));
        attackColumn.setCellValueFactory(new PropertyValueFactory<Creature, Integer>("attack"));
        defenseColumn.setCellValueFactory(new PropertyValueFactory<Creature, Integer>("defense"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("size"));
        habitatColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("habitat"));
        dragonColorColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("dragonColor"));
        dragonSpeciesColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("dragonSpecies"));
        wingspanColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("wingspan"));
        weaponColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("weapon"));
        trollColorColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("trollColor"));
        trollSpeciesColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("trollSpecies"));
        wolfColorColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("wolfColor"));
        wolfSpeciesColumn.setCellValueFactory(new PropertyValueFactory<Creature, String>("wolfSpecies"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Creature, ImageView>("image"));
        creatures.setItems(db.viewCreatures());
    }

    public TableView getCreatureTable() {
        return creatures;
    }

    public void goToViewInfo(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewInfo.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
