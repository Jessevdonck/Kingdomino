package GUI;

import domein.DomeinController;
import exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistreerSpelerController implements Initializable
{
    private final DomeinController dc;
    private final SceneSwitchController sceneSwitchController;
    private final TaalController tc;
    @FXML
    private Button registreerBalk = new Button();
    @FXML
    private TextField gebruikersnaamBalk = new TextField();
    @FXML
    private TextField geboortejaarBalk = new TextField();
    @FXML
    private Label wrongLogIn = new Label();

    public RegistreerSpelerController(DomeinController dc, TaalController tc)
    {
        this.dc = dc;
        this.tc = tc;
        this.sceneSwitchController = new SceneSwitchController(dc);

    }

    public void laadLanguage()
        {
        String gekozenTaal = tc.getLanguage();
        Locale locale = new Locale(gekozenTaal);
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);
        registreerBalk.setText(bundle.getString("registreerKort"));
        gebruikersnaamBalk.setPromptText(bundle.getString("gebruikersnaam"));
        geboortejaarBalk.setPromptText(bundle.getString("geboortejaar"));
        }


    public void registreerSpeler()
    {
        String gebruikersnaam = gebruikersnaamBalk.getText();
        String geboortejaarString = geboortejaarBalk.getText();

        try {
            if (geboortejaarString.trim().isBlank())
                throw new OntbrekendGeboortejaarException();
        } catch (OntbrekendGeboortejaarException | NumberFormatException e) {
            wrongLogIn.setText(e.getMessage());
            wrongLogIn.setStyle("-fx-text-fill: red;");
        }

        int geboortejaar = Integer.parseInt(geboortejaarBalk.getText());

        try {
            dc.registreerSpeler(gebruikersnaam, geboortejaar);
            wrongLogIn.setText("Speler registreren succesvol!");
            wrongLogIn.setStyle("-fx-text-fill: #00e000;");

        } catch (GebruikersnaamInGebruikException | TeJongeGebruikerException | OngeldigeGebruikersnaamException |
                 SpatiesInGebruikersnaamException |
                 OntbrekendeGebruikersnaamException | IllegalArgumentException e)
        {
            wrongLogIn.setText(e.getMessage());
            wrongLogIn.setStyle("-fx-text-fill: red;");
        }
    }

    public void switchToHomescreen(MouseEvent event) throws IOException
    {
        sceneSwitchController.switchToHomescreen(event,this.dc, tc);
    }

@Override
public void initialize(URL url, ResourceBundle resourceBundle)
    {
    laadLanguage();
    }
}
