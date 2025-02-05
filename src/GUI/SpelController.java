package GUI;

import domein.DomeinController;
import domein.DominoTegel;
import domein.Speler;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import exceptions.*;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
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
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.Kleur;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SpelController implements Initializable {
    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);

    private boolean tegelRotated = false;

    @FXML
    private Label spelerEen, spelerTwee, spelerDrie;
    @FXML
    private GridPane gridPane1;
    @FXML
    private GridPane gridPane2;
    @FXML
    private GridPane gridPane3;
    @FXML
    private GridPane gridPane4;
    @FXML
    private GridPane beginKolom;
    @FXML
    private GridPane eindKolom;
    @FXML
    private Label instructieMelding;
    @FXML
    private Button bevestigBtn;
    @FXML
    private Circle keuzeTegelBeginKolom1;
    @FXML
    private Circle keuzeTegelBeginKolom2;
    @FXML
    private Circle keuzeTegelBeginKolom3;
    @FXML
    private Circle keuzeTegelBeginKolom4;
    @FXML
    private Circle keuzeTegelEindKolom1;
    @FXML
    private Circle keuzeTegelEindKolom2;
    @FXML
    private Circle keuzeTegelEindKolom3;
    @FXML
    private Circle keuzeTegelEindKolom4;
    @FXML
    private Label instructieTekst;
    @FXML
    private Button stapelButton;
    @FXML
    private Button verwijderBtn;
    @FXML
    private Button bevestignBtn;
    @FXML
    private Button volgendeBtn;
    @FXML
    private VBox eindKolomKeuze;
    @FXML
    private VBox beginKolomKeuze;
    @FXML
    private Pane bord1, bord2, bord3, bord4;
    @FXML AnchorPane scorePopUp;


    @FXML
    private void switchToScoreScene(ActionEvent event) throws IOException{
        SceneSwitchController sc = new SceneSwitchController(dc);

        sc.switchToScoreScene(event, dc, TaalController.getInstance());


    }

    @FXML
    private AnchorPane tafel;

    @FXML
    private ImageView draggedImageView;
    CustomBord[] customBorden;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private ImageView keyTypedImageView;

    private ResourceBundle bundle;

    private int bordIndexGroen = -1;
    private int bordIndexBlauw = -1;
    private int bordIndexRoos = -1;
    private int bordIndexGeel = -1;

    private FXMLLoader loader;


    private int gekozenCirkel = -1;
    Circle geselecteerdeCirkel;

    private List<Kleur> kleurenSpelers;
    private int huidigeSpelerIndex = 0;

    Paint geselecteerdeKleurPaint;
    Pane[] borden;
    private double newX;
    private double newY;
    private double mouseX;
    private double mouseY;
    private double cellSize;
    private int spelersMetTegels = 0;


    // ------ Game State Booleans --------
    private boolean rondeEen = true;
    private boolean plaatsTegel = false;
    private boolean kiesNieuweTegel = false;
    private boolean laatsteRonde = false;


    private boolean isEindeRonde;
    private boolean isBeginRonde;
    private List<Kleur> gekozenVolgordeHuidig;
    private List<Kleur> gekozenVolgordeNieuw;
    private final String[] startTegelImagePath =
            {
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_blauw.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/geel.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_groen.png",
                    "/img/KingDomino_Afbeeldingen1/starttegel/starttegel_roos.png"
            };

    public SpelController(DomeinController dc, FXMLLoader loader) {
        this.loader = loader;
        this.dc = dc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maakBorden();
        laadLanguage();

        plaatsStartTegels();

        plaatsTegelsInKolom(getEindKolomTegels(), eindKolom);
        plaatsTegelInStapel(getStapel(), stapelButton);
        plaatsTegelsInBeginKolom(getBeginKolomTegels(), beginKolom);
        for (Node node : eindKolom.getChildren()) {
            // Disable eindkolom
            node.setDisable(true);
        }
        updateKaartenBeweegbaarHeid();
        speelBeurtEersteRonde();
        //startSpel();

    }

    /*-------------------------------------------------FRONTEND---------------------------------------------------*/
    public void laadStarttegels(Pane pane, String startTegelImagePath) {
        ImageView imageView = new ImageView(new Image(startTegelImagePath));


        imageView.setFitWidth(78);
        imageView.setFitHeight(78);
        imageView.setLayoutX(156); //r
        imageView.setLayoutY(156);


        pane.getChildren().add(imageView);
    }

    public void plaatsStartTegels() {
        HashMap<Speler, Kleur> spelersMetKleuren = dc.getSpelendeSpelers();

        List<Pane> beschikbareBorden = new ArrayList<>();
        beschikbareBorden.add(bord1);
        beschikbareBorden.add(bord2);
        beschikbareBorden.add(bord3);
        beschikbareBorden.add(bord4);

        System.out.println(dc.getSpelendeSpelers());

        int index = 0;

        for (Map.Entry<Speler, Kleur> entry : spelersMetKleuren.entrySet()) {
            Speler speler = entry.getKey();
            Kleur kleur = entry.getValue();

            if (index < beschikbareBorden.size()) {
                Pane pane = beschikbareBorden.get(index);
                String startTegelImagePath = getStartTegelImagePath(kleur);
                switch (kleur) {
                    case GROEN:
                        bordIndexGroen = index;
                        break;
                    case ROOS:
                        bordIndexRoos = index;
                        break;
                    case GEEL:
                        bordIndexGeel = index;
                        break;
                    case BLAUW:
                        bordIndexBlauw = index;
                        break;
                }
                laadStarttegels(pane, startTegelImagePath);
            }

            index++;

        }
    }

    private String getStartTegelImagePath(Kleur kleur) {
        switch (kleur) {
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

    private void plaatsTegelsInBeginKolom(List<DominoTegel> tegels, GridPane kolom) {
        int index = 1;
        int indexGridPane = 0;
        kolom.getChildren().clear();

        for (DominoTegel tegel : tegels) {
            ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
            imageView.setId("imageView" + index);

            imageView.setFitHeight(78);
            imageView.setFitWidth(156);

            imageView.setCursor(Cursor.HAND);

            imageView.setFocusTraversable(true);

            imageView.setOnKeyPressed(this::imageViewKeyPressed);
            imageView.setOnMousePressed(this::imageViewMousePressed);
            imageView.setOnMouseDragged(this::imageViewMouseDragged);
            imageView.setOnMouseReleased(this::imageViewMouseReleased);

            kolom.add(imageView, 0, indexGridPane);

            index++;
            indexGridPane++;

        }
    }


    @FXML
    public void updateNaarVolgendeRonde() {
        if(dc.getSpel().getTegelsDeck().isEmpty())
        {
            laatsteRonde = true;
        }
        dc.updateKaarten();
        beginKolom.getChildren().clear();
        eindKolom.getChildren().clear();
        plaatsTegelsInKolom(dc.getBeginKolom(), beginKolom);
        plaatsTegelsInKolom(dc.getEindKolom(), eindKolom);
        plaatsTegelInStapel(dc.getBeschikbareTegels(), stapelButton);
    }

    private void plaatsTegelsInKolom(List<DominoTegel> tegels, GridPane kolom) {
        int index = dc.getSpelendeSpelers().size() + 1;
        int indexGridPane = 0;

        kolom.getChildren().clear();


        for (DominoTegel tegel : tegels) {
            ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
            imageView.setId(kolom.getId() + index);

            imageView.setFitHeight(78);
            imageView.setFitWidth(156);

            imageView.setCursor(Cursor.HAND);

            imageView.setFocusTraversable(true);

            imageView.setOnKeyPressed(this::imageViewKeyPressed);
            imageView.setOnMousePressed(this::imageViewMousePressed);
            imageView.setOnMouseDragged(this::imageViewMouseDragged);
            imageView.setOnMouseReleased(this::imageViewMouseReleased);

            kolom.add(imageView, 0, indexGridPane);
            index++;
            indexGridPane++;

        }
    }

    @FXML
    private void imageViewGeklik(MouseEvent event) {
        ImageView geklikteImage = (ImageView) event.getSource();
        String id = geklikteImage.getId();
        System.out.println(id);
    }

    private void plaatsTegelInStapel(List<DominoTegel> tegels, Button stapelButton) {
        if (!tegels.isEmpty()) {
            stapelButton.setGraphic(null);

            Random random = new Random();
            int randomIndex = random.nextInt(tegels.size());
            DominoTegel willekeurigeTegel = tegels.get(randomIndex);

            // Maak een nieuwe ImageView aan voor de willekeurige tegel
            ImageView imageView = new ImageView(new Image(willekeurigeTegel.getFotoAchterkant()));

            // Stel de afmetingen van de ImageView in
            imageView.setFitHeight(78);
            imageView.setFitWidth(156);

            // Voeg de ImageView toe aan de stapelButton
            stapelButton.setGraphic(imageView);
        }
    }


    /*-------------------------------------------------Drag & Drop / Turning Tiles-------------------------------*/

    private void imageViewMousePressed(MouseEvent event) {

        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        orgTranslateX = ((ImageView) (event.getSource())).getTranslateX();
        orgTranslateY = ((ImageView) (event.getSource())).getTranslateY();
    }

    private void imageViewMouseDragged(MouseEvent event) {

        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        draggedImageView = (ImageView) event.getSource();
        draggedImageView.requestFocus();
        draggedImageView.setTranslateX(newTranslateX);
        draggedImageView.setTranslateY(newTranslateY);

    }

    private void imageViewMouseReleased(MouseEvent event) {
        cellSize = customBorden[0].getSizeSquare();
        Kleur kleurHuidigeSpeler = getKleurSpeler();
        int bordIndex = 0;

        switch (kleurHuidigeSpeler) {
            case GROEN:
                bordIndex = bordIndexGroen;
                break;
            case BLAUW:
                bordIndex = bordIndexBlauw;
                break;
            case ROOS:
                bordIndex = bordIndexRoos;
                break;
            case GEEL:
                bordIndex = bordIndexGeel;
                break;
        }

        // Haal de coördinaten op van de muispositie ten opzichte van het bord
        mouseX = event.getSceneX() - borden[bordIndex].getBoundsInParent().getMinX();
        mouseY = event.getSceneY() - borden[bordIndex].getBoundsInParent().getMinY();

        // Pas deze coördinaten aan om ervoor te zorgen dat ze binnen het bordgebied blijven
        mouseX = Math.max(0, Math.min(mouseX, borden[bordIndex].getWidth()) - 1);
        mouseY = Math.max(0, Math.min(mouseY, borden[bordIndex].getHeight()) - 1);

        // Bereken de gesnapte x- en y-coördinaten binnen het bord
        newX = !tegelRotated ? Math.floor(mouseX / cellSize) * cellSize : Math.floor(mouseX / cellSize) * cellSize - (cellSize / 2);
        newY = !tegelRotated ? Math.floor(mouseY / cellSize) * cellSize : Math.floor(mouseY / cellSize) * cellSize + (cellSize / 2);

        if (!tegelRotated && newX >= borden[bordIndex].getWidth() - cellSize) {
            // Negeer de actie als de tegelRotated false is en de tegel in de laatste kolom wordt geplaatst
            newX = Math.floor((borden[bordIndex].getWidth() - cellSize * 2) / cellSize) * cellSize;
            mouseX = borden[bordIndex].getWidth() - cellSize - 1;
        }

        if (tegelRotated && newY >= borden[bordIndex].getHeight() - cellSize) {
            // Negeer de actie als de tegel geroteerd is en wordt geplaatst in de bovenste rij
            newY = Math.floor((borden[bordIndex].getHeight() - cellSize * 2) / cellSize) * cellSize + (cellSize / 2);
            mouseY = newY - cellSize / 2;
        }

        if (newX == 156 && newY == 156) {
            return;
        }


        // Werk de positie van de ImageView bij naar de gesnapte coördinaten
        draggedImageView.setLayoutX(newX);
        draggedImageView.setLayoutY(newY);


        if (!borden[bordIndex].getChildren().contains(draggedImageView)) {
            borden[bordIndex].getChildren().add(draggedImageView);
        }

        // Reset de translate
        draggedImageView.setTranslateX(0);
        draggedImageView.setTranslateY(0);
        draggedImageView.getParent().requestFocus();
    }

    /* Hier gaat er gekeken worden of onze tegel al gedraaid is geweest, dan zet hij hem terug op zijn
       oorsponkelijke horizontale status, anders wordt hij 90 graden gedraaid naar rechts om hem verticaal te zetten
     */
    private void imageViewKeyPressed(KeyEvent event) {


        keyTypedImageView = (ImageView) event.getSource();
        if (event.getCode() == KeyCode.R) {
            double rotation = keyTypedImageView.getRotate();
            if (rotation == 0.0) {
                keyTypedImageView.setRotate(90);
                tegelRotated = true;
            } else {
                keyTypedImageView.setRotate(0);
                tegelRotated = false;
            }
        }


    }



    /*-------------------------------------------------VOLGENDE RONDE--------------------------------------------*/

    @FXML
    private void stapelButtonHandler(ActionEvent event) {

        if (!beginKolom.getChildren().isEmpty() || gekozenVolgordeNieuw.contains(null)) {
            String prevText = instructieTekst.getText();
            String prevStyle = instructieTekst.getStyle();
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(pauseEvent -> {
                instructieTekst.setText(prevText);
                instructieTekst.setStyle(prevStyle);
            });
            instructieTekst.setText(bundle.getString("RondeNogNietGedaan"));
            instructieTekst.setStyle("-fx-text-fill: red;");
            pause.play();
        } else {
            String prevText = instructieTekst.getText();
            String prevStyle = instructieTekst.getStyle();
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(pauseEvent -> {
                instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("PlaatsTegelInstructie"));
                instructieTekst.setStyle(prevStyle);
            });
            instructieTekst.setText(bundle.getString("VolgendeRondeGestart"));
            instructieTekst.setStyle("-fx-text-fill: white;");
            pause.play();
            // DOE ALLES OM RONDE TE UPDATEN
            dc.setVolgordeKoning(gekozenVolgordeNieuw);
            updateNaarVolgendeRonde();
            updateCirkels();
            updateKaartenBeweegbaarHeid();


            beginKolom.getChildren().get(0).setDisable(false);
            for (Node node : eindKolom.getChildren()) {
                // Disable eindkolom
                node.setDisable(true);
            }
            plaatsTegel = true;
        }

    }


    /*-------------------------------------------------LAAD LANGUAGE---------------------------------------------*/
    public void laadLanguage() //Wordt gebruikt wanneer er al taal gekozen is
    {
        String gekozenTaal = TaalController.getInstance().getLanguage();

        Locale locale = new Locale(gekozenTaal);
        bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);

        // Hier wordt de tekst van de buttons veranderd zodat er geen korte aparte methode voor moet zijn
        verwijderBtn.setText(bundle.getString("Verwijder"));
        volgendeBtn.setText(bundle.getString("Volgende"));
    }

    /*-------------------------------------------------BACKEND---------------------------------------------------*/

    @FXML
    public void maakBorden() {
        customBorden = new CustomBord[4];

        if (dc.getSpelendeSpelers().size() == 3) {
            customBorden[0] = new CustomBord();
            customBorden[1] = new CustomBord();
            customBorden[2] = new CustomBord();

            customBorden[0].maakBord(bord1);
            customBorden[1].maakBord(bord2);
            customBorden[2].maakBord(bord3);

            this.borden = new Pane[]{bord1, bord2, bord3};
        } else {
            customBorden[0] = new CustomBord();
            customBorden[1] = new CustomBord();
            customBorden[2] = new CustomBord();
            customBorden[3] = new CustomBord();

            customBorden[0].maakBord(bord1);
            customBorden[1].maakBord(bord2);
            customBorden[2].maakBord(bord3);
            customBorden[3].maakBord(bord4);
            this.borden = new Pane[]{bord1, bord2, bord3, bord4};
        }
    }

    @FXML
    private void verwijderButtonHandler(ActionEvent event) {

        DominoTegel tegel = dc.getGeclaimdeTegel(getKleurSpeler(), true);
        if (!dc.kanTegelPlaatsen(huidigeSpelerIndex, tegel)) {
            String prevText = instructieTekst.getText();
            String prevStyle = instructieTekst.getStyle();
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(pauseEvent -> {
                instructieTekst.setText(prevText);
                instructieTekst.setStyle(prevStyle);
            });
            instructieTekst.setText(bundle.getString("TegelVerwijderdUitSpel"));
            instructieTekst.setStyle("-fx-text-fill: green;");
            pause.play();

            // Delete imageview

            Kleur kleurHuidigeSpeler = getKleurSpeler();
            int bordIndex = 0;

            switch (kleurHuidigeSpeler) {
                case GROEN:
                    bordIndex = bordIndexGroen;
                    break;
                case BLAUW:
                    bordIndex = bordIndexBlauw;
                    break;
                case ROOS:
                    bordIndex = bordIndexRoos;
                    break;
                case GEEL:
                    bordIndex = bordIndexGeel;
                    break;
            }

            System.out.println(borden[bordIndex].getChildren().get(0).toString());
            if (borden[bordIndex].getChildren().contains(draggedImageView)) {
                borden[bordIndex].getChildren().remove(draggedImageView);
            } else {
                beginKolom.getChildren().remove(0);
            }

            dc.getBeginKolom().remove(tegel);
            plaatsTegel = false;
            kiesNieuweTegel = true;


        } else {
            String prevText = instructieTekst.getText();
            String prevStyle = instructieTekst.getStyle();
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(pauseEvent -> {
                instructieTekst.setText(prevText);
                instructieTekst.setStyle(prevStyle);
            });
            instructieTekst.setText(bundle.getString("TegelKanGeplaatstWorden"));
            instructieTekst.setStyle("-fx-text-fill: white;");
            pause.play();
        }


    }

    private void updateKaartenBeweegbaarHeid() {

        int index = 0;

        for (Node kaart : beginKolom.getChildren()) {


            kaart.setDisable(index != 0);

            index++;
        }
    }

    private void updateCirkels() {

        int index = 0;
        for (Node cirkel : beginKolomKeuze.getChildren()) {

            if (cirkel instanceof Circle) {
                Kleur kleur = gekozenVolgordeNieuw.get(index);
                Circle circle = (Circle) cirkel;
                switch (kleur) {
                    case GEEL -> circle.setFill(Color.web("#f2ff3d"));
                    case ROOS -> circle.setFill(Color.web("#ff63ea"));
                    case BLAUW -> circle.setFill(Color.web("#51aeff"));
                    case GROEN -> circle.setFill(Color.web("#66ff61"));
                    default -> circle.setFill(Color.GREY);
                }

                circle.setDisable(true);

            }
            index++;
        }

        for (Node cirkelRechts : eindKolomKeuze.getChildren()) {
            if (cirkelRechts instanceof Circle) {
                Circle circle = (Circle) cirkelRechts;
                circle.setFill(Color.GREY);
                circle.setDisable(false);
            }
        }
        gekozenVolgordeHuidig = gekozenVolgordeNieuw;
        gekozenVolgordeNieuw = new ArrayList<>(Arrays.asList(new Kleur[dc.getSpelendeSpelers().size()]));

    }

    @FXML
    private void volgendeButtonHandler(ActionEvent event) {

        if (rondeEen) {
            try{
                if(gekozenCirkel == -1){
                    throw new IllegalArgumentException("Mag niet");
                }
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                String prevText = instructieTekst.getText();
                String prevStyle = instructieTekst.getStyle();
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(pauseEvent -> {
                    instructieTekst.setText(prevText);
                    instructieTekst.setStyle(prevStyle);
                });
                instructieTekst.setText(bundle.getString("KiesEerstEenTegel"));
                instructieTekst.setStyle("-fx-text-fill: red;");
                pause.play();
            }
            System.out.println("peop");
            voegSpelerToeAanGekozenVolgorde(getKleurSpeler(), gekozenCirkel);
            dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 0);
            try {
                geselecteerdeCirkel.setDisable(true);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }


            spelersMetTegels++;
            geselecteerdeCirkel = null;
            huidigeSpelerIndex = (huidigeSpelerIndex + 1) % dc.getSpelendeSpelers().size();
            instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("KiesEenTegel"));

            if (spelersMetTegels == dc.getSpelendeSpelers().size()) {
                dc.setVolgordeKoning(gekozenVolgordeHuidig);
                rondeEen = false;
                plaatsTegel = true;

                instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("PlaatsTegelInstructie"));
                System.out.println(huidigeSpelerIndex);
                System.out.println(gekozenCirkel);
                gekozenCirkel = -1;
                return;
            }

        }


        if (plaatsTegel) {
            int kolom = (int) (Math.floor(mouseX / cellSize));
            int rij = (int) (Math.floor(mouseY / cellSize));
            boolean verticaal = tegelRotated;
            DominoTegel tegel = dc.getGeclaimdeTegel(getKleurSpeler(), true);

            Kleur kleurHuidigeSpeler = getKleurSpeler();
            int bordIndex = 0;

            switch (kleurHuidigeSpeler) {
                case GROEN:
                    bordIndex = bordIndexGroen;
                    break;
                case BLAUW:
                    bordIndex = bordIndexBlauw;
                    break;
                case ROOS:
                    bordIndex = bordIndexRoos;
                    break;
                case GEEL:
                    bordIndex = bordIndexGeel;
                    break;
            }

            if(!borden[bordIndex].getChildren().contains(draggedImageView)){
                String prevText = instructieTekst.getText();
                String prevStyle = instructieTekst.getStyle();
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(pauseEvent -> {
                    instructieTekst.setText(prevText);
                    instructieTekst.setStyle(prevStyle);
                });
                instructieTekst.setText(bundle.getString("PlaatsEerstJeTegelInJeDomein"));
                instructieTekst.setStyle("-fx-text-fill: red;");
                pause.play();
            }else{
                try {
                    dc.verplaatsDominotegel(kolom, rij, verticaal, tegel, huidigeSpelerIndex);


                    draggedImageView.setOnMouseDragged(null);
                    draggedImageView.setOnMousePressed(null);
                    draggedImageView.setOnMouseReleased(null);
                    draggedImageView.setOnKeyPressed(null);
                    tegelRotated = false;
                    plaatsTegel = false;
                    kiesNieuweTegel = true;
                    if(laatsteRonde) {
                        for(Node node: eindKolomKeuze.getChildren()){
                            node.setDisable(true);
                        }
                        kiesNieuweTegel = false;
                        huidigeSpelerIndex++;
                        if (huidigeSpelerIndex >= dc.getSpelendeSpelers().size()) {
                            // Als de huidige spelerindex de maximale index overschrijdt, toon de scorePopUp
                            //spelerEen.setText(dc.geefWinnaars().get(0).toString());
                            //spelerTwee.setText(dc.geefWinnaars().get(1).toString());
                            //spelerDrie.setText(dc.geefWinnaars().get(2).toString());
                            scorePopUp.setVisible(true);

                        } else {
                            // Anders, update de instructietekst en andere logica voor de volgende speler
                            instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("PlaatsTegelInstructie"));
                            updateKaartenBeweegbaarHeid();
                            plaatsTegel = true;
                        }
                    }
                    String prevText = instructieTekst.getText();
                    String prevStyle = instructieTekst.getStyle();
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                    pause.setOnFinished(pauseEvent -> {
                        instructieTekst.setText(prevText);
                        instructieTekst.setStyle(prevStyle);
                    });
                    instructieTekst.setText(bundle.getString("TegelSuccesVolToegevoegd"));
                    instructieTekst.setStyle("-fx-text-fill: #00e000;");
                    pause.play();

                    return;
                } catch (PlaatsenMiddenVanHetBordException | OverlappingHorizontaalException |
                         OverlappingVerticaalException |
                         TegelNietOvereenMetAanliggendeTegelException | IllegalArgumentException e) {
                    String prevText = instructieTekst.getText();
                    String prevStyle = instructieTekst.getStyle();
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                    pause.setOnFinished(pauseEvent -> {
                        instructieTekst.setText(prevText);
                        instructieTekst.setStyle(prevStyle);
                    });
                    instructieTekst.setText(e.getMessage());
                    instructieTekst.setStyle("-fx-text-fill: red;");
                    pause.play();
                }
            }
            // Try-Catch om te checken of de tegel correct geplaatst kan worden in het tegelgebied.

        }

        if (kiesNieuweTegel) {

            instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("PlaatsTegelInstructie"));

            if (dc.getGeclaimdeTegel(getKleurSpeler(), rondeEen) == null) {
                try{
                    if(gekozenCirkel == -1){
                        throw new IllegalArgumentException("Mag niet");
                    }
                }catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    String prevText = instructieTekst.getText();
                    String prevStyle = instructieTekst.getStyle();
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                    pause.setOnFinished(pauseEvent -> {
                        instructieTekst.setText(prevText);
                        instructieTekst.setStyle(prevStyle);
                    });
                    instructieTekst.setText(bundle.getString("KiesEerstEenTegel"));
                    instructieTekst.setStyle("-fx-text-fill: red;");
                    pause.play();
                }
                voegSpelerToeAanGekozenVolgorde(getKleurSpeler(), gekozenCirkel);
                dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 1);


                try {
                    geselecteerdeCirkel.setDisable(true);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
                geselecteerdeCirkel = null;


            } else {
                try {
                    geselecteerdeCirkel.setDisable(true);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
                geselecteerdeCirkel = null;
            }
            System.out.println(gekozenVolgordeNieuw.toString());
            kiesNieuweTegel = false;
            plaatsTegel = true;
            huidigeSpelerIndex = (huidigeSpelerIndex + 1) % dc.getSpelendeSpelers().size();
            instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("PlaatsTegelInstructie"));
            updateKaartenBeweegbaarHeid();
            if (!gekozenVolgordeNieuw.contains(null)) {
                instructieTekst.setText(bundle.getString("KlikOpStapelInstructie"));
            }
            gekozenCirkel = -1;
            return;
        }


//        if (dc.getGeclaimdeTegel(getKleurSpeler()) == null) {
//            dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 1);
//
//            System.out.println(gekozenVolgorde);
//            instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("KiesEenTegel"));
//
//        } else {
//            try {
//                geselecteerdeCirkel.setDisable(true);
//            } catch (NullPointerException e) {
//                System.out.println(e.getMessage());
//            }
//            geselecteerdeCirkel = null;
//            instructieTekst.setText("Speler met kleur " + getKleurSpeler() + ", plaats je tegel en klik op bevestig. Kies vervolgens een nieuwe tegel!");
//        }


    }


    @FXML
    private void circleClickHandler(MouseEvent event) {
        if (geselecteerdeCirkel != null) {
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

    private Paint getKleurSpelerPaint(Kleur kleur) {
        switch (kleur) {
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


    private Kleur getKleurSpeler() {
        return dc.getVolgordeKoning().get(huidigeSpelerIndex);
    }


    private List<DominoTegel> getBeginKolomTegels() {
        return dc.getSpel().geefBeginKolom();
    }

    private List<DominoTegel> getEindKolomTegels() {
        return dc.getSpel().geefEindKolom();
    }

    List<DominoTegel> getStapel() {
        return dc.getSpel().getTegelsDeck();
    }

    private void voegSpelerToeAanGekozenVolgorde(Kleur kleur, int index) {
        // Controleer of de lijst al spelers bevat, zo niet, maak een nieuwe lijst aan
        if (gekozenVolgordeHuidig == null) {
            gekozenVolgordeHuidig = new ArrayList<>(Arrays.asList(new Kleur[dc.getSpelendeSpelers().size()]));
        }
        if (gekozenVolgordeNieuw == null) {
            gekozenVolgordeNieuw = new ArrayList<>(Arrays.asList(new Kleur[dc.getSpelendeSpelers().size()]));
        }
        if (rondeEen) {
            gekozenVolgordeHuidig.set(gekozenCirkel - 1, kleur);
            return;
        }
        if (!rondeEen) {
            gekozenVolgordeNieuw.set(gekozenCirkel - 1, kleur);
        }


    }


    public void speelBeurtEersteRonde() {
        dc.koningRondeEenShuffle();


        instructieTekst.setText(bundle.getString("SpelerMetKleur") + getKleurSpeler() + bundle.getString("KiesEenTegel"));
    }


}
