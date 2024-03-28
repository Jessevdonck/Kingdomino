package GUI;

import domein.DomeinController;
import domein.DominoTegel;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import util.Kleur;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SpelController
{
    private final DomeinController dc;
    private Scanner input = new Scanner(System.in);

    public SpelController(DomeinController dc)
    {
        this.dc = dc;
    }

    public void speelBeurt()
    {
        toonTegelLijst(dc.getSpel().geefTweedeKolom());
        System.out.println("Welke tegel wil je nemen?");
        int tegel = input.nextInt();

        dc.getVolgordeKoning().remove(0);
    }

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
