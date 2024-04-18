package GUI;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import domein.DomeinController;
import domein.DominoTegel;
import dto.SpelerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SpelApplicatieGUI {
    private RegistreerSpelerController registreerSpelerController;
    private SceneSwitchController sceneSwitchController;
    private SpelController spelController;
    private final DomeinController dc;

    @FXML private Button nederlandsButton;
    @FXML private Button fransButton;
    @FXML private Button engelsButton;
    @FXML private Button speelBtn;
    @FXML private Button registreerBtn;
    @FXML private Button afsluitenBtn;
    private Scanner input = new Scanner(System.in);

    public SpelApplicatieGUI()
    {
        this.dc = new DomeinController();
        this.registreerSpelerController = new RegistreerSpelerController(dc);
        this.spelController = new SpelController(dc);
        this.sceneSwitchController = new SceneSwitchController(new Stage());
    }


    /*-----------------------------------------------------------------------------SPEL CONTROLLER---------------------------------------------------------------*/
    private void speelBeurt()
    {
        spelController.speelBeurt();
    }
    private void toonTegelLijst(List<DominoTegel> lijst)
    {
        spelController.toonTegelLijst(lijst);
    }

    public void spelSituatie()
    {
        spelController.spelSituatie();
    }

    public void speelRonde()
    {
        spelController.speelRonde();
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

    public void switchToHomescreen(MouseEvent event) throws IOException
    {
        sceneSwitchController.switchToHomescreen(event);
    }

    @FXML
    public void switchToSpeelScene(ActionEvent event) throws IOException
    {
        sceneSwitchController.switchToSpeelScene(event);
    }

    @FXML
    public void switchToBordScene(MouseEvent event) throws IOException
    {
        sceneSwitchController.switchToBordScene(event);
    }

    public void afsluiten(ActionEvent event) {
        sceneSwitchController.afsluiten(event);
    }

    /*-----------------------------------------------------------------------------SCENE SWITCH---------------------------------------------------------------*/

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


