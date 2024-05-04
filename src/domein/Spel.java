package domein;

import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    public Spel(HashMap<Speler, Kleur> spelers)
    {
        this.tegels = dominoTegelMapper.geefAlleDominoTegels();
        Collections.shuffle(tegels);

        int aantalTegelsPerSpeler = spelers.size() == 3 ? 36 : 48;
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
        this.claimdeTegel = new HashMap<Kleur, DominoTegel>();
    }

    public void setVolgordeSpelers(List<Kleur> kleurList){
        volgordeSpelers = kleurList;
    }

    public HashMap<Kleur, TegelGebied> getTegelGebieden() {return this.tegelGebieden;}
    public List<Kleur> getVolgordeSpelers(){return volgordeSpelers;}
    public List<DominoTegel> geefBeginKolom(){
        return beginKolom;
    }
    public List<DominoTegel> geefEindKolom(){
        return eindkolom;
    }
    public List<DominoTegel> getTegelsDeck()
    {
        return tegels;
    }

    public HashMap<Speler, Kleur> getSpelers()
    {
        return gekozenSpelers;
    }

    public HashMap<Speler, Kleur> getGekozenSpelers()
    {
        return gekozenSpelers;
    }


    public boolean isEindeSpel()
    {
        return tegels == null;
    }

    public List<DominoTegel> geefKaarten(int aantalKaarten) {
        List<DominoTegel> kaarten = new ArrayList<>();
        for (int i = 0; i < aantalKaarten; i++) {
            kaarten.add(geefTegel());
        }

        return kaarten;
    }

    public void maakStartKolom() {
        beginKolom = geefKaarten(getSpelers().size());
        sorteerOpTegelNummer(beginKolom);
    }

    public void maakEindKolom() {
        eindkolom = geefKaarten(getSpelers().size());
        sorteerOpTegelNummer(eindkolom);
    }

    public static void sorteerOpTegelNummer(List<DominoTegel> tegels) {
        tegels.sort(Comparator.comparingInt(DominoTegel::getTegelNummer));
    }

    private List<Speler> zetNaarSpelers(List<Kleur> winnaars){
        List<Speler> spelers = new ArrayList<>();
        spelers.add(getSpeler(winnaars.get(0)));
        return spelers;
    }

    public List<Speler> geefWinnaars()
    {
        HashMap<Kleur, Integer> scores = geefScores();
        int hoogsteScore = -1;
        List<Kleur> winnaars = new ArrayList<>();

        for (int score : scores.values()) {
            if (score > hoogsteScore) {
                hoogsteScore = score;
            }
        }

        for (Map.Entry<Kleur, Integer> entry : scores.entrySet()) {
            if (entry.getValue() == hoogsteScore) {
                winnaars.add(entry.getKey());
            }
        }

        if (winnaars.size() == 1) {
            zetNaarSpelers(winnaars);
        }

        int grootsteGrondgebied = -1;

        for (Kleur kleur : winnaars) {
            int grootte = getGrondgebiedGrootte(kleur);
            if ( grootte>= grootsteGrondgebied) {
                grootsteGrondgebied = grootte;
            }else
            {
                winnaars.remove(kleur);
            }
        }

        if (winnaars.size() == 1) {
            zetNaarSpelers(winnaars);
        }

        int meesteKronen = -1;

        for (Kleur kleur : winnaars) {
            int grootte = getAantalKronen(kleur);
            if ( grootte>= meesteKronen) {
                meesteKronen = grootte;
            }else
            {
                winnaars.remove(kleur);
            }
        }

        return zetNaarSpelers(winnaars);
    }

    private int getGrondgebiedGrootte(Kleur kleur) {
        return tegelGebieden.get(kleur).getGrootteGebied();
    }

    private int getAantalKronen(Kleur kleur) {
        return tegelGebieden.get(kleur).getAantalKronen();
    }

    private Speler getSpeler(Kleur kleur) {
        for (Map.Entry<Speler, Kleur> entry : gekozenSpelers.entrySet()) {
            if (entry.getValue() == kleur) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Speler met kleur " + kleur + " niet gevonden");
    }

    public HashMap<Kleur, Integer> geefScores()
    {
        HashMap<Kleur, Integer> scores = new HashMap<Kleur, Integer>();
        for (Kleur kleur : gekozenSpelers.values()) {
            scores.put(kleur, tegelGebieden.get(kleur).zoekDomein());
        }
        return scores;
    }

    public void schudDominotegels()
    {
        Collections.shuffle(tegels);
    }


    public HashMap<Kleur, Integer> getScore()
    {
        HashMap<Kleur, Integer> scores = new HashMap<Kleur, Integer>();
        for (Kleur kleur : gekozenSpelers.values()) {
            scores.put(kleur, tegelGebieden.get(kleur).zoekDomein());
        }
        return scores;
    }

    public DominoTegel geefTegel()
    {
        if (tegels.isEmpty()) {
            throw new RuntimeException("No cards left in the deck");
        }
        return tegels.remove(0);
    }

    public void verplaatsTegel(int kolom, int rij, boolean verticaal, DominoTegel tegel)
    {
        tegelGebieden.get(volgordeSpelers.get(0)).plaatsTegel(kolom, rij, verticaal, tegel);
        beginKolom.remove(tegel);
    }

    public void verplaatskoning(Kleur kleur, DominoTegel dominoTegel) {
        claimdeTegel.put(kleur, dominoTegel);
    }

    public DominoTegel getGeclaimdeTegel(Kleur kleur) {
        return claimdeTegel.get(kleur);
    }

}
