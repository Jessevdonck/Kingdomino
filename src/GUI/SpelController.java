package GUI;

import domein.DomeinController;
import domein.DominoTegel;
import domein.Speler;
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
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import util.Kleur;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SpelController implements Initializable
{
    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);

    private boolean tegelRotated = false;

    @FXML private GridPane gridPane1;
    @FXML private GridPane gridPane2;
    @FXML private GridPane gridPane3;
    @FXML private GridPane gridPane4;
    @FXML private GridPane beginKolom;
    @FXML private GridPane eindKolom;
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
    @FXML private Pane bord1, bord2, bord3, bord4;



    @FXML private AnchorPane tafel;

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


    private int gekozenCirkel = 0;
    Circle geselecteerdeCirkel;

    private List<Kleur> kleurenSpelers;
    private int huidigeSpelerIndex = 0;
    private int rondeNummer = 1;
    Paint geselecteerdeKleurPaint;
    Pane[] borden;
    private double newX;
    private double newY;
    private int spelersMetTegels = 0;
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
        maakBorden();
        laadLanguage();

        plaatsStartTegels();

        plaatsTegelsInEindKolom(getEindKolomTegels(), eindKolom);
        plaatsTegelInStapel(getStapel(), stapel);
        plaatsTegelsInBeginKolom(getBeginKolomTegels(), beginKolom);

        speelBeurtEersteRonde();
        //startSpel();

    }
    /*-------------------------------------------------FRONTEND---------------------------------------------------*/
    public void laadStarttegels(Pane pane, String startTegelImagePath)
    {
        ImageView imageView = new ImageView(new Image(startTegelImagePath));


        imageView.setFitWidth(78);
        imageView.setFitHeight(78);
        imageView.setLayoutX(156); //r
        imageView.setLayoutY(156);


        pane.getChildren().add(imageView);
    }

    public void plaatsStartTegels()
    {
        HashMap<Speler, Kleur> spelersMetKleuren = dc.getSpelendeSpelers();

        List<Pane> beschikbareBorden = new ArrayList<>();
        beschikbareBorden.add(bord1);
        beschikbareBorden.add(bord2);
        beschikbareBorden.add(bord3);
        beschikbareBorden.add(bord4);

        System.out.println(dc.getSpelendeSpelers());

        int index = 0;

        for(Map.Entry<Speler, Kleur> entry : spelersMetKleuren.entrySet())
        {
            Speler speler = entry.getKey();
            Kleur kleur = entry.getValue();

            if(index < beschikbareBorden.size())
            {
                Pane pane = beschikbareBorden.get(index);
                String startTegelImagePath = getStartTegelImagePath(kleur);
                switch (kleur){
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

    private void plaatsTegelsInBeginKolom(List<DominoTegel> tegels, GridPane kolom) {
        int index = 1;
        int indexGridPane = 0;
        kolom.getChildren().clear();

        for (DominoTegel tegel : tegels)
        {
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

    private void plaatsTegelsInEindKolom(List<DominoTegel> tegels, GridPane kolom)
    {
        int index = dc.getSpelendeSpelers().size() + 1;
        int indexGridPane = 0;

        kolom.getChildren().clear();


        for (DominoTegel tegel : tegels)
        {
            ImageView imageView = new ImageView(new Image(tegel.getFotoVoorkant()));
            imageView.setId("imageView" + index);

            imageView.setFitHeight(78);
            imageView.setFitWidth(156);

            kolom.add(imageView, 0, indexGridPane);
            index++;
            indexGridPane++;
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


    /*-------------------------------------------------Drag & Drop / Turning Tiles-------------------------------*/
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
        draggedImageView.requestFocus();
        draggedImageView.setTranslateX(newTranslateX);
        draggedImageView.setTranslateY(newTranslateY);

    }

    private void imageViewMouseReleased(MouseEvent event)
    {
        double cellSize = customBorden[0].getSizeSquare();
        Kleur kleurHuidigeSpeler = getKleurSpeler();
        int bordIndex = 0;

        switch(kleurHuidigeSpeler) {
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
        double mouseX = event.getSceneX() - borden[bordIndex].getBoundsInParent().getMinX();
        double mouseY = event.getSceneY() - borden[bordIndex].getBoundsInParent().getMinY();

        // Pas deze coördinaten aan om ervoor te zorgen dat ze binnen het bordgebied blijven
        mouseX = Math.max(0, Math.min(mouseX, borden[bordIndex].getWidth()) - 1);
        mouseY = Math.max(0, Math.min(mouseY, borden[bordIndex].getHeight()) - 1);

        // Bereken de gesnapte x- en y-coördinaten binnen het bord
         newX = !tegelRotated ? Math.floor(mouseX / cellSize) * cellSize : Math.floor(mouseX / cellSize) * cellSize - (cellSize / 2);
         newY = !tegelRotated ? Math.floor(mouseY / cellSize) * cellSize : Math.floor(mouseY / cellSize) * cellSize - (cellSize / 2);

        if (!tegelRotated && newX >= borden[bordIndex].getWidth() - cellSize) {
            // Negeer de actie als de tegelRotated false is en de tegel in de laatste kolom wordt geplaatst
            return;
        }

        if (tegelRotated && newY < cellSize / 2) {
            // Negeer de actie als de tegel geroteerd is en wordt geplaatst in de bovenste rij
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
    private void imageViewKeyPressed(KeyEvent event)
    {

        System.out.println("Key pressed: " + event.getCode());
        keyTypedImageView = (ImageView) event.getSource();
        if(event.getCode() == KeyCode.R){
            double rotation = keyTypedImageView.getRotate();
            if(rotation == 0.0) {
                keyTypedImageView.setRotate(90);
                tegelRotated = true;
            }else{
                keyTypedImageView.setRotate(0);
                tegelRotated = false;
            }
        }

        System.out.println("R Pressed");
    }

    /*-------------------------------------------------LAAD LANGUAGE---------------------------------------------*/
    public void laadLanguage() //Wordt gebruikt wanneer er al taal gekozen is
    {
        String gekozenTaal = TaalController.getInstance().getLanguage();

        Locale locale = new Locale(gekozenTaal);
        bundle = ResourceBundle.getBundle("resourcebundles.lang", locale);

    }

    /*-------------------------------------------------BACKEND---------------------------------------------------*/

    @FXML
    public void maakBorden()
    {
        customBorden = new CustomBord[4];

        if(dc.getSpelendeSpelers().size() == 3)
        {
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
            this.borden = new Pane[]{bord1, bord2, bord3,bord4};
        }
    }

    @FXML
    private void bevestigButtonHandler(ActionEvent event)
    {
        int kolom = (int) (newX / 78);
        int rij = (int) (newY / 78);
        boolean verticaal = tegelRotated;
        DominoTegel tegel = dc.getGeclaimdeTegel(getKleurSpeler());


        System.out.println("kolom" + kolom + "\n rij " + rij + "Tegel" + tegel);

        // Try-Catch om te checken of de tegel correct geplaatst kan worden in het tegelgebied.
        try {
            dc.verplaatsDominotegel(kolom, rij, verticaal, tegel, huidigeSpelerIndex);
            System.out.println("Tegel toegevoegd met kolom: " + kolom  +"\nrij: " + rij + "\nverticaal: " + tegelRotated + "\ntegel: " + tegel);

            draggedImageView.setOnMouseDragged(null);
            draggedImageView.setOnMousePressed(null);
            draggedImageView.setOnMouseReleased(null);
            draggedImageView.setOnKeyPressed(null);
            tegelRotated = false;

            instructieTekst.setText(bundle.getString("TegelSuccesVolToegevoegd"));
            instructieTekst.setStyle("-fx-text-fill: #00e000;");
        } catch(IllegalArgumentException e) {
            instructieTekst.setText(e.getMessage());
            instructieTekst.setStyle("-fx-text-fill: red;");
        }

    }

    @FXML
    private void verwijderButtonHandler(ActionEvent event){

        int kolom = (int) (newX / 78);
        int rij = (int) (newY / 78);
        boolean verticaal = tegelRotated;
        DominoTegel tegel = dc.getGeclaimdeTegel(getKleurSpeler());

        // Try-Catch om te checken of de tegel weldegelijk geplaatst kan worden, of toch niet.
        try {

            //methodeDieChecktOfTegelWelOfNietGeplaatstKanWorden()
            System.out.println("Tegel verwijderd uit spel: " + tegel);

            draggedImageView.setOnMouseDragged(null);
            draggedImageView.setOnMousePressed(null);
            draggedImageView.setOnMouseReleased(null);
            draggedImageView.setOnKeyPressed(null);
            tegelRotated = false;

            instructieTekst.setText(bundle.getString("TegelVerwijderdUitSpel"));
            instructieTekst.setStyle("-fx-text-fill: #00e000;");
        } catch(IllegalArgumentException e) {
            instructieTekst.setText(e.getMessage());
            instructieTekst.setStyle("-fx-text-fill: red;");
        }

    }

    @FXML
    private void volgendeButtonHandler(ActionEvent event)
    {

        if(rondeNummer == 1) {
            dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 0);

            System.out.println(dc.getGeclaimdeTegel(getKleurSpeler()));
            spelersMetTegels++;

            if(spelersMetTegels == dc.getSpelendeSpelers().size())
            {
                rondeNummer++;
            }
            System.out.println(rondeNummer);
        } else
        {
            dc.voegKoningAanKaart(getKleurSpeler(), gekozenCirkel, 1);
        }


        System.out.println("speler toegevoegd!");
        geselecteerdeCirkel.setDisable(true);
        geselecteerdeCirkel = null;
        huidigeSpelerIndex = (huidigeSpelerIndex + 1) % 3;


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
