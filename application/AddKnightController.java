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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.ComboBox;
import javafx.collections.*;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.FileChooser;

/**
 * Controller for AddKnight.fxml. User can add a Knight to the DB (id HAS to be unique)
 */
public class AddKnightController extends MainMenuController implements Initializable{
    private NestriaDB db = new NestriaDB();
    ObservableList<String> options = db.getWeaponsForCombo();
    ObservableList<String> shields = db.getShieldsForCombo();
    ObservableList<String> kingdoms = db.getKingdomsForCombo();
    ObservableList<String> goals = db.getGoalsForCombo();

    @FXML private TextField KnightId;
    @FXML private TextField KnightName;
    @FXML private TextField KnightAttack;
    @FXML private TextField KnightHealth;
    @FXML private TextField KnightDefense;
    @FXML private ComboBox KnightWeapon = new ComboBox(options);
    @FXML private ComboBox KnightShield = new ComboBox(shields);
    @FXML private ComboBox KnightKingdom = new ComboBox(kingdoms);
    @FXML private ComboBox Goal = new ComboBox(goals);
    @FXML private ImageView BackgroundPic;
    @FXML private ImageView Status;
    @FXML private TextField statusText;
    @FXML private Button chooseImage;
    @FXML private TextField imageURL;
    private FileChooser fileChooser;
    private File file;

    /*
     * When AddKnightController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        KnightWeapon.getItems().removeAll(KnightWeapon.getItems());
        KnightWeapon.getItems().addAll(options);
        KnightWeapon.getSelectionModel().select(options.get(0));
        KnightShield.getItems().removeAll(KnightShield.getItems());
        KnightShield.getItems().addAll(shields);
        KnightShield.getSelectionModel().select(shields.get(0));
        KnightKingdom.getItems().removeAll(KnightKingdom.getItems());
        KnightKingdom.getItems().addAll(kingdoms);
        KnightKingdom.getSelectionModel().select(kingdoms.get(0));
        Goal.getItems().removeAll(Goal.getItems());
        Goal.getItems().addAll(goals);
        statusText.setText("");
        Goal.getSelectionModel().select(goals.get(0));
        
        // for choose image button
        chooseImage.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("GIF", "*.gif"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                        new FileChooser.ExtensionFilter("PNG", "*.png")
                    );
                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                    file = fileChooser.showOpenDialog(window);
                    imageURL.setText(file.toString()); //shows file path in textfield
                }
            });
    }

    /*
     * Clears textfields.
     */
    public void setUpText() {
        KnightId.setText("");
        KnightName.setText("");
        KnightAttack.setText("");
        KnightHealth.setText("");
        KnightDefense.setText("");
    }

    /*
     * Adds a Knight to DB.
     */
    public void addKnight() {
        int shieldNum = KnightShield.getSelectionModel().getSelectedIndex() + 1;
        int weaponNum = KnightWeapon.getSelectionModel().getSelectedIndex() + 1;
        int kingdomNum = KnightKingdom.getSelectionModel().getSelectedIndex() + 1;
        int goalNum = Goal.getSelectionModel().getSelectedIndex() + 1;
      
        byte[] fileContent;
        // initialize a byte array of size of the file
        if(file == null) {
            fileContent = null;
        }
        else {
            fileContent = new byte[(int) file.length()];
            FileInputStream inputStream = null;
            try {
                // create an input stream pointing to the file
                inputStream = new FileInputStream(file);
                // read the contents of file into byte array
                inputStream.read(fileContent);
            } catch (IOException e) {

            } finally {
                // close input stream
                if (inputStream != null) {
                    try{
                        inputStream.close();
                    }
                    catch(Exception e) {

                    }
                }
            }
        }
        
        boolean result = db.addKnight(KnightId, KnightName, KnightAttack, KnightDefense, KnightHealth, weaponNum, shieldNum, kingdomNum, goalNum, fileContent);
        if(result == true) {
            try{ 
                byte[] array = db.getPicture(77);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                Image im = SwingFXUtils.toFXImage(b,null);
                Status.setImage(im);
                statusText.setText("Added Knight Successfully");
                setUpText(); //clears fields  
            }
            catch (Exception e){
                System.out.println(e);
            }  
        }
        else{ 
            try{ 
                byte[] array = db.getPicture(78);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                Image im = SwingFXUtils.toFXImage(b,null);
                Status.setImage(im);
                statusText.setText("Error: Did not add Knight Successfully. " +
                    "Check to make sure id is unique AND/OR id, attack, defense, and health are integers.");
                setUpText(); //clears fields  
            }
            catch (Exception e){
                System.out.println(e);
            }  
        }
        setUpText(); //clears fields    
    }
}
