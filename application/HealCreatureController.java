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

public class HealCreatureController implements Initializable{

    @FXML private TextField EnterCreatureId;
    @FXML private TextField HealBy;
    @FXML private TextField CreatureName;
    @FXML private TextField CreatureId;
    @FXML private TextField CreatureHealth;
    NestriaDB db = new NestriaDB();

    /**
     * When AddVikingController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        EnterCreatureId.setText("");
        HealBy.setText("");
        CreatureName.setText("");
        CreatureId.setText("");
        CreatureHealth.setText("");
    }

    public void heal() {
        db.healCreature(EnterCreatureId, HealBy);
        CreatureName.setText(db.getCreaturename());
        CreatureId.setText(db.getCreatureid());
        CreatureHealth.setText(db.getCreaturehealth());
    }

    public void goToHealScene(ActionEvent event) throws IOException, SQLException  {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealPlayer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public TextField returnEnterCreatureId() {
        return EnterCreatureId;
    }

    public TextField returnHealBy() {
        return HealBy;
    }

    public TextField returnCreatureName() {
        return CreatureName;
    }

    public TextField returnCreatureId() {
        return CreatureId;
    }

    public TextField returnCreatureHealth() {
        return CreatureHealth;
    }

    public void goToMainMenu(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
