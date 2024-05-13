package domein;

import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.css.PseudoClass;
import persistentie.DominoTegelMapper;
import util.Kleur;
import util.LandschapType;

public class Spel
{
    private List<DominoTegel> tegels;
    private List<DominoTegel> beginKolom;
    private List<DominoTegel> eindkolom;
    private List<Kleur> volgordeSpelers;
    private HashMap<Speler, Kleur> gekozenSpelers;
    private HashMap<Kleur, TegelGebied> tegelGebieden;
    private DominoTegelMapper dominoTegelMapper = new DominoTegelMapper();

    private HashMap<Kleur, DominoTegel> claimdeTegel;


    /**
     * Constructor voor Spel
     * @param spelers HashMap met spelers en hun kleur
     */
    public Spel(HashMap<Speler, Kleur> spelers)
    {
        this.tegels = dominoTegelMapper.geefAlleDominoTegels();
        Collections.shuffle(tegels);

        int aantalTegelsPerSpeler = spelers.size() == 3 ? 6 : 8;
        if (tegels.size() < aantalTegelsPerSpeler) {
            throw new IllegalArgumentException("Er zijn niet genoeg tegels voor het opgegeven aantal spelers.");
        }

        this.tegels = new ArrayList<>(tegels.subList(0, aantalTegelsPerSpeler));

        this.gekozenSpelers = spelers;
        this.volgordeSpelers = null;
        this.tegelGebieden = new HashMap<Kleur, TegelGebied>();
        for (Kleur kleur : gekozenSpelers.values()) {
            this.tegelGebieden.put(kleur, new TegelGebied());
        }

        this.beginKolom = geefKaarten(spelers.size());
        sorteerOpTegelNummer(beginKolom);
        this.eindkolom = geefKaarten(spelers.size());
        sorteerOpTegelNummer(eindkolom);
        this.claimdeTegel = new HashMap<Kleur, DominoTegel>();
    }

    /**
     * @param kleurList List met kleuren
     */
    public void setVolgordeSpelers(List<Kleur> kleurList){
        volgordeSpelers = kleurList;
    }

    /**
     * @return HashMap met tegelgebieden
     */
    public HashMap<Kleur, TegelGebied> getTegelGebieden() {return this.tegelGebieden;}

    /**
     * @return Lijst van de kleuren
     */
    public List<Kleur> getVolgordeSpelers(){return volgordeSpelers;}

    /**
     * @return Lijst van de beginkolom
     */
    public List<DominoTegel> geefBeginKolom(){
        return this.beginKolom;
    }

    /**
     * @return Lijst van de eindkolom
     */
    public List<DominoTegel> geefEindKolom(){
        return this.eindkolom;
    }

    /**
     * @return Lijst van de tegels in het dek
     */
    public List<DominoTegel> getTegelsDeck()
    {
        return tegels;
    }

    /**
     * @return HashMap van de spelers en hun kleur
     */
    public HashMap<Speler, Kleur> getSpelers()
    {
        return gekozenSpelers;
    }

    /**
     * @return HashMap van de spelers en hun kleur
     */
    public HashMap<Speler, Kleur> getGekozenSpelers()
    {
        return gekozenSpelers;
    }


    /**
     * @return een boolean of het het einde van het spel is, true als het einde van het spel is
     */
    public boolean isEindeSpel()
    {
        return tegels.isEmpty();
    }

    /**
     * @param aantalKaarten aantal kaarten dat je wilt
     * @return een lijst van tegels
     */
    public List<DominoTegel> geefKaarten(int aantalKaarten) {
        List<DominoTegel> kaarten = new ArrayList<>();
        for (int i = 0; i < aantalKaarten; i++) {
            kaarten.add(geefTegel());
        }

        return kaarten;
    }

    /**
     * @return Een tegen uit het dek halen
     */
    public DominoTegel geefTegel()
    {
        if (tegels.isEmpty()) {
            throw new RuntimeException("No cards left in the deck");
        }
        return tegels.remove(0);
    }

    /**
     * @param tegels lijst van tegels
     */
    public static void sorteerOpTegelNummer(List<DominoTegel> tegels) {
        tegels.sort(Comparator.comparingInt(DominoTegel::getTegelNummer));
    }

    /**
     * @param winnaars Lijst van winnaars
     * @return Lijst van spelers
     */
    private List<Speler> zetNaarSpelers(List<Kleur> winnaars){
        List<Speler> spelers = new ArrayList<>();
        spelers.add(getSpeler(winnaars.get(0)));
        return spelers;
    }

    /**
     * @param tegelGebied
     * @return een 2D array van de beschikbare tegels
     */
    public Integer[][] geefBeschikbareTegels(TegelGebied tegelGebied){
        Landschap[][] grid = tegelGebied.getGebied();
        Integer[][] beschikbareTegels = new Integer[grid.length][grid[0].length];
        for (int i = 0; i < 5;i++ ){
            for(int j = 0; j < 5; j++) {
                if (grid[i][j] == null) {
                    beschikbareTegels[i][j] = 1; // Set the status of the tile to "Beschikbaar"
                }else {
                    beschikbareTegels[i][j] = 0;
                }
            }
        }
            return beschikbareTegels;
    }

    /**
     * @return HashMap van de winnaars
     */
    public HashMap<Kleur, HashMap<LandschapType, Integer>> geefWinnaars()
    {
        HashMap<Kleur, HashMap<LandschapType, Integer>> scores = geefScores();
        return null;
    }

    /**
     * @param kleur kleur van de speler
     * @return de grootte van het grondgebied
     */
    private int getGrondgebiedGrootte(Kleur kleur) {
        return tegelGebieden.get(kleur).getGrootteGebied();
    }

    /**
     * @param kleur kleur van de speler
     * @return het aantal kronen van het gebied
     */
    private int getAantalKronen(Kleur kleur) {
        return tegelGebieden.get(kleur).getAantalKronen();
    }

    /**
     * @param kleur kleur van de speler
     * @return de speler
     */
    private Speler getSpeler(Kleur kleur) {
        for (Map.Entry<Speler, Kleur> entry : gekozenSpelers.entrySet()) {
            if (entry.getValue() == kleur) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Speler met kleur " + kleur + " niet gevonden");
    }

    /**
     * @return HashMap van de scores
     */
    public HashMap<Kleur, HashMap<LandschapType, Integer>> geefScores()
    {
        HashMap<Kleur, HashMap<LandschapType, Integer>> scores = new HashMap<Kleur, HashMap<LandschapType, Integer>>();
        for (Kleur kleur : gekozenSpelers.values()) {
            scores.put(kleur, tegelGebieden.get(kleur).zoekDomein());
        }
        return scores;
    }

    /**
     * @param kolom kolom van de tegel
     * @param rij rij van de tegel
     * @param verticaal boolean verticaal of niet
     * @param tegel tegel die verplaatst wordt
     * @param spelerIndex index van de speler
     */
    public void verplaatsTegel(int kolom, int rij, boolean verticaal, DominoTegel tegel, int spelerIndex)
    {
        tegelGebieden.get(volgordeSpelers.get(spelerIndex)).plaatsTegel(kolom, rij, verticaal, tegel);
        beginKolom.remove(tegel);
    }

    /**
     * @param kleur kleur van de speler
     * @param dominoTegel tegel die verplaatst wordt
     */
    public void verplaatskoning(Kleur kleur, DominoTegel dominoTegel) {
        claimdeTegel.put(kleur, dominoTegel);
    }

    /**
     * @param kleur kleur van de speler
     * @return de geclaimde tegel van de speler
     */
    public DominoTegel getGeclaimdeTegel(Kleur kleur) {
        return claimdeTegel.get(kleur);
    }

    /**
     * @param spelerIndex index van de speler
     * @param tegel tegel die geplaatst wordt
     * @return een boolean of de tegel geplaatst kan worden
     */
    public boolean kanTegelPlaatsen(int spelerIndex,DominoTegel tegel ) {
        return tegelGebieden.get(volgordeSpelers.get(spelerIndex))
                .kanTegelPlaatsen(tegel);
    }
}
