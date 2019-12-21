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

public class HealHumanController implements Initializable{

    @FXML private TextField EnterPlayerId;
    @FXML private TextField HealBy;
    @FXML private TextField PlayerName;
    @FXML private TextField PlayerId;
    @FXML private TextField PlayerHealth;
    private NestriaDB db = new NestriaDB();

    /**
     * When AddVikingController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        EnterPlayerId.setText("");
        HealBy.setText("");
        PlayerName.setText("");
        PlayerId.setText("");
        PlayerHealth.setText("");
    }

    public void heal() {
        db.healPlayer(EnterPlayerId, HealBy, PlayerName, PlayerId, PlayerHealth);
        PlayerName.setText(db.getnametext());
        PlayerId.setText(db.getidtext());
        PlayerHealth.setText(db.gethealtext());
    }

    public void goToHealScene(ActionEvent event) throws IOException, SQLException  {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealPlayer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public TextField returnEnterPlayerId() {
        return EnterPlayerId;
    }

    public TextField returnHealBy() {
        return HealBy;
    }

    public TextField returnPlayerName() {
        return PlayerName;
    }

    public TextField returnPlayerId() {
        return PlayerId;
    }

    public TextField returnPlayerHealth() {
        return PlayerHealth;
    }

    public void goToMainMenu(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
