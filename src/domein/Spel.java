package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import persistentie.DominoTegelMapper;
import util.Kleur;

public class Spel {

    private int dominotegels;
    private HashMap<Speler, Kleur> spelerKleur;
    private DominoTegelMapper dominotegelmapper;

    public Spel(HashMap<Speler, Kleur> spelerKleur) {
        this.spelerKleur = spelerKleur;
        if (spelerKleur.size() < 3 || spelerKleur.size() > 4) {
            throw new IllegalArgumentException("Het aantal spelers moet tussen 3 en 4 liggen.");
        }
        this.dominotegels = spelerKleur.size() == 3 ? 36 : 48;
        
    }

    public int getDominotegels() {
        return dominotegels;
    }

    public HashMap<Speler, Kleur> getSpelerKleur() {
        return spelerKleur;
    }

    public List<DominoTegel> schudDominotegels() {
        List<DominoTegel> dominotegels = new ArrayList<>();
        Collections.shuffle(dominotegels);
        return dominotegels;
    }
}
