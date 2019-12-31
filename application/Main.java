package application; 

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.fxml.FXMLLoader;
import java.io.*;

/**
 * Main method of program. Starts application and Start Screen is opened. 
 */
public class Main extends Application {
    private MediaView view;
    private MediaPlayer mediaPlayer;

    /**
     * Starts program and opens to "Start" screen. User is presented  with a
     * scene where the only option is to press the button "Start Game."
     */
    @Override
    public void start(Stage primaryStage) {
        try {

            NestriaDB db = new NestriaDB();

            if (!db.isOpen()) {
                System.out.printf("Could not connect to database.%n");
                System.exit(1);
            }

            db.close();
            StackPane root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));

            Scene scene = new Scene(root,1280,720);

            File file = new File("application\\NestriaTitleScreen.mp4");
            String media = file.toURI().toString();
            Media musicFile = new Media(media);
            mediaPlayer = new MediaPlayer(musicFile);
            mediaPlayer.setAutoPlay(true);
            view = new MediaView(mediaPlayer);
            mediaPlayer.onRepeatProperty();
            view.setPreserveRatio(true);
            view.autosize();
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                    public void run() {
                        mediaPlayer.seek(Duration.ZERO);
                    }
                });
            root.getChildren().add(0, view);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Nestria");
            primaryStage.setMaxHeight(720);
            primaryStage.setMaxWidth(1280);
            primaryStage.setMinHeight(720);
            primaryStage.setMinWidth(1280);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches program (FXML loads and start method is called)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns media player
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Returns media view
     */
    public MediaView getViewPlayer() {
        return view;
    }
}

