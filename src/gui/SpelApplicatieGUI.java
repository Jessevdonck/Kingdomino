package GUI;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import com.sun.tools.javac.Main;
import domein.DomeinController;
import domein.DominoTegel;
import domein.Spel;
import domein.Speler;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Kleur;

public class SpelApplicatieGUI {
    //gui attributen registreerSpeler
    @FXML
    private Button registreerBalk = new Button();
    @FXML
    private TextField gebruikersnaamBalk = new TextField();
    @FXML
    private TextField geboortejaarBalk = new TextField();
    @FXML
    private Label wrongLogIn = new Label();
    //gui attributen startSpel
    @FXML
    private ImageView blauwImageView;

    @FXML
    private ImageView geelImageView;

    @FXML
    private ListView<SpelerDTO> geselecteerdeSpelers;

    @FXML
    private ImageView groenImageView;

    @FXML
    private ListView<SpelerDTO> ongeselecteerdeSpelers;

    @FXML
    private ImageView roosImageView;

    @FXML
    private Button verwijderButton;

    @FXML
    private Button voegToeButton;
    private ObservableList<SpelerDTO> geselecteerdeSpelersList;
    private ObservableList<SpelerDTO> ongeselecteerdeSpelersList;

    //gui attributen sceneChange
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);


    public void laadSpelersInListView()
    {
        SpelerDTO[] spelersArray = dc.geefAlleSpelers();
        ongeselecteerdeSpelersList = FXCollections.observableArrayList(spelersArray);

        ongeselecteerdeSpelers.setItems(ongeselecteerdeSpelersList);

    }

    public void kiesSpelers()
    {

    }
    public SpelApplicatieGUI()
    {
        this.dc = new DomeinController();
    }
    public SpelApplicatieGUI(DomeinController dc) {
        this.dc = dc;
    }

    public void startSpel()
    {

        }


    private void speelBeurt(){
        toonTegelLijst(dc.getSpel().geefTweedeKolom());
        System.out.println("Welke tegel wil je nemen?");
        int tegel = input.nextInt();

        dc.getVolgordeKoning().remove(0);
    }
    private void toonTegelLijst(List<DominoTegel> lijst){
        int count = 0;
        for (DominoTegel tegel : lijst){
            count++;
            System.out.printf("%d : %s", count, tegel.toString());

        }
    }

    private void VraagKleur(Kleur[] kleurenarray){
        int i = 0;
        for(Kleur kleur : kleurenarray) {

            System.out.printf("%d : %s \n", i+1, kleur);
            i++;
        }
    }


    public void geefSpelersAlsKeuze(SpelerDTO[] spelers) {
        for (int i = 0; i <= spelers.length - 1; i++) {

            System.out.printf("%d : %s, %d \t | \t", i+1, spelers[i].gebruikersnaam(), spelers[i].geboortejaar());
            if(i % 2 == 1) {
                System.out.println(); }
        }
    }

    public void spelSituatie() {
        HashMap<SpelerDTO, Kleur> spelers = dc.getSpelendeSpelers();
        for (SpelerDTO speler : spelers.keySet()) {
            System.out.println(speler.gebruikersnaam() + " speelt met kleur " + spelers.get(speler));
        }

        // TODO - Zijn koninkrijk ;Zijn koning op een dominotegel in de startkolom of de eindkolom

        List<DominoTegelDTO> beschikbareTegels = dc.getStartKolom();
        System.out.println("Beschikbare tegels:");
        for (DominoTegelDTO tegel : beschikbareTegels) {
            System.out.printf("%d", tegel.tegelNummer());
        }

        List<DominoTegelDTO> startkolom = dc.getStartKolom();
        System.out.println("Startkolom:");
        for (DominoTegelDTO tegel : startkolom) {
            System.out.printf("%s : %s", tegel.landschapType1().toString(), tegel.landschapType2().toString());
        }

        List<DominoTegelDTO> tweedekolom = dc.getTweedeKolom();
        System.out.println("TweedeKolom:");
        for (DominoTegelDTO tegel : tweedekolom) {
            System.out.printf("%s : %s", tegel.landschapType1().toString(), tegel.landschapType2().toString());
        }

        // TODO - implement SpelApplicatie.spelSituatie
    }

    public void speelRonde() {
        // TODO - implement SpelApplicatie.speelRonde
        speelBeurt();
        if (dc.isEindeSpel()) {
            System.out.println("Het spel is afgelopen.");
        }
    }

    public void registreerSpeler() {


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
            wrongLogIn.setStyle("-fx-text-fill: green;");

        } catch (GebruikersnaamInGebruikException | TeJongeGebruikerException | OngeldigeGebruikersnaamException | SpatiesInGebruikersnaamException |
                 OntbrekendeGebruikersnaamException | IllegalArgumentException e)
        {
            wrongLogIn.setText(e.getMessage());
            wrongLogIn.setStyle("-fx-text-fill: red;");
        }
    }

    public void switchToRegisterScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHomescreen(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Homepage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToSpeelScene(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/spelersKiezen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SpelApplicatieGUI spelApplicatieGUI = new SpelApplicatieGUI();
        spelApplicatieGUI.laadSpelersInListView();
}


    public void afsluiten(ActionEvent event) {
        System.exit(0);
    }
}
