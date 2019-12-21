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

public class ViewShieldController implements Initializable{

    @FXML private TableView<Shield> shields = new TableView<>();
    @FXML private TableColumn<Shield,Integer> idColumn;
    @FXML private TableColumn<Shield, String> nameColumn;
    @FXML private TableColumn<Shield, Integer> defenseColumn;
    @FXML private TableColumn<Shield, ImageView> imageColumn;

    private NestriaDB db = new NestriaDB();

    /**
     * When AddVikingController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Shield, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Shield, String>("name"));
        defenseColumn.setCellValueFactory(new PropertyValueFactory<Shield, Integer>("defense"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Shield, ImageView>("image"));
        shields.setItems(db.viewShields());
    }

    public TableView getShieldTable() {
        return shields;
    }

    public void goToViewInfo(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewInfo.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
