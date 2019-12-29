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
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

/**
 * Controller for ViewInfo.fxml. User can view options that chooses what items/characters they want to view
 * in the DB.
 */
public class ViewInfoController extends StartController implements Initializable{

    @FXML private ImageView image;
    private NestriaDB db = new NestriaDB();

    /**
     * When ViewInfo.fxml is called.
     */
    public void initialize(URL url, ResourceBundle rb) {
        try{ 
            byte[] array = db.getPicture(75);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image im = SwingFXUtils.toFXImage(b,null);
            image.setImage(im);
            db.close(); // closes connection
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /*
     * Go to ViewPlayerController.
     */
    public void goToViewPlayer(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewPlayers.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show(); 
    }

    /*
     * Go to ViewCreatureController.
     */
    public void goToViewCreature(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewCreatures.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /*
     * Go to ViewWeaponController
     */
    public void goToViewWeapon(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewWeapons.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /*
     * Go to ViewShieldController
     */
    public void goToViewShield(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewShields.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /*
     * Go to ViewSceneryController
     */
    public void goToViewScenery(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewScenery.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
