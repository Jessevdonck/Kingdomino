package GUI;

import domein.DomeinController;
import domein.DominoTegel;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Kleur;

import java.net.URL;
import java.util.*;

public class SpelController implements Initializable
{
    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);

//    @FXML private GridPane gridPane1;
//    @FXML private GridPane gridPane2;
//    @FXML private GridPane gridPane3;
//    @FXML private GridPane gridPane4;
//    @FXML private VBox beginKolom;
//    @FXML private VBox eindKolom;
//    @FXML private HBox stapel;
//    @FXML private Label instructieMelding;
//    @FXML private Button bevestigBtn;

    private final String[] startTegelImagePath =
            {
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_blauw.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/geel.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_groen.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_roos.png"
            };


    public SpelController(DomeinController dc)
        {
            this.dc = dc;
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        plaatsStartTegels();
        plaatsTegelsInBeginKolom(getBeginKolomTegels(), beginKolom);
        plaatsTegelInEindKolom(getEindKolomTegels(), eindKolom);
        plaatsTegelInStapel(getStapel(), stapel);

    }
/*-------------------------------------------------FRONTEND---------------------------------------------------*/
    public void laadStarttegels(GridPane gridPane, String startTegelImagePath)
    {
//        ImageView imageView = new ImageView(new Image(startTegelImagePath));
//
//
//
//        gridPane.widthProperty().addListener((observable, oldValue, newValue) ->
//        {
//            double cellWidth = newValue.doubleValue() / gridPane.getColumnCount();
//            imageView.setFitWidth(cellWidth);
//        });
//
//
//        gridPane.heightProperty().addListener((observable, oldValue, newValue) ->
//        {
//            double cellHeight = newValue.doubleValue() / gridPane.getRowCount();
//            imageView.setFitHeight(cellHeight);
//        });
//
//
//        imageView.setPreserveRatio(true);
//
//        gridPane.add(imageView, 2, 2);
    }

    public void plaatsStartTegels()
        {
//            HashMap<SpelerDTO, Kleur> spelersMetKleuren = dc.getSpelendeSpelers();
//
//            List<GridPane> beschikbareGridPanes = new ArrayList<>();
//            beschikbareGridPanes.add(gridPane1);
//            beschikbareGridPanes.add(gridPane2);
//            beschikbareGridPanes.add(gridPane3);
//            beschikbareGridPanes.add(gridPane4);
//
//            int index = 0;
//
//            for(Map.Entry<SpelerDTO, Kleur> entry : spelersMetKleuren.entrySet())
//            {
//                SpelerDTO speler = entry.getKey();
//                Kleur kleur = entry.getValue();
//
//                if(index < beschikbareGridPanes.size())
//                {
//                    GridPane gridPane = beschikbareGridPanes.get(index);
//                    String startTegelImagePath = getStartTegelImagePath(kleur);
//                    laadStarttegels(gridPane, startTegelImagePath);
//                }
//
//                index++;
//            }
//        }
//
//        private String getStartTegelImagePath(Kleur kleur)
//            {
//                switch (kleur)
//                {
//                    case BLAUW:
//                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_blauw.png";
//                    case GEEL:
//                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_geel.png";
//                    case GROEN:
//                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_groen.png";
//                    case ROOS:
//                        return "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_roos.png";
//                    default:
//                        return null;
//                }
//            }
//
//        private void plaatsTegelsInBeginKolom(List<DominoTegel> tegels, VBox kolom)
//        {
////            kolom.getChildren().clear();
////            kolom.setSpacing(20);
////
////            for (int i = 0; i < dc.getSpelendeSpelers().size(); i++)
////            {
////                Random random = new Random();
////                int randomInteger = random.nextInt(tegels.size());
////                DominoTegel tegel = tegels.get(randomInteger);
////                ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
////
////                /*kolom.widthProperty().addListener((observable, oldValue, newValue) ->
////                {
////                    imageView.setFitWidth(newValue.doubleValue());
////                });
////
////                kolom.heightProperty().addListener((observable, oldValue, newValue) ->
////                {
////                    imageView.setFitHeight(newValue.doubleValue() / tegels.size());
////                });
////
////                imageView.setPreserveRatio(true);*/
////
////                imageView.setFitHeight(100);
////                imageView.setFitWidth(200);
////
////                kolom.getChildren().add(imageView);
////                System.out.println(tegel);
////            }
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

        private void plaatsTegelsInBeginKolom(List<DominoTegel> tegels, VBox kolom)
        {
            kolom.getChildren().clear();
            kolom.setSpacing(20);

            for (int i = 0; i < dc.getSpelendeSpelers().size(); i++)
            {
                Random random = new Random();
                int randomInteger = random.nextInt(tegels.size());
                DominoTegel tegel = tegels.get(randomInteger);
                ImageView imageView = new ImageView(new Image(tegel.getFotoAchterkant()));

                /*kolom.widthProperty().addListener((observable, oldValue, newValue) ->
                {
                    imageView.setFitWidth(newValue.doubleValue());
                });

                kolom.heightProperty().addListener((observable, oldValue, newValue) ->
                {
                    imageView.setFitHeight(newValue.doubleValue() / tegels.size());
                });

                imageView.setPreserveRatio(true);*/

                imageView.setFitHeight(100);
                imageView.setFitWidth(200);

                kolom.getChildren().add(imageView);
                System.out.println(tegel);
            }
        }

        private void plaatsTegelInEindKolom(List<DominoTegel> tegels, VBox kolom)
        {
//            kolom.getChildren().clear();
//            kolom.setSpacing(20);
//
//            for (int i = 0; i < dc.getSpelendeSpelers().size(); i++)
//            {
//                Random random = new Random();
//                int randomInteger = random.nextInt(tegels.size());
//                DominoTegel tegel = tegels.get(randomInteger);
//                ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
//
//                /*kolom.widthProperty().addListener((observable, oldValue, newValue) ->
//                {
//                    imageView.setFitWidth(newValue.doubleValue());
//                });
//
//                kolom.heightProperty().addListener((observable, oldValue, newValue) ->
//                {
//                    imageView.setFitHeight(newValue.doubleValue() / tegels.size());
//                });
//
//                imageView.setPreserveRatio(true);*/
//
//                imageView.setFitHeight(100);
//                imageView.setFitWidth(200);
//
//                kolom.getChildren().add(imageView);
//
//                System.out.println(tegel);
//            }
        }

    private void plaatsTegelInStapel(List<DominoTegel> tegels, HBox kolom)
    {
//        kolom.getChildren().clear();
//
//        Random random = new Random();
//        int randomIndex = random.nextInt(tegels.size());
//        DominoTegel willekeurigeTegel = tegels.get(randomIndex);
//
//        ImageView imageView = new ImageView(new Image(willekeurigeTegel.getFotoAchterkant()));
//
//        imageView.setFitHeight(100);
//        imageView.setFitWidth(200);
//
//        kolom.getChildren().add(imageView);

    }

/*-------------------------------------------------BACKEND---------------------------------------------------*/


    public void verschuifKolommen()
    {
//        List<DominoTegel> eindKolomTegels = dc.getSpel().geefEindKolom();
//        List<DominoTegel> beginKolomTegels = dc.getSpel().geefBeginKolom();
//
//        beginKolomTegels.clear();
//        beginKolomTegels.addAll(eindKolomTegels);
//
//        List<DominoTegel> stapelTegels = dc.getBeschikbareTegels();
//        eindKolomTegels.clear();
//        eindKolomTegels.addAll(stapelTegels);
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

    public void spelSituatie()
    {

    }

    public void speelBeurt()
    {

    }

    public void speelRonde()
    {
//        verschuifKolommen();
//        speelBeurt();
//        if (dc.isEindeSpel()) {
//            System.out.println("Het spel is afgelopen.");
//        }
    }
}
