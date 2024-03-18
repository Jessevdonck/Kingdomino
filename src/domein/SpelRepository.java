package domein;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.Kleur;
import util.LandschapType;

public class SpelRepository {

    private HashMap<Speler, Kleur> gekozenSpelers;
    private Spel momenteelSpel;

    public SpelRepository() {
        gekozenSpelers = new HashMap<>();

    }

    public Spel getMomenteelSpel() {return momenteelSpel;}
    public HashMap<Speler, Kleur> getSpelers(){return momenteelSpel.getSpelers();}

    public void voegSpelerToeAanSpel(Speler speler, Kleur kleur) {
        if (gekozenSpelers.size() >= 4) {
            throw new IllegalArgumentException("Het maximum aantal spelers is bereikt.");
        }
        gekozenSpelers.put(speler, kleur);
    }


    public boolean isEindeSpel() {
        return momenteelSpel.isEindeSpel();
    }

    public Speler[] geefWinnaars() {
        return momenteelSpel.geefWinnaars();
    }
    
    // De speler met het hoogste aantal prestigepunten is de winnaar
    // -Bij gelijk aantal prestigepunten wint de speler met het grootste grondgebied van een domein binnen zijn koninkrijk
    // -Indien nog altijd gelijk dan wint de speler met de meeste kronen binnen zijn koninkrijk
    // -Indien dan nog altijd gelijk, wordt de overwinning gedeeld

    public int getScore(Speler speler) {
        return 0;
    }

    // Een koninkrijk bestaat uit een aantal afzonderlijke domeinen.
    // Een domein is een gebied van aan elkaar grenzende vakjes van een gelijk landschapstype.
    // Per domein verdien je volgend aantal prestigepunten: aantal vakjes x aantal kronen in het domein.
    // Een domein zonder een kroon brengt dus 0 prestigepunten op.
    // Bereken voor elk domein het aantal prestigepunten en tel alles op voor de speler voor zijn eindtotaal.
    // Een speler kan meerdere afzonderlijke domeinen hebben van eenzelfde landschapstype

    public List<DominoTegel> maakDeck(int aantalSpelers)
    {
        List<DominoTegel> deck = new ArrayList<DominoTegel>();

        if (aantalSpelers == 3) {
            // TODO - Maak deck met 36 kaarten
            // OP DIT MOMENT PLACEHOLDER DECKS
            for (int i = 1; i <= 36; i++) {
                deck.add(new DominoTegel(new Landschap(LandschapType.WEIDE, 0),
                        new Landschap(LandschapType.WEIDE, 0),
                        i));
            }
        } else if (aantalSpelers == 4) {
            for (int i = 1; i <= 48; i++) {
                deck.add(new DominoTegel(new Landschap(LandschapType.WEIDE, 0),
                        new Landschap(LandschapType.WEIDE, 0),
                        i));
            }
            // TODO - Maak deck met 48 kaarten
        } else {
            throw new IllegalArgumentException("aantal spelers moet 3 of 4 zijn");
        }

        return deck;

    }
    public void startSpel() {
        if (gekozenSpelers.size() < 3) {
            throw new IllegalArgumentException("Je moet minimaal 3 spelers hebben om het spel te starten.");
        }

        momenteelSpel = new Spel(gekozenSpelers, maakDeck(gekozenSpelers.size()));
        momenteelSpel.schudDominotegels();
        momenteelSpel.maakStartKolom();


    }
}
