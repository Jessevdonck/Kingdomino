package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import persistentie.DominoTegelMapper;
import util.Kleur;

public class Spel {
    private int AantalDominotegels;
    private HashMap<Speler, Kleur> gekozenSpelers;
    private DominoTegelMapper dominotegelmapper;



    public Spel(HashMap<Speler, Kleur> spelers) {
        this.gekozenSpelers = spelers;
        if (spelers.size() < 3 || spelers.size() > 4) {
            throw new IllegalArgumentException("Het aantal spelers moet tussen 3 en 4 liggen.");
        }
        this.AantalDominotegels = spelers.size() == 3 ? 36 : 48;
        
    }

    public int getDominotegels() {
        return AantalDominotegels;
    }
    public int getAantalSpelers(){return gekozenSpelers.size();}

    public HashMap<Speler, Kleur> getGekozenSpelers() {
        return gekozenSpelers;
    }


    public List<DominoTegel> schudDominotegels() {
        List<DominoTegel> dominotegels = new ArrayList<>();
        Collections.shuffle(dominotegels);
        return dominotegels;
    }
}
