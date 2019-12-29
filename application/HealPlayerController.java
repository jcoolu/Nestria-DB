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
 * Controller for HealPlayer.fxml. User can choose to heal either a player or creature.
 */
public class HealPlayerController extends StartController implements Initializable {

    @FXML private ImageView image;
    private NestriaDB db = new NestriaDB();

    /*
     * When HealPlayer.fxml is called, this is initialized. 
     */
    public void initialize(URL url, ResourceBundle rb) {
        try{ 
            byte[] array = db.getPicture(79);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image im = SwingFXUtils.toFXImage(b,null);
            image.setImage(im);
            db.close();
        }
        catch (Exception e){
            System.out.println(e);
        }  
    }

    /*
     * Go to HealCreatureController
     */
    public void goToHealCreature(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealCreature.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /*
     * Go to HealHumanController
     */
    public void goToHealPlayer(ActionEvent event) throws IOException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealHuman.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();  
    }
}
