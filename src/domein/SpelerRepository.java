package domein;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

    private final SpelerMapper mapper;

    public SpelerRepository() 
    {
        mapper = new SpelerMapper();
    }
    
    public void voegToe(Speler speler) {
       if (bestaatSpeler(speler.getGebruikersnaam()))
            throw new GebruikersnaamInGebruikException();
       
       mapper.voegToe(speler);
    }


    // return alle spelers als array van Speler[]
    public Speler[] geefSpelers() {return mapper.geefAlleSpelers();}

    private boolean bestaatSpeler(String gebruikersnaam){
        return mapper.geefSpeler(gebruikersnaam)!=null;
    }

    public void updateSpeler(Speler speler) {
        mapper.updateSpeler(speler);
    }

}
