package GUI;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

    public void switchToRegisterScene(ActionEvent event, DomeinController dc, TaalController tc) throws IOException
    {
        MediaPlayerSingleton.getInstanceSoundFX().play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        loader.setController(new RegistreerSpelerController(dc, tc));
        Parent root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Image cursorImage = new Image(getClass().getResourceAsStream("/img/middle-ages-cursor-32x32.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        stage.setScene(scene);
        stage.show();
        System.out.println(tc.getLanguage());
    }

    public void switchToHomescreen(MouseEvent event, DomeinController dc, TaalController tc) throws IOException {
        MediaPlayerSingleton.getInstanceSoundFX().play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Homepage.fxml"));
        loader.setController(new HomepageController(dc));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Image cursorImage = new Image(getClass().getResourceAsStream("/img/middle-ages-cursor-32x32.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSpeelScene(ActionEvent event, DomeinController dc, TaalController tc) throws IOException {
        MediaPlayerSingleton.getInstanceSoundFX().play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/spelersKiezen.fxml"));
        loader.setController(new SpelerSelectieController(dc, tc));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Image cursorImage = new Image(getClass().getResourceAsStream("/img/middle-ages-cursor-32x32.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOptiesScene(ActionEvent event, DomeinController dc, TaalController tc) throws IOException {
        MediaPlayerSingleton.getInstanceSoundFX().play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/opties.fxml"));
        loader.setController(new OptiesController(dc, tc));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Image cursorImage = new Image(getClass().getResourceAsStream("/img/middle-ages-cursor-32x32.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBordScene(MouseEvent event, DomeinController dc) throws IOException {
        Parent root;

        MediaPlayerSingleton.getInstanceSoundFX().play();
        if (dc.getSpelendeSpelers().size() == 3)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Bord3Spelers.fxml"));
            loader.setController(new SpelController(dc, loader));
            root = loader.load();
        } else if(dc.getSpelendeSpelers().size() == 4) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Bord4Spelers.fxml"));
            loader.setController(new SpelController(dc, loader));
            root = loader.load();
        } else throw new IllegalArgumentException("Fout aantal spelers");


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Image cursorImage = new Image(getClass().getResourceAsStream("/img/middle-ages-cursor-32x32.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double centerX = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2;
        double centerY = bounds.getMinX() + (bounds.getHeight() - scene.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);

        stage.show();
    }

    public void switchToScoreScene(ActionEvent event, DomeinController dc, TaalController tc) throws IOException {
        MediaPlayerSingleton.getInstanceSoundFX().play();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScoreScherm.fxml"));
        loader.setController(new ScoreController(dc, tc));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Image cursorImage = new Image(getClass().getResourceAsStream("/img/middle-ages-cursor-32x32.png"));
        scene.setCursor(new ImageCursor(cursorImage));
        stage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double centerX = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2;
        double centerY = bounds.getMinX() + (bounds.getHeight() - scene.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);

        stage.show();
    }

    public void afsluiten(ActionEvent event)
    {
        MediaPlayerSingleton.getInstanceSoundFX().play();
        System.exit(0);
    }

}
