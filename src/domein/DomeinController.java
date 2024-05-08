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
    private List<DominoTegel> dominoTegels;

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

    public HashMap<Speler, Kleur> getSpelendeSpelers(){


        return spelRepository.getSpelers();
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
    public List<SpelerDTO> getWinnaars() {
        List<Speler> winnaars = spelRepository.geefWinnaars();
        return Arrays.stream(winnaars.toArray(new Speler[0]))
                .map(speler -> new SpelerDTO(speler.getGebruikersnaam(), speler.getGeboortejaar(), speler.getAantalGewonnen(), speler.getAantalGespeeld()))
                .toList();
    };

    // int kolom is 0 == beginkolom , 1 == eindkolom
    public void voegKoningAanKaart(Kleur kleur, int index, int kolom)
    {
        if(kolom == 0) {
            spelRepository.getBeginKolom().get(index - 1).claimTegel(kleur);
        }
        else {
            spelRepository.getEindKolom().get(index - 1).claimTegel(kleur);
        }
    }


    public List<DominoTegelDTO> getBeginKolom(){
        List<DominoTegel> startKolom = spelRepository.getBeginKolom();
        return Arrays.stream(startKolom.toArray(new DominoTegel[0]))
                .map(tegel -> new DominoTegelDTO(tegel.getLandschapType1().getType(), tegel.getLandschapType2().getType(), tegel.getTegelNummer(), tegel.getKronen1(), tegel.getKronen2(), tegel.getKoningVanSpeler().toString()))
                .toList();
    }

    public List<DominoTegelDTO> getEindKolom() {
        List<DominoTegel> tweedeKolom = spelRepository.getEindKolom();
        return Arrays.stream(tweedeKolom.toArray(new DominoTegel[0]))
                .map(tegel -> new DominoTegelDTO(tegel.getLandschapType1().getType(), tegel.getLandschapType2().getType(), tegel.getTegelNummer(), tegel.getKronen1(), tegel.getKronen2(), tegel.getKoningVanSpeler().toString()))
                .toList();
    }

    public List<DominoTegel> getBeschikbareTegels() {
        List<DominoTegel> tegelsDeck = spelRepository.getTegelsDeck();
        return tegelsDeck;
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

    public Kleur getKleurVanSpeler(String naam){return spelRepository.getKleurVanSpeler(naam);}


    public void verplaatsDominotegel(int kolom, int rij, boolean verticaal, DominoTegel tegel)
    {
        spelRepository.verplaatsTegel(kolom, rij, verticaal, tegel);
    }

    public String getFotoAchterkantVanTegel(int tegelNummer)
    {
        for(DominoTegel tegel : dominoTegels)
        {
            if(tegel.getTegelNummer() == tegelNummer)
            {
                return tegel.getFotoAchterkant();
            }
        }
        throw new IllegalArgumentException("Foto tegel achterkant niet gevonden");
    }

    public String getFotoVoorkantVanTegel(int tegelNummer)
    {
        for(DominoTegel tegel : dominoTegels)
        {
            if(tegel.getTegelNummer() == tegelNummer)
            {
                return tegel.getFotoVoorkant();
            }
        }
        throw new IllegalArgumentException("Foto tegel voorkant niet gevonden");
    }

    public void verplaatsKoning(Kleur kleur, DominoTegel dominoTegel){
        spelRepository.verplaatsKoning(kleur, dominoTegel);
    }

    public DominoTegel getGeclaimdeTegel(Kleur kleur){
        return spelRepository.getGeclaimdetegel(kleur);
    }
}
