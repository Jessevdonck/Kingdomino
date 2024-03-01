package domein;

// testcommit Tommy - test2 na nieuwe ssh keys te genereren

public class DomeinController {

    private final SpelerRepository spelerRepository;
    private final SpelRepository spelRepository;

    public DomeinController() {
        spelerRepository = new SpelerRepository();
        spelRepository = new SpelRepository();
    }

    public void registreerSpeler(String gebruikersnaam, int geboortejaar) 
    {
        Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
        spelerRepository.voegToe(nieuweSpeler);
    }
    
    public void voegSpelerAanSpel() {
    	
    }
    
    public void startSpel() {
    	
    }

}
