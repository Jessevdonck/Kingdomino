package GUI;

import domein.DomeinController;
import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistreerSpelerController
{
    private final DomeinController dc;
    private final SceneSwitchController sceneSwitchController;
    @FXML
    private Button registreerBalk = new Button();
    @FXML
    private TextField gebruikersnaamBalk = new TextField();
    @FXML
    private TextField geboortejaarBalk = new TextField();
    @FXML
    private Label wrongLogIn = new Label();

    public RegistreerSpelerController(DomeinController dc)
    {
        this.dc = dc;
        this.sceneSwitchController = new SceneSwitchController(new Stage());
    }

    public RegistreerSpelerController(){
        this.dc = new DomeinController();
        this.sceneSwitchController = new SceneSwitchController(new Stage());
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
        sceneSwitchController.switchToHomescreen(event);
    }
}
