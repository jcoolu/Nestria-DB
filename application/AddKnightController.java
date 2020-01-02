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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Controller for AddKnight.fxml. User can add a Knight to the DB (id HAS to be unique)
 */
public class AddKnightController extends MainMenuController implements Initializable{
    private NestriaDB db = new NestriaDB();
    ObservableList<String> options = db.getWeaponsForCombo();
    ObservableList<String> shields = db.getShieldsForCombo();
    ObservableList<String> kingdoms = db.getKingdomsForCombo();
    ObservableList<String> goals = db.getGoalsForCombo();

    @FXML private TextField knightId;
    @FXML private TextField knightName;
    @FXML private TextField knightAttack;
    @FXML private TextField knightHealth;
    @FXML private TextField knightDefense;
    @FXML private ComboBox knightWeapon = new ComboBox(options);
    @FXML private ComboBox knightShield = new ComboBox(shields);
    @FXML private ComboBox knightKingdom = new ComboBox(kingdoms);
    @FXML private ComboBox goal= new ComboBox(goals);
    @FXML private ImageView status;
    @FXML private TextField statusText;
    @FXML private Button chooseImage;
    @FXML private TextField imageURL;
    @FXML private TableView<Player> ids = new TableView<Player>();
    @FXML private TableColumn<Player, Integer> idColumn;
    @FXML private TextField searchId;

    private FileChooser fileChooser;
    private File file;

    /*
     * When AddKnightController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        //for Combo box
        knightWeapon.getItems().removeAll(knightWeapon.getItems());
        knightWeapon.getItems().addAll(options);
        knightWeapon.getSelectionModel().select(options.get(1));
        knightShield.getItems().removeAll(knightShield.getItems());
        knightShield.getItems().addAll(shields);
        knightShield.getSelectionModel().select(shields.get(1));
        knightKingdom.getItems().removeAll(knightKingdom.getItems());
        knightKingdom.getItems().addAll(kingdoms);
        knightKingdom.getSelectionModel().select(kingdoms.get(1));
        goal.getItems().removeAll(goal.getItems());
        goal.getItems().addAll(goals);
        statusText.setText("");
        goal.getSelectionModel().select(goals.get(0));

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

        idColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("id"));
        ids.setItems(db.viewPlayers());
        FilteredList<Player> filteredData = new FilteredList<>(db.viewPlayers(), p -> true);

        searchId.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(player -> {
                        // If filter text is empty, display all ids.
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        // Compare id
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (Integer.toString(player.getId()).contains(lowerCaseFilter)) {
                            return true; // Filter matches id
                        } 
                        return false; // Does not match.
                    });
            });        
            // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Player> sortedData = new SortedList<>(filteredData);
        
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(ids.comparatorProperty());
        
        // 5. Add sorted (and filtered) data to the table.
        ids.setItems(sortedData);
    }

    /*
     * Clears textfields.
     */
    public void setUpText() {
        knightId.setText("");
        knightName.setText("");
        knightAttack.setText("");
        knightHealth.setText("");
        knightDefense.setText("");
    }

    /*
     * Adds a Knight to DB.
     */
    public void addKnight() {
        int shieldNum = knightShield.getSelectionModel().getSelectedIndex() + 1;
        int weaponNum = knightWeapon.getSelectionModel().getSelectedIndex() + 1;
        int kingdomNum = knightKingdom.getSelectionModel().getSelectedIndex() + 1;
        int goalNum = goal.getSelectionModel().getSelectedIndex() + 1;

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

        boolean result = db.addKnight(knightId, knightName, knightAttack, knightDefense, knightHealth, weaponNum, shieldNum, kingdomNum, goalNum, fileContent);
        if(result == true) {
            try{ 
                byte[] array = db.getPicture(77);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                Image im = SwingFXUtils.toFXImage(b,null);
                status.setImage(im);
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
                status.setImage(im);
                statusText.setText("Error: Did not add Knight Successfully. " +
                    "Check to make sure id is unique AND/OR id, attack, defense, and health are integers.");
                setUpText(); //clears fields  
            }
            catch (Exception e){
                System.out.println(e);
            }  
        }
        setUpText(); //clears fields    
        ids.setItems(db.viewPlayers());
    }
}
