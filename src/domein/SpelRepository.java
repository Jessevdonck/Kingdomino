package domein;
import java.util.*;

import persistentie.DominoTegelMapper;
import util.Kleur;
import util.LandschapType;

public class SpelRepository {

    private HashMap<Speler, Kleur> gekozenSpelers;
    private Spel momenteelSpel;

    private DominoTegelMapper dominoTegelMapper = new DominoTegelMapper();

    public SpelRepository() {
        gekozenSpelers = new HashMap<>();
    }

    public List<Kleur> koningRondeEen() {
        List<Kleur> koningen = new ArrayList<>(getSpelers().values());

        Collections.shuffle(koningen);
        momenteelSpel.setVolgordeSpelers(koningen);
        return koningen;
    }

    public Spel getMomenteelSpel() {
        return momenteelSpel;
    }

    public HashMap<Speler, Kleur> getSpelers() {
        return momenteelSpel.getSpelers();
    }

    public List<DominoTegel> getStartKolom() {
        return momenteelSpel.geefStartKolom();
    }

    public List<DominoTegel> getTweedeKolom() {
        return momenteelSpel.geefTweedeKolom();
    }

    public List<DominoTegel> getTegelsDeck() {
        return momenteelSpel.getTegelsDeck();
    }

    public List<Kleur> getVolgordeKoning() {
        return momenteelSpel.getVolgordeSpelers();

    }

    public void voegSpelerToeAanSpel(Speler speler, Kleur kleur) {
        System.out.println(gekozenSpelers);
        if (!isKleurGekozen(kleur))
        {
            if (gekozenSpelers.size() < 4) {
                gekozenSpelers.put(speler, kleur);
            } else {
                throw new IllegalArgumentException("Het maximum aantal spelers is bereikt.");
            }
        } else
        {
            throw new IllegalArgumentException("Kleur is al gekozen!");
        }


    }

    public void verwijderSpelerUitSpel(String naam) {
        Speler teVerwijderenSpeler = null;

        //Speler zoeken op basis van naam
        for (Speler speler : gekozenSpelers.keySet()) {
            if (speler.getGebruikersnaam().equals(naam)) {
                gekozenSpelers.remove(speler);
                System.out.println(gekozenSpelers);
                break;
            }
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

    // In maakDeck roepen we eerst de mapperfunctie op om alle kaarten in een lijst te geven
    // Erna shuffelen we deze lijst en overlopen we die lijst
    // Tijdens het overlopen zetten we de kaarten over naar een goede lijst met kaarten, dit tot 36 of 48
    // Niet echt performant, maar zo kleinschalig maakt het niet uit (Jordi zei om het zo te doen)
    public List<DominoTegel> maakDeck(int aantalSpelers) {

        List<DominoTegel> deck = dominoTegelMapper.geefAlleDominoTegels();

        Collections.shuffle(deck);

        List<DominoTegel> goedeDeck = new ArrayList<>();

        if (aantalSpelers == 3) {
            for (int i = 0; i <= 35; i++) {
                goedeDeck.add(deck.get(i));
            }
        } else if (aantalSpelers == 4) {
            for (int i = 0; i <= 47; i++) {
                goedeDeck.add(deck.get(i));
            }
        } else {
            throw new IllegalArgumentException("aantal spelers moet 3 of 4 zijn");
        }

        return goedeDeck;

    }

    public void kiesTegel(int tegelnummer) {
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

    public boolean isKleurGekozen(Kleur kleur)
    {
        return gekozenSpelers.containsValue(kleur);
    }

    public Kleur getKleurVanSpeler(String naam) {
    for (Map.Entry<Speler, Kleur> entry : gekozenSpelers.entrySet()) {
        if (entry.getKey().getGebruikersnaam().equals(naam)) {
            return entry.getValue();
        }
    }
    throw new IllegalArgumentException("Speler niet gevonden");
    }

    /*public void verplaatsDominoTegel(int rij,int kolom, String richting)  {
        Convert string to boolean, if string is verticaal then true else false
        boolean verticaal = richting.equals("verticaal");


        momenteelSpel.verplaatsDominoTegel(waar, richting);













    }*/
}
