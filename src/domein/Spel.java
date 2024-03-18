package domein;

import java.util.*;

import persistentie.DominoTegelMapper;
import util.Kleur;
import util.LandschapType;

public class Spel
{
    private int AantalDominotegels;


    private List<DominoTegel> tegels;
    private List<DominoTegel> startKolom;
    private List<DominoTegel> tweedeKolom;
    private HashMap<Speler, Kleur> gekozenSpelers;

    public Spel(HashMap<Speler, Kleur> spelers, List<DominoTegel> tegelsDeck)
    {
        this.tegels = tegelsDeck;
        this.gekozenSpelers = spelers;
        if (spelers.size() < 3 || spelers.size() > 4) {
            throw new IllegalArgumentException("Het aantal spelers moet tussen 3 en 4 liggen.");
        }
        this.AantalDominotegels = spelers.size() == 3 ? 36 : 48;

    }

    public List<DominoTegel> geefStartKolom(){
        return startKolom;
    }
    public List<DominoTegel> getTegelsDeck()
    {
        return tegels;
    }

    public int getDominotegels()
    {
        return AantalDominotegels;
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
        if(geefWinnaars() != null){
            return true;
        } else{
            return tegels == null;
        }


    }



    public List<DominoTegel> geefKaarten(int aantalKaarten)
    {
        List<DominoTegel> tegels = new ArrayList<DominoTegel>();
        for (int i = 0; i < aantalKaarten; i++) {
            tegels.add(geefTegel());
        }
        return tegels;
    }

    public void maakStartKolom(){
        startKolom = geefKaarten(3);
        sorteerOpTegelNummer(startKolom);
    }

    public static void sorteerOpTegelNummer(List<DominoTegel> tegels) {
            Collections.sort(tegels, new Comparator<DominoTegel>() {
            @Override
            public int compare(DominoTegel tegel1, DominoTegel tegel2) {
                return Integer.compare(tegel1.getTegelNummer(), tegel2.getTegelNummer());
            }
        });
    }

    public Speler[] geefWinnaars()
    {
        // Calculeren van tegels etc,
        return null;
    }

    public void schudDominotegels()
    {
        Collections.shuffle(tegels);
    }


    public DominoTegel geefTegel()
    {
        if (tegels.isEmpty()) {
            throw new RuntimeException("No cards left in the deck");
        }
        return tegels.remove(0);
    }
}
