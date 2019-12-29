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
 * Main Menu Controller. Allows user to "Enter Arena", "Add Player", "Heal Players", "View Info", etc...
 */
public class MainMenuController implements Initializable {

    @FXML private ImageView background;
    protected NestriaDB db = new NestriaDB();

    /*
     * Setup for MainMenu.fxml.
     */
    public void initialize(URL url, ResourceBundle rb) {
        try{ 
            byte[] array = db.getPicture(27);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(b,null);
            background.setImage(image);
            db.close(); // closes connection
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /*
     * Sends user to Arena.fxml.
     */
    public void goToArena(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("Arena.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close(); // closes connection
    }

    /*
     * Sends user to AddPlayerScene1.fxml.
     */
    public void goToAddPlayer(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("AddPlayerScene1.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close(); // closes connection
    }

    /*
     * Sends user to HowToPlay.fxml.
     */
    public void goToHowToPlay(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close(); // closes connection
    }

    /*
     * Sends user to HealPlayer.fxml.
     */
    public void goToHealPlayer(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealPlayer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close(); // closes connection
    }

    /*
     * Sends user to ViewInfo.fxml.
     */
    public void goToViewInfo(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewInfo.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        db.close(); // closes connection
    }

    /*
     * Quits program.
     */
    public void quitGame(ActionEvent event) throws IOException{
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.close();
        db.close(); // closes connection
    }
}
