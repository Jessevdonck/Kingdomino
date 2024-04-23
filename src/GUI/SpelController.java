package GUI;

import domein.DomeinController;
import domein.DominoTegel;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import util.Kleur;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SpelController implements Initializable
{
    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);

    @FXML private GridPane gridPane1;
    @FXML private GridPane gridPane2;
    @FXML private GridPane gridPane3;
    @FXML private GridPane gridPane4;

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

    public SpelController()
        {
            this.dc = new DomeinController();
        }

    public void speelBeurt()
    {
        toonTegelLijst(dc.getSpel().geefTweedeKolom());
        System.out.println("Welke tegel wil je nemen?");
        int tegel = input.nextInt();

        dc.getVolgordeKoning().remove(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        laadStarttegels(gridPane1,"/img/KingDomino_Afbeeldingen1/starttegel/starttegel_blauw.png");
       /* laadStarttegels(gridPane2,startTegelImagePath[1]);
        laadStarttegels(gridPane3,startTegelImagePath[2]);
        laadStarttegels(gridPane4,startTegelImagePath[3]);*/
    }
/*-------------------------------------------------FRONTEND---------------------------------------------------*/
public void laadStarttegels(GridPane gridPane, String startTegelImagePath)
    {
        double celHoogte = gridPane.getRowConstraints().get(0).getPrefHeight();
        double celBreedte = gridPane.getColumnConstraints().get(0).getPrefWidth();

        ImageView imageView = new ImageView(new Image(startTegelImagePath));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(celHoogte - 10);
        imageView.setFitHeight(celHoogte - 10);

        gridPane.add(imageView,2, 2);
    }

/*-------------------------------------------------BACKEND---------------------------------------------------*/

    public void toonTegelLijst(List<DominoTegel> lijst)
    {
        int count = 0;
        for (DominoTegel tegel : lijst)
        {
            count++;
            System.out.printf("%d : %s", count, tegel.toString());
        }
    }

    public void spelSituatie()
    {
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

    public void speelRonde()
    {
        // TODO - implement SpelApplicatie.speelRonde
        speelBeurt();
        if (dc.isEindeSpel()) {
            System.out.println("Het spel is afgelopen.");
        }
    }


}
