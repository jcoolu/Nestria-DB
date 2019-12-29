package application; 

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import java.util.*;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for ViewScenery.fxml. User can view all scenery in game. 
 */
public class ViewSceneryController extends MainMenuController implements Initializable{

    @FXML private TableView<Scenery> scenery = new TableView<>();
    @FXML private TableColumn<Scenery,Integer> idColumn;
    @FXML private TableColumn<Scenery, String> nameColumn;
    @FXML private TableColumn<Scenery, String> effectColumn;
    @FXML private TableColumn<Scenery, ImageView> imageColumn;

    /**
     * Setup for ViewScenery.fxml. Sets columns and rows for scenery table.
     */
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Scenery, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Scenery, String>("region"));
        effectColumn.setCellValueFactory(new PropertyValueFactory<Scenery, String>("effect"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Scenery, ImageView>("image"));
        scenery.setItems(db.viewScenery());
    }

    /**
     * Returns scenery table. 
     */
    public TableView getSceneryTable() {
        return scenery;
    }
}
