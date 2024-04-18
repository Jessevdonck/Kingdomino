package GUI;
import domein.DomeinController;
import dto.SpelerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.Kleur;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SpelerSelectieController implements Initializable{



    @FXML
    private Circle groen;
    @FXML
    private Circle blauw;
    @FXML
    private Circle roos;
    @FXML
    private Circle geel;
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
    private Circle huidigGeselecteerdeCircle;

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
                if(geselecteerdeSpelersList.size() >= 4)
                {
                    foutmelding.setText("Maximum 4 spelers toegelaten!");
                    return;
                }
                if (dc.isKleurGekozen(geselecteerdeKleur))
                {
                    foutmelding.setText("De kleur " + geselecteerdeKleur +" is al gekozen");
                    return;
                }
                //ListView updaten
                geselecteerdeSpelers.getItems().add(geselecteerdeSpelerNaam);
                ongeselecteerdeSpelersList.remove(geselecteerdeSpelerNaam);

                //Speler aanmaken
                voegSpelerToeAanGekozenSpelers(geselecteerdeSpeler.gebruikersnaam(), geselecteerdeSpeler.geboortejaar(),
                                               geselecteerdeSpeler.aantalGewonnen(), geselecteerdeSpeler.aantalGespeeld(),
                                               geselecteerdeKleur);

                //Reset Kleurselectie
                huidigGeselecteerdeCircle.setStroke(null);
                huidigGeselecteerdeCircle = null;
                geselecteerdeKleur = null;

                //Alfabetisch sorteren lijsten
                ongeselecteerdeSpelersList.sort(String::compareTo);
                geselecteerdeSpelersList.sort(String::compareTo);

                foutmelding.setText("");
            } else
            {
                foutmelding.setText("Selecteer eerst een speler en een kleur!");
            }

        }

    @FXML
    public void deleteSpeler()
        {
            String geselecteerdeSpeler = geselecteerdeSpelers.getSelectionModel().getSelectedItem();

            System.out.println(geselecteerdeSpeler);

            ongeselecteerdeSpelersList.add(geselecteerdeSpeler);
            geselecteerdeSpelers.getItems().remove(geselecteerdeSpeler);

            dc.verwijderSpelerUitGekozenSpelers(geselecteerdeSpeler);

            //Alfabetisch sorteren
            ongeselecteerdeSpelersList.sort(String::compareTo);
            geselecteerdeSpelersList.sort(String::compareTo);

            //Reset delete knop na gebruik
            geselecteerdeKleur = null;
            huidigGeselecteerdeCircle = null;
        }

    public void selecteerGroen()
        {
            selecteerCircle(groen, Kleur.GROEN);
        }

    public void selecteerBlauw()
        {
            selecteerCircle(blauw, Kleur.BLAUW);
        }

    public void selecteerRoos()
        {
            selecteerCircle(roos, Kleur.ROOS);
        }

    public void selecteerGeel()
        {
            selecteerCircle(geel, Kleur.GEEL);
        }

    public void selecteerCircle(Circle circle, Kleur kleur)
        {
            if (huidigGeselecteerdeCircle != null)
            {
                huidigGeselecteerdeCircle.setStroke(null);
            }

            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(1.5);

            huidigGeselecteerdeCircle = circle;
            geselecteerdeKleur = kleur;
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
        if(geselecteerdeSpelersList.size() < 3)
        {
            foutmelding.setText("Je moet minstens 3 spelers hebben!");
            return;
        }
        ssc.switchToBordScene(event);
    }


}


