package main;

import domein.DomeinController;
import GUI.HomepageController;
import domein.TegelGebied;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import ui.SpelApplicatie;

import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

public class StartUpGUI extends Application
{

    MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws Exception
    {
        music();
        DomeinController dc = new DomeinController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Homepage.fxml"));
        loader.setController(new HomepageController(dc));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("KingDomino G59");
        stage.getIcons().add(new Image(getClass().getResource("/img/LogoKingdominoPNG.png").toExternalForm()));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

public void music()
    {
        String muziekPath = "/sounds/bg_music.mp3";
        Media bgMusic = new Media(getClass().getResource(muziekPath).toString());
        mediaPlayer = new MediaPlayer(bgMusic);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}


