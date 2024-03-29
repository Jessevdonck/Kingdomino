package GUI;
import java.io.IOException;
import java.util.*;
import java.util.List;
import domein.DomeinController;
import domein.DominoTegel;
import dto.SpelerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SpelApplicatieGUI {
    private RegistreerSpelerController registreerSpelerController;
    private SceneSwitchController sceneSwitchController;
    private SpelController spelController;
    private SpelerSelectieController spelerSelectieController;
    private final DomeinController dc;

    private ObservableList<String> spelers = FXCollections.observableArrayList();
    @FXML ListView<String> ongeselecteerdeSpelers;


    private Scanner input = new Scanner(System.in);

    public SpelApplicatieGUI()
    {
        this.dc = new DomeinController();
        this.registreerSpelerController = new RegistreerSpelerController(dc);
        this.spelController = new SpelController(dc);
        this.sceneSwitchController = new SceneSwitchController(new Stage());
        this.ongeselecteerdeSpelers = new ListView<>();
        this.spelerSelectieController = new SpelerSelectieController(ongeselecteerdeSpelers);

        SpelerDTO[] alleSpelers = dc.geefAlleSpelers();

        for (SpelerDTO speler : alleSpelers)
        {
            spelers.add(speler.gebruikersnaam());
        }

        this.ongeselecteerdeSpelers.setItems(spelers);
    }


    @FXML
    public void laadSpelers()
    {
        spelerSelectieController.laadSpelers(dc.geefAlleSpelers());
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
}


