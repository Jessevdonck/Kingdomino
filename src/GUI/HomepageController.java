package GUI;
import java.io.IOException;
import java.util.*;
import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class HomepageController
    {
    private final RegistreerSpelerController registreerSpelerController;
    private final SceneSwitchController sceneSwitchController;;
    private final DomeinController dc;

    @FXML private Button speelBtn;
    @FXML private Button registreerBtn;
    @FXML private Button afsluitenBtn;

    public HomepageController()
    {
        this.dc = new DomeinController();
        this.registreerSpelerController = new RegistreerSpelerController(dc);
        this.sceneSwitchController = new SceneSwitchController(new Stage());
    }

    /*---------------------------------------------------------------------------REGISTREER SPELER-------------------------------------------------------------*/
    @FXML
    public void registreerSpeler()
    {
        registreerSpelerController.registreerSpeler();
    }

    /*-----------------------------------------------------------------------------SCENE SWITCH---------------------------------------------------------------*/
    @FXML
    public void switchToRegisterScene(ActionEvent event) throws IOException
    {
       sceneSwitchController.switchToRegisterScene(event);
    }

    @FXML
    public void switchToSpeelScene(ActionEvent event) throws IOException
    {
        sceneSwitchController.switchToSpeelScene(event);
    }

    public void afsluiten(ActionEvent event) {
        sceneSwitchController.afsluiten(event);
    }

    /*-----------------------------------------------------------------------------LAAD TAAL---------------------------------------------------------------*/

    private void laadLanguage(String lang)
    {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);
        speelBtn.setText(bundle.getString("speel"));
        registreerBtn.setText(bundle.getString("registreer"));
        afsluitenBtn.setText(bundle.getString("afsluiten"));

    }
    @FXML
    private void switchNaarNederlands(MouseEvent event)
    {
        laadLanguage("nl");
    }

    @FXML
    private void switchNaarFrans(MouseEvent event)
    {
        laadLanguage("fr");
    }

    @FXML
    private void switchNaarEngels(MouseEvent event)
    {
        laadLanguage("en");
    }

}


