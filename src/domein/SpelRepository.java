package domein;
import java.util.HashMap;
import java.util.List;

import util.Kleur;

public class SpelRepository {

    private HashMap<Speler, Kleur> gekozenSpelers;
    private Spel momenteelSpel;

    public SpelRepository() {
        gekozenSpelers = new HashMap<>();

    }

    public Spel getMomenteelSpel() {return momenteelSpel;}
    public HashMap<Speler, Kleur> getGekozenSpelers(){return gekozenSpelers;}

    public void voegSpelerToeAanSpel(Speler speler, Kleur kleur) {
        if (gekozenSpelers.size() >= 4) {
            throw new IllegalArgumentException("Het maximum aantal spelers is bereikt.");
        }
        gekozenSpelers.put(speler, kleur);
    }

    public void startSpel() {
        if (gekozenSpelers.size() < 3) {
            throw new IllegalArgumentException("Je moet minimaal 3 spelers hebben om het spel te starten.");
        }
        momenteelSpel = new Spel(gekozenSpelers, new DominoTegelsDeck());


        momenteelSpel.schudDominotegels();

    }
}
