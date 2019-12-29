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
 * Controller for ViewShield.fxml. User can view all shields in DB.
 */
public class ViewShieldController extends MainMenuController implements Initializable{
    
    @FXML private TableView<Shield> shields = new TableView<>();
    @FXML private TableColumn<Shield,Integer> idColumn;
    @FXML private TableColumn<Shield, String> nameColumn;
    @FXML private TableColumn<Shield, Integer> defenseColumn;
    @FXML private TableColumn<Shield, ImageView> imageColumn;

    /**
     * When ViewShieldController is called. User can view all shields in DB. 
     */
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Shield, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Shield, String>("name"));
        defenseColumn.setCellValueFactory(new PropertyValueFactory<Shield, Integer>("defense"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Shield, ImageView>("image"));
        shields.setItems(db.viewShields());
    }

    /**
     * Returns shields table.
     */
    public TableView getShieldTable() {
        return shields;
    }
    
}
