package domein;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<Kleur> koningRondeEen()
    {
        List<Kleur> koningen = new ArrayList<>(getSpelers().values());

        Collections.shuffle(koningen);
        momenteelSpel.setVolgordeSpelers(koningen);
        return koningen;
    }

    public Spel getMomenteelSpel() {return momenteelSpel;}
    public HashMap<Speler, Kleur> getSpelers(){return momenteelSpel.getSpelers();}
    public List<DominoTegel> getStartKolom(){
        return momenteelSpel.geefStartKolom();
    }
    public List<DominoTegel> getTweedeKolom(){
        return momenteelSpel.geefTweedeKolom();
    }
    public List<DominoTegel> getTegelsDeck(){
        return momenteelSpel.getTegelsDeck();
    }
    public List<Kleur> getVolgordeKoning(){
        return momenteelSpel.getVolgordeSpelers();

    }

    public void voegSpelerToeAanSpel(Speler speler, Kleur kleur)
        {
            if (gekozenSpelers.size() >= 4) {
                throw new IllegalArgumentException("Het maximum aantal spelers is bereikt.");
            }
            gekozenSpelers.put(speler, kleur);
        }

    public void verwijderSpelerUitSpel(String naam)
        {
            Speler teVerwijderenSpeler = null;

            //Speler zoeken op basis van naam
        for (Speler speler : gekozenSpelers.keySet())
        {
            if (speler.getGebruikersnaam().equals(naam))
            {
                teVerwijderenSpeler = speler;
                break;
            }
        }

        if (teVerwijderenSpeler != null){
            gekozenSpelers.remove(teVerwijderenSpeler);
        } else {
            throw new IllegalArgumentException("Speler niet gevonden in het spel.");
        }
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

    public void kiesTegel( int tegelnummer){
        List<DominoTegel> tegels = momenteelSpel.geefTweedeKolom();
        for (DominoTegel tegel : tegels) {
            if (tegel.getTegelNummer() == tegelnummer) {
                tegel.claimTegel(getVolgordeKoning().get(0));
            }
        }
    }

    public void startSpel() {
        if (gekozenSpelers.size() < 3) {
            throw new IllegalArgumentException("Je moet minimaal 3 spelers hebben om het spel te starten.");
        }

        momenteelSpel = new Spel(gekozenSpelers, maakDeck(gekozenSpelers.size()));
        momenteelSpel.schudDominotegels();
        momenteelSpel.maakStartKolom();


    }

    /*public void verplaatsDominoTegel(String waar, String richting) {
        momenteelSpel.verplaatsDominoTegel(waar, richting);
    }*/
}
