package GUI;

import domein.DomeinController;
import domein.Spel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitchController
{
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DomeinController dc;



    public SceneSwitchController(DomeinController dc)
    {
        this.stage = new Stage();
        this.dc = dc;
    }

    public void switchToRegisterScene(ActionEvent event, DomeinController dc) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        loader.setController(new RegistreerSpelerController(dc));
        Parent root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHomescreen(MouseEvent event, DomeinController dc) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Homepage.fxml"));
        loader.setController(new HomepageController(dc));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSpeelScene(ActionEvent event, DomeinController dc) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/spelersKiezen.fxml"));
        loader.setController(new SpelerSelectieController(dc));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBordScene(MouseEvent event, DomeinController dc) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Bord.fxml"));
        loader.setController(new SpelController(dc));
        Parent root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double centerX = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2;
        stage.setX(centerX);
        stage.setY(0);

        stage.show();
    }

    public void afsluiten(ActionEvent event)
    {
        System.exit(0);
    }

}
