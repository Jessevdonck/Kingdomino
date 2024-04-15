package GUI;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SpelerSelectieController implements Initializable{




    @FXML
    private ListView<String> ongeselecteerdeSpelers;
    @FXML
    private ListView<String> geselecteerdeSpelers;
    @FXML
    private ImageView addButton;
    @FXML
    private ImageView deleteButton;

    private ObservableList<String> ongeselecteerdeSpelersList;
    private ObservableList<String> geselecteerdeSpelersList;
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
            String geselecteerdeSpeler = ongeselecteerdeSpelers.getSelectionModel().getSelectedItem();
            geselecteerdeSpelers.getItems().add(geselecteerdeSpeler);
            ongeselecteerdeSpelersList.remove(geselecteerdeSpeler);

            ongeselecteerdeSpelersList.sort(String::compareToIgnoreCase);
            geselecteerdeSpelersList.sort(String::compareToIgnoreCase);
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
