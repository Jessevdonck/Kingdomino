package GUI;

import domein.DomeinController;
import domein.DominoTegel;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import util.Kleur;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SpelController implements Initializable
{
    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);

    @FXML private GridPane gridPane1;
    @FXML private GridPane gridPane2;
    @FXML private GridPane gridPane3;
    @FXML private GridPane gridPane4;
    @FXML private VBox beginKolom;
    @FXML private VBox eindKolom;
    @FXML private HBox stapel;
    @FXML private Label instructieMelding;
    @FXML private Button bevestigBtn;
    @FXML private Circle keuzeTegelBeginKolom1;
    @FXML private Circle keuzeTegelBeginKolom2;
    @FXML private Circle keuzeTegelBeginKolom3;
    @FXML private Circle keuzeTegelBeginKolom4;
    @FXML private Circle keuzeTegelEindKolom1;
    @FXML private Circle keuzeTegelEindKolom2;
    @FXML private Circle keuzeTegelEindKolom3;
    @FXML private Circle keuzeTegelEindKolom4;
    @FXML private Label instructieTekst;
    @FXML private ImageView stapelImageView;
    @FXML private Button verwijderBtn;
    @FXML private Button bevestignBtn;
    @FXML private Button volgendeBtn;
    @FXML private VBox eindKolomKeuze;
    @FXML private VBox beginKolomKeuze;

    @FXML
    private ImageView draggedImageView;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;


    private FXMLLoader loader;

    private int gekozenCirkel = 0;
    Circle geselecteerdeCirkel;

    private List<Kleur> kleurenSpelers;
    private int huidigeSpelerIndex = 0;
    private int rondeNummer = 1;
    Paint geselecteerdeKleurPaint;
    private final String[] startTegelImagePath =
            {
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_blauw.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/geel.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_groen.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_roos.png"
            };


    public SpelController(DomeinController dc, FXMLLoader loader)
        {
            this.loader = loader;
            this.dc = dc;
        }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        plaatsStartTegels();
        plaatsTegelsInBeginKolom(getBeginKolomTegels(), beginKolom);
        plaatsTegelsInEindKolom(getEindKolomTegels(), eindKolom);
        plaatsTegelInStapel(getStapel(), stapel);
        speelBeurtEersteRonde();
        //startSpel();

        for (Node node : beginKolom.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setOnMouseClicked(this::imageViewGeklik);
            }
        }
        for (Node node : eindKolom.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                imageView.setOnMouseClicked(this::imageViewGeklik);
            }
        }

    }
/*-------------------------------------------------FRONTEND---------------------------------------------------*/
    public void laadStarttegels(GridPane gridPane, String startTegelImagePath)
    {
      ImageView imageView = new ImageView(new Image(startTegelImagePath));



      gridPane.widthProperty().addListener((observable, oldValue, newValue) ->
      {
          double cellWidth = newValue.doubleValue() / gridPane.getColumnCount();
          imageView.setFitWidth(cellWidth);
      });


      gridPane.heightProperty().addListener((observable, oldValue, newValue) ->
      {
          double cellHeight = newValue.doubleValue() / gridPane.getRowCount();
          imageView.setFitHeight(cellHeight);
      });


      imageView.setPreserveRatio(true);

      gridPane.add(imageView, 2, 2);
}

public void plaatsStartTegels()
    {
          HashMap<SpelerDTO, Kleur> spelersMetKleuren = dc.getSpelendeSpelers();

          List<GridPane> beschikbareGridPanes = new ArrayList<>();
          beschikbareGridPanes.add(gridPane1);
          beschikbareGridPanes.add(gridPane2);
          beschikbareGridPanes.add(gridPane3);
          beschikbareGridPanes.add(gridPane4);

          int index = 0;

          for(Map.Entry<SpelerDTO, Kleur> entry : spelersMetKleuren.entrySet())
          {
              SpelerDTO speler = entry.getKey();
              Kleur kleur = entry.getValue();

              if(index < beschikbareGridPanes.size())
              {
                  GridPane gridPane = beschikbareGridPanes.get(index);
                  String startTegelImagePath = getStartTegelImagePath(kleur);
                  laadStarttegels(gridPane, startTegelImagePath);
              }

              index++;
          }
      }

        private String getStartTegelImagePath(Kleur kleur)
            {
                switch (kleur)
                {
                    case BLAUW:
                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_blauw.png";
                    case GEEL:
                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_geel.png";
                    case GROEN:
                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_groen.png";
                    case ROOS:
                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_roos.png";
                    default:
                        return null;
                }
            }

        private void plaatsTegelsInBeginKolom(List<DominoTegel> tegels, VBox kolom) {
            int index = 1;
            kolom.getChildren().clear();
            kolom.setSpacing(20);

            for (DominoTegel tegel : tegels)
            {
                ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
                imageView.setId("imageView" + index);

                imageView.setFitHeight(78);
                imageView.setFitWidth(156);

                imageView.setCursor(Cursor.HAND);

                imageView.setOnMousePressed(this::imageViewMousePressed);
                imageView.setOnMouseDragged(this::imageViewMouseDragged);

                kolom.getChildren().add(imageView);
                index++;

            }
        }

        private void plaatsTegelsInEindKolom(List<DominoTegel> tegels, VBox kolom)
        {

            kolom.getChildren().clear();
            kolom.setSpacing(20);
            int index;

            if(dc.getSpelendeSpelers().size() == 3)
            {
                index = 4;
            } else {index = 5;}


            for (DominoTegel tegel : tegels)
            {
                ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
                imageView.setId("imageView" + index);

                imageView.setFitHeight(78);
                imageView.setFitWidth(156);

                kolom.getChildren().add(imageView);
                index++;
            }
        }

    @FXML
    private void imageViewGeklik(MouseEvent event)
    {
        ImageView geklikteImage = (ImageView) event.getSource();
        String id = geklikteImage.getId();
        System.out.println(id);
    }



    private void plaatsTegelInStapel(List<DominoTegel> tegels, HBox kolom)
    {
        kolom.getChildren().clear();

        Random random = new Random();
        int randomIndex = random.nextInt(tegels.size());
        DominoTegel willekeurigeTegel = tegels.get(randomIndex);

        ImageView imageView = new ImageView(new Image(willekeurigeTegel.getFotoAchterkant()));

        imageView.setFitHeight(78);
        imageView.setFitWidth(156);

        kolom.getChildren().add(imageView);

    }

    private void updateVBoxVisibility() {
        if (rondeNummer == 1) {
            beginKolomKeuze.setVisible(true);
            eindKolomKeuze.setVisible(false);
        } else {
            beginKolomKeuze.setVisible(false);
            eindKolomKeuze.setVisible(true);
        }
    }
/*-------------------------------------------------Drag & Drop-----------------------------------------------*/
//    private boolean isDraggableImageView(ImageView imageView) {
//        return imageView.getId().startsWith("beginKolom");
//    }

    private void imageViewMousePressed(MouseEvent event)
    {

        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        orgTranslateX = ((ImageView)(event.getSource())).getTranslateX();
        orgTranslateY = ((ImageView)(event.getSource())).getTranslateY();
    }

    private void imageViewMouseDragged(MouseEvent event)
    {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        draggedImageView = (ImageView) event.getSource();
        draggedImageView.setTranslateX(newTranslateX);
        draggedImageView.setTranslateY(newTranslateY);
    }
/*-------------------------------------------------BACKEND---------------------------------------------------*/

    @FXML
    private void volgendeButtonHandler(ActionEvent event)
    {

        if(rondeNummer == 1) {
            dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 0);
        } else dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 1);


        System.out.println("speler toegevoegd!");
        geselecteerdeCirkel.setDisable(true);
        geselecteerdeCirkel = null;
        huidigeSpelerIndex++;
        updateVBoxVisibility();
        instructieTekst.setText("Speler met kleur " + getKleurSpeler() + ", kies een tegel voor je koninkrijk!");

    }
    @FXML
    private void circleClickHandler(MouseEvent event)
    {
        if (geselecteerdeCirkel != null)
        {
            geselecteerdeCirkel.setFill(Color.GREY);
        }

        geselecteerdeCirkel = (Circle) event.getSource();
        String circleId = ((Circle) event.getSource()).getId();
        gekozenCirkel = Integer.parseInt(circleId.substring(circleId.length() - 1));

        Kleur kleurHuidigeSpeler = getKleurSpeler();
        geselecteerdeKleurPaint = getKleurSpelerPaint(kleurHuidigeSpeler);
        geselecteerdeCirkel.setFill(geselecteerdeKleurPaint);
        System.out.println("Gekozen cirkel: " + gekozenCirkel);
    }

    private Paint getKleurSpelerPaint(Kleur kleur)
    {
        switch(kleur)
        {
            case BLAUW:
                return Color.web("#51aeff");
            case GEEL:
                return Color.web("#f2ff3d");
            case GROEN:
                return Color.web("#66ff61");
            case ROOS:
                return Color.web("#ff63ea");
            default:
                return Color.web("black");
        }
    }


    private Kleur getKleurSpeler()
    {
        kleurenSpelers = dc.getVolgordeKoning();
        return kleurenSpelers.get(huidigeSpelerIndex);
    }


    private void verschuifKolommen()
    {
        List<DominoTegel> eindKolomTegels = dc.getSpel().geefEindKolom();
        List<DominoTegel> beginKolomTegels = dc.getSpel().geefBeginKolom();

        beginKolomTegels.clear();
        beginKolomTegels.addAll(eindKolomTegels);

        List<DominoTegel> stapelTegels = dc.getBeschikbareTegels();
        eindKolomTegels.clear();
        eindKolomTegels.addAll(stapelTegels);
    }
    private List<DominoTegel> getBeginKolomTegels()
    {
        return dc.getSpel().geefBeginKolom();
    }

    private List<DominoTegel> getEindKolomTegels()
    {
        return dc.getSpel().geefEindKolom();
    }

     List<DominoTegel> getStapel()
     {
         return dc.getSpel().getTegelsDeck();
     }


     public void startSpel()
     {
         dc.koningRondeEenShuffle();
         speelBeurtEersteRonde();

         while (!dc.isEindeSpel())
         {
             speelRonde();
         }

         instructieTekst.setText("Het spel is afgelopen!");
         System.out.println("Het spel is afgelopen!");
     }

     public void speelBeurtEersteRonde()
     {
         dc.koningRondeEenShuffle();
         updateVBoxVisibility();

        instructieTekst.setText("Speler met kleur " + getKleurSpeler() + ", kies een tegel voor je koninkrijk!");

         int keuze = gekozenCirkel;
     }
    public void speelBeurt()
    {

    }

    public void speelRonde()
    {

        rondeNummer++;
    }
}
