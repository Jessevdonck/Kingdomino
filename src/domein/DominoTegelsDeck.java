package domein;

import dto.SpelerDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DominoTegelsDeck
{
    private List<DominoTegel> dominoTegelList;
    public DominoTegelsDeck(){
        this.dominoTegelList = new ArrayList<>();


    }

    public void maakDeck(int aantalSpelers){
        if(aantalSpelers == 3){
            // maak deck met 36 kaarten
        } else if (aantalSpelers == 4) {
            // maak deck met 48 kaarten
        } else {
            throw new IllegalArgumentException("aantal spelers moet 3 of 4 zijn");
        }

    }

    public void Schud(){
        Collections.shuffle(dominoTegelList);
    }

    public DominoTegel geefTegel(){
        if (dominoTegelList.isEmpty()) {
            throw new RuntimeException("No cards left in the deck");
        }
        return dominoTegelList.remove(0);
    }

}
