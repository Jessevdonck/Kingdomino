package domein;

// testcommit Tommy - test2 na nieuwe ssh keys te genereren

import dto.SpelerDTO;
import util.Kleur;

import java.util.Arrays;

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

    public void voegSpelerToeAanGekozenSpelers(String naam, int geboortejaar, int aantalgewonnen, int aantalgespeeld, Kleur kleur){
        spelRepository.voegSpelerToeAanSpel(new Speler(naam, geboortejaar, aantalgewonnen, aantalgespeeld),kleur);
    }

    public Speler[] geefAlleSpelendeSpelers(){
        return spelRepository.getGekozenSpelers().keySet().toArray(new Speler[spelRepository.getGekozenSpelers().size()]);
    }

    public void startSpel(){
        spelRepository.startSpel();
    }

    public boolean isEindeSpel(){
        return spelRepository.isEindeSpel();
    }

    public int [] geefScores(){
        return null;
    }

    public SpelerDTO[] winnaars() {

        return null;
    };

    public SpelerDTO[] geefAlleSpelers(){
        Speler[] spelers = spelerRepository.geefSpelers();
        return Arrays.stream(spelers)
                .map(speler -> new SpelerDTO(
                        speler.getGebruikersnaam(),
                        speler.getGeboortejaar(),
                        speler.getAantalGewonnen(),
                        speler.getAantalGespeeld()))
                .toArray(SpelerDTO[]::new);

    }
}
