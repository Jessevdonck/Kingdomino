package main;

import domein.DomeinController;
import GUI.HomepageController;
import domein.TegelGebied;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.SpelApplicatie;

import java.util.Locale;
import java.util.ResourceBundle;

public class StartUpGUI extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        DomeinController dc = new DomeinController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Homepage.fxml"));
        loader.setController(new HomepageController(dc));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("KingDomino G59");
        stage.getIcons().add(new Image(getClass().getResource("/img/LogoKingdominoPNG.png").toExternalForm()));
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
