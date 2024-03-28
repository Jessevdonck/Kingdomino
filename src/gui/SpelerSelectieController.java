package GUI;

import dto.SpelerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class SpelerSelectieController {
    @FXML
    private ImageView blauwImageView;
    @FXML
    private ImageView geelImageView;
    @FXML
    private ImageView groenImageView;
    @FXML
    private ImageView roosImageView;
    @FXML
    private Button verwijderButton;
    @FXML
    private Button voegToeButton;
    @FXML
    private Button startButton;
    @FXML
    private ListView<String> ongeselecteerdeSpelers;
    @FXML
    private ListView<SpelerDTO> geselecteerdeSpelers;
    private ObservableList<SpelerDTO> geselecteerdeSpelersList;
    private ObservableList<String> ongeselecteerdeSpelersList;

    public SpelerSelectieController(ListView<String> listView) {
        this.ongeselecteerdeSpelers = listView;
        this.ongeselecteerdeSpelersList = FXCollections.observableArrayList();
        this.ongeselecteerdeSpelers.setItems(ongeselecteerdeSpelersList);
    }

    public void laadSpelers(SpelerDTO[] spelersArray)
    {
        String[] spelerNamen = new String[spelersArray.length];
        for (int i = 0; i < spelersArray.length; i++)
        {
            spelerNamen[i] = spelersArray[i].gebruikersnaam();
        }
        ongeselecteerdeSpelersList.setAll(spelerNamen);
    }


    public void updateSpelersList(ObservableList<String> nieuweSpelers)
    {
        this.ongeselecteerdeSpelersList.setAll(nieuweSpelers);
    }

    public ObservableList<String> getSpelers() {
        return ongeselecteerdeSpelersList;
    }

}
