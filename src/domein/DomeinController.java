package domein;

// testcommit Tommy - test2 na nieuwe ssh keys te genereren

import dto.DominoTegelDTO;
import dto.SpelerDTO;
import util.Kleur;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void verwijderSpelerUitGekozenSpelers(String naam)
        {
            spelRepository.verwijderSpelerUitSpel(naam);
        }

    public HashMap<SpelerDTO, Kleur> getSpelendeSpelers(){
        HashMap<Speler, Kleur> spelendeSpelers = spelRepository.getSpelers();
        HashMap<SpelerDTO, Kleur> spelendeSpelersDTO = new HashMap<>();
        for (Map.Entry<Speler, Kleur> entry : spelendeSpelers.entrySet()) {
            Speler speler = entry.getKey();
            SpelerDTO spelerDTO = new SpelerDTO(speler.getGebruikersnaam(), speler.getGeboortejaar(), speler.getAantalGewonnen(), speler.getAantalGespeeld());
            spelendeSpelersDTO.put(spelerDTO, entry.getValue());
        }
        return spelendeSpelersDTO;
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
    public Spel getSpel(){return spelRepository.getMomenteelSpel();}
    public List<Kleur> getVolgordeKoning() {return spelRepository.getVolgordeKoning();}

    public void koningRondeEenShuffle(){
        spelRepository.koningRondeEen();
    }
    public SpelerDTO[] winnaars() {

        return null;
    };

    // int kolom is 0 == eerstekolom , 1 == tweedekolom
    public void voegKoningAanKaart(Kleur kleur, int index, int kolom){
        if(kolom == 0) {
            spelRepository.getStartKolom().get(index - 1).claimTegel(kleur);
        }
        else {
            spelRepository.getTweedeKolom().get(index - 1).claimTegel(kleur);
        }
    }

    public List<DominoTegelDTO> getStartKolom(){
        List<DominoTegel> startKolom = spelRepository.getStartKolom();
        return Arrays.stream(startKolom.toArray(new DominoTegel[0]))
                .map(tegel -> new DominoTegelDTO(tegel.getLandschapType1().getType(), tegel.getLandschapType2().getType(), tegel.getTegelNummer(), tegel.getKronen1(), tegel.getKronen2(), tegel.getKoningVanSpeler().toString()))
                .toList();
    }

    public List<DominoTegelDTO> getTweedeKolom() {
        List<DominoTegel> tweedeKolom = spelRepository.getTweedeKolom();
        return Arrays.stream(tweedeKolom.toArray(new DominoTegel[0]))
                .map(tegel -> new DominoTegelDTO(tegel.getLandschapType1().getType(), tegel.getLandschapType2().getType(), tegel.getTegelNummer(), tegel.getKronen1(), tegel.getKronen2(), tegel.getKoningVanSpeler().toString()))
                .toList();
    }

    public List<DominoTegelDTO> getBeschikbareTegels() {
        List<DominoTegel> tegelsDeck = spelRepository.getTegelsDeck();
        return Arrays.stream(tegelsDeck.toArray(new DominoTegel[0]))
                .map(tegel -> new DominoTegelDTO(tegel.getLandschapType1().getType(), tegel.getLandschapType2().getType(), tegel.getTegelNummer(), tegel.getKronen1(), tegel.getKronen2(), tegel.getKoningVanSpeler().toString()))
                .toList();
    }

    public String voorbeeld() {
        Speler[] spelers = spelerRepository.geefSpelers();
        String lintje = "De spelers zijn: \n";
        for (Speler speler : spelers) {
            String.format("%s%s\n", lintje, speler.getGebruikersnaam());
            lintje += speler.getGebruikersnaam() + " ";
        }
        return "Dit is een voorbeeld";
    }

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

    public void kiesTegel(int tegelNummer){
        spelRepository.kiesTegel(tegelNummer);
    }

    public boolean isKleurGekozen(Kleur kleur) {
        return spelRepository.isKleurGekozen(kleur);
    }


    /*public void verplaatsDominoTegel(String waar, String richting) {
        spelRepository.verplaatsDominoTegel(waar, richting);
    }*/
}
