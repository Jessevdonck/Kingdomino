package GUI;
import domein.DomeinController;
import dto.SpelerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.Kleur;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SpelerSelectieController implements Initializable{



    @FXML
    private ImageView groenImageView;
    @FXML
    private ImageView blauwImageView;
    @FXML
    private ImageView roosImageView;
    @FXML
    private ImageView geelImageView;
    @FXML
    private ListView<String> ongeselecteerdeSpelers;
    @FXML
    private ListView<String> geselecteerdeSpelers;
    @FXML
    private ImageView addButton;
    @FXML
    private ImageView deleteButton;
    @FXML
    private Label foutmelding;

    private ObservableList<String> ongeselecteerdeSpelersList;
    private ObservableList<String> geselecteerdeSpelersList;
    private String geselecteerdeSpelerNaam;
    private SpelerDTO geselecteerdeSpeler;
    private Kleur geselecteerdeKleur;

    private DomeinController dc;
    private SceneSwitchController ssc;



    public SpelerSelectieController()
        {
            this.dc = new DomeinController();
            this.ssc = new SceneSwitchController();
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
        {
            ongeselecteerdeSpelersList = FXCollections.observableArrayList();
            geselecteerdeSpelersList = FXCollections.observableArrayList();
            laadSpelers(dc.geefAlleSpelers());
        }
/*---------------------------------------LIST POPULATING---------------------------------------------------*/
    public void laadSpelers(SpelerDTO[] spelersArray)
        {
            List<SpelerDTO> spelers = Arrays.asList(spelersArray);
            List<String> spelerNamen = new ArrayList<String>();

            for (SpelerDTO speler : spelers)
            {
                spelerNamen.add(speler.gebruikersnaam());
            }
            ongeselecteerdeSpelers.setItems(ongeselecteerdeSpelersList);
            geselecteerdeSpelers.setItems(geselecteerdeSpelersList);
            ongeselecteerdeSpelersList.setAll(spelerNamen);
            System.out.println(ongeselecteerdeSpelersList);
        }

    @FXML
    public void addSpeler()
        {
            geselecteerdeSpelerNaam = ongeselecteerdeSpelers.getSelectionModel().getSelectedItem();
            geselecteerdeSpeler = Arrays.stream(dc.geefAlleSpelers())
                    .filter(speler -> speler.gebruikersnaam().equals(geselecteerdeSpelerNaam))
                    .findFirst()
                    .orElse(null);

            if(geselecteerdeSpelerNaam != null && geselecteerdeKleur != null) {

                geselecteerdeSpelers.getItems().add(geselecteerdeSpelerNaam);
                ongeselecteerdeSpelersList.remove(geselecteerdeSpeler);

                voegSpelerToeAanGekozenSpelers(geselecteerdeSpeler.gebruikersnaam(), geselecteerdeSpeler.geboortejaar(),
                                               geselecteerdeSpeler.aantalGewonnen(), geselecteerdeSpeler.aantalGespeeld(),
                                               geselecteerdeKleur);

                //Alfabetisch sorteren lijsten
                ongeselecteerdeSpelersList.sort(String::compareToIgnoreCase);
                geselecteerdeSpelersList.sort(String::compareToIgnoreCase);
            } else
            {
                foutmelding.setText("Selecteer eerst een speler en een kleur!");
            }

        }

    @FXML
    public void deleteSpeler()
        {
            String geselecteerdeSpeler = geselecteerdeSpelers.getSelectionModel().getSelectedItem();
            ongeselecteerdeSpelersList.add(geselecteerdeSpeler);
            geselecteerdeSpelers.getItems().remove(geselecteerdeSpeler);

            ongeselecteerdeSpelersList.sort(String::compareToIgnoreCase);
            geselecteerdeSpelersList.sort(String::compareToIgnoreCase);
        }

    public void selecteerGroen()
        {
            groenImageView.setStyle("-fx-border-color: white; -fx-border-width: 2;");
            geselecteerdeKleur = Kleur.GROEN;
        }

    public void selecteerBlauw()
        {
            geselecteerdeKleur = Kleur.BLAUW;
        }

    public void selecteerRoos()
        {
            geselecteerdeKleur = Kleur.ROOS;
        }

    public void selecteerGeel()
        {
            geselecteerdeKleur = Kleur.GEEL;
        }

    public void voegSpelerToeAanGekozenSpelers(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, Kleur kleur)
        {
            dc.voegSpelerToeAanGekozenSpelers(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld, kleur);
        }

/*----------------------------------------------------SCENE SWITCH-----------------------------------------------*/

public void switchToHomescreen(MouseEvent event) throws IOException
    {
        ssc.switchToHomescreen(event);
    }

public void switchToBordScene(MouseEvent event) throws IOException
    {
        ssc.switchToBordScene(event);
    }

}
