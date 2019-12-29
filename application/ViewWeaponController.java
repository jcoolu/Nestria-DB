package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
 * Controller for ViewWeapon.fxml. User can view all weapons in DB.
 */
public class ViewWeaponController extends MainMenuController implements Initializable{

    @FXML private TableView<Weapon> weapons = new TableView<>();
    @FXML private TableColumn<Weapon,Integer> idColumn;
    @FXML private TableColumn<Weapon, String> nameColumn;
    @FXML private TableColumn<Weapon, Integer> attackColumn;
    @FXML private TableColumn<Weapon, ImageView> imageColumn;

    /**
     * Setup for ViewCreature.fxml. Sets columns and rows for creature table.
     */
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Weapon, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Weapon, String>("name"));
        attackColumn.setCellValueFactory(new PropertyValueFactory<Weapon, Integer>("attack"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Weapon, ImageView>("image"));
        weapons.setItems(db.viewWeapons());
    }

    /**
     * Returns weapon table.
     */
    public TableView getWeaponTable() {
        return weapons;
    }
}
