package GUI;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class HomepageController implements Initializable
    {
    private final RegistreerSpelerController registreerSpelerController;
    private final SceneSwitchController sceneSwitchController;;
    private final DomeinController dc;
    private final TaalController tc;

    @FXML private Button speelBtn;
    @FXML private Button registreerBtn;
    @FXML private Button afsluitenBtn;

    public HomepageController(DomeinController dc)
    {
        this.dc = dc;
        this.tc = TaalController.getInstance();
        this.registreerSpelerController = new RegistreerSpelerController(dc, tc);
        this.sceneSwitchController = new SceneSwitchController(dc);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
        {
            laadLanguage();
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
       sceneSwitchController.switchToRegisterScene(event, this.dc, tc);
    }

    @FXML
    public void switchToSpeelScene(ActionEvent event) throws IOException
    {
        sceneSwitchController.switchToSpeelScene(event, this.dc, tc);
    }

    public void afsluiten(ActionEvent event) {
        sceneSwitchController.afsluiten(event);
    }

    /*-----------------------------------------------------------------------------LAAD TAAL---------------------------------------------------------------*/

    private void laadLanguage(String lang) //Wordt gebruikt wanneer taal nog niet gekozen is
    {
        Locale locale = new Locale(tc.getLanguage());
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);
        speelBtn.setText(bundle.getString("speel"));
        registreerBtn.setText(bundle.getString("registreer"));
        afsluitenBtn.setText(bundle.getString("afsluiten"));

    }

    public void laadLanguage() //Wordt gebruikt wanneer er al taal gekozen is
    {
        String gekozenTaal = tc.getLanguage();
        Locale locale = new Locale(gekozenTaal);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);
        speelBtn.setText(bundle.getString("speel"));
        registreerBtn.setText(bundle.getString("registreer"));
        afsluitenBtn.setText(bundle.getString("afsluiten"));
    }
    @FXML
    private void switchNaarNederlands(MouseEvent event)
    {
        tc.setLanguage("nl");
        laadLanguage("nl");

    }

    @FXML
    private void switchNaarFrans(MouseEvent event)
    {
        tc.setLanguage("fr");
        laadLanguage("fr");
    }

    @FXML
    private void switchNaarEngels(MouseEvent event)
    {
        tc.setLanguage("en");
        laadLanguage("en");
    }


    }


