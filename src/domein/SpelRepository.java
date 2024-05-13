package domein;
import java.util.*;

import persistentie.DominoTegelMapper;
import util.Kleur;
import util.LandschapType;

public class SpelRepository {

    private HashMap<Speler, Kleur> gekozenSpelers;
    private Spel momenteelSpel;

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

    public List<DominoTegel> getBeginKolom() {
        return momenteelSpel.geefBeginKolom();
    }

    public List<DominoTegel> getEindKolom() {
        return momenteelSpel.geefEindKolom();
    }

    public List<DominoTegel> getTegelsDeck() {
        return momenteelSpel.getTegelsDeck();
    }

    public List<Kleur> getVolgordeKoning() {
        return momenteelSpel.getVolgordeSpelers();
    }

    public void setVolgordeKoning(List<Kleur> kleurList){
        momenteelSpel.setVolgordeSpelers(kleurList);
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

    public Speler vanKleurNaarSpeler(Kleur kleur)
    {
        return momenteelSpel.getSpeler(kleur);
    }

    public boolean isEindeSpel() {
        return momenteelSpel.isEindeSpel();
    }

    public HashMap<Kleur, Integer> geefWinnaars() {

        return momenteelSpel.geefWinnaars();
    }

    // De speler met het hoogste aantal prestigepunten is de winnaar
    // -Bij gelijk aantal prestigepunten wint de speler met het grootste grondgebied van een domein binnen zijn koninkrijk
    // -Indien nog altijd gelijk dan wint de speler met de meeste kronen binnen zijn koninkrijk
    // -Indien dan nog altijd gelijk, wordt de overwinning gedeeld

    public HashMap<Kleur,HashMap<LandschapType, Integer>> geefScores() {
        return momenteelSpel.geefScores();
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

    public void kiesTegel(int tegelnummer) {
        List<DominoTegel> tegels = momenteelSpel.geefEindKolom();
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

        momenteelSpel = new Spel(gekozenSpelers);
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

    public void verplaatsTegel(int kolom, int rij, boolean verticaal, DominoTegel tegel, int spelerIndex)
    {
        momenteelSpel.verplaatsTegel(kolom, rij, verticaal, tegel, spelerIndex);
    }

    public DominoTegel getGeclaimdetegel(Kleur kleur){
       return momenteelSpel.getGeclaimdeTegel(kleur);
    }

    public void verplaatsKoning(Kleur kleur, DominoTegel dominoTegel) {
        momenteelSpel.verplaatskoning(kleur, dominoTegel);
    }

    public boolean kanTegelPlaatsen(int spelerindex, DominoTegel tegel) {
        return momenteelSpel.kanTegelPlaatsen(spelerindex, tegel);
    }
}
