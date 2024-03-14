package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import persistentie.DominoTegelMapper;
import util.Kleur;

public class Spel {
    private int AantalDominotegels;
    private DominoTegelsDeck tegelsDeck;
    private HashMap<Speler, Kleur> gekozenSpelers;

    public Spel(HashMap<Speler, Kleur> spelers, DominoTegelsDeck tegelsDeck) {
        this.tegelsDeck = tegelsDeck;
        tegelsDeck.maakDeck(spelers.size());
        this.gekozenSpelers = spelers;
        if (spelers.size() < 3 || spelers.size() > 4) {
            throw new IllegalArgumentException("Het aantal spelers moet tussen 3 en 4 liggen.");
        }
        this.AantalDominotegels = spelers.size() == 3 ? 36 : 48;
        
    }
    public DominoTegelsDeck getTegelsDeck(){
        return tegelsDeck;
    }
    public int getDominotegels() {
        return AantalDominotegels;
    }
    public HashMap<Speler, Kleur> getSpelers()
    {return gekozenSpelers;}

    public HashMap<Speler, Kleur> getGekozenSpelers() {
        return gekozenSpelers;
    }

    public boolean isEindeSpel() {
        // Als beschikbare tegels op zijn, return true
        return false;
    }

    public Speler[] geefWinnaars() {
        // Calculeren van tegels etc,
        return null;
    }

    public void schudDominotegels() {
        tegelsDeck.Schud();
    }
}
