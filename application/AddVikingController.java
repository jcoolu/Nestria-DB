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
 * Controller for AddViking.fxml. User can add a Viking to the DB (id HAS to be unique)
 */
public class AddVikingController  extends MainMenuController implements Initializable{
    private NestriaDB db = new NestriaDB();
    private ObservableList<String> options = db.getWeaponsForCombo();
    private ObservableList<String> shields = db.getShieldsForCombo();
    private ObservableList<String> tribes = db.getTribesForCombo();
    private ObservableList<String> goals = db.getGoalsForCombo();

    @FXML private TextField vikingId;
    @FXML private TextField vikingName;
    @FXML private TextField vikingAttack;
    @FXML private TextField vikingHealth;
    @FXML private TextField vikingDefense;
    @FXML private ComboBox vikingWeapon = new ComboBox(options);
    @FXML private ComboBox vikingShield = new ComboBox(shields);
    @FXML private ComboBox vikingTribe = new ComboBox(tribes);
    @FXML private ComboBox goal = new ComboBox(goals);
    @FXML private ImageView status;
    @FXML private TextField statusText;
    @FXML private Button chooseImage;
    @FXML private TextField imageURL;
    @FXML private TableView<Player> ids = new TableView<Player>();
    @FXML private TableColumn<Player, Integer> idColumn;
    @FXML private TextField searchId;
    
    private FileChooser fileChooser;
    private File file;

    /**
     * When AddVikingController is called
     */
    public void initialize(URL url, ResourceBundle rb) {
        vikingWeapon.getItems().removeAll(vikingWeapon.getItems());
        vikingWeapon.getItems().addAll(options);
        vikingWeapon.getSelectionModel().select(options.get(0));
        vikingWeapon.getItems().removeAll(vikingWeapon.getItems());
        vikingWeapon.getItems().addAll(shields);
        vikingWeapon.getSelectionModel().select(shields.get(0));
        vikingTribe.getItems().removeAll(vikingTribe.getItems());
        vikingTribe.getItems().addAll(tribes);
        vikingTribe.getSelectionModel().select(tribes.get(0));
        goal.getItems().removeAll(goal.getItems());
        goal.getItems().addAll(goals);
        goal.getSelectionModel().select(goals.get(0));
        statusText.setText("");

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
        vikingId.setText("");
        vikingName.setText("");
        vikingAttack.setText("");
        vikingHealth.setText("");
        vikingDefense.setText("");
    }

    /*
     * Adds a viking to the DB.
     */
    public void addViking() {
        int shieldNum = vikingShield.getSelectionModel().getSelectedIndex() + 1;
        int weaponNum = vikingWeapon.getSelectionModel().getSelectedIndex() + 1;
        int kingdomNum = vikingTribe.getSelectionModel().getSelectedIndex() + 1;
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

        boolean result = db.addViking(vikingId, vikingName, vikingAttack, vikingDefense, vikingHealth, weaponNum, shieldNum, kingdomNum, goalNum, fileContent);
        if(result == true) {
            try{ 
                byte[] array = db.getPicture(77);
                ByteArrayInputStream bis = new ByteArrayInputStream(array);
                BufferedImage b = ImageIO.read(bis);
                Image im = SwingFXUtils.toFXImage(b,null);
                status.setImage(im);
                statusText.setText("Added Viking Successfully");
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
                statusText.setText("Error: Did not add Viking Successfully. " +
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
