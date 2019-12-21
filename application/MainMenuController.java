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
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

public class MainMenuController implements Initializable {

    @FXML private ImageView background;
    private NestriaDB db = new NestriaDB();

    public void initialize(URL url, ResourceBundle rb) {
        try{ 
            byte[] array = db.getPicture(27);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            BufferedImage b = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(b,null);
            background.setImage(image);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void goToArena(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("Arena.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        NestriaDB db = new NestriaDB();
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToAddPlayer(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("AddPlayerScene1.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToHowToPlay(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HowToPlay.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToHealPlayer(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("HealPlayer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void goToViewInfo(ActionEvent event) throws IOException, SQLException {
        AnchorPane tableViewParent = FXMLLoader.load(getClass().getResource("ViewInfo.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void quitGame(ActionEvent event) throws IOException{
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
