package domein;

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

    /**
     * Constructor voor DomeinController
     */
    public DomeinController() {
        spelerRepository = new SpelerRepository();
        spelRepository = new SpelRepository();
    }

    /**
     * Methode om een speler te registreren
     * @param gebruikersnaam gebruikersnaam van de speler
     * @param geboortejaar geboortejaar van de speler
     */
    public void registreerSpeler(String gebruikersnaam, int geboortejaar)
    {
        Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
        spelerRepository.voegToe(nieuweSpeler);
    }

    /**
     * Methode om speler toe te voegen aan de gekozen spelers
     * @param naam naam van de speler
     * @param geboortejaar geboortejaar van de speler
     * @param aantalgewonnen aantal gewonnen spellen
     * @param aantalgespeeld aantal gespeelde spellen
     * @param kleur kleur van de speler
     */
    public void voegSpelerToeAanGekozenSpelers(String naam, int geboortejaar, int aantalgewonnen, int aantalgespeeld, Kleur kleur){
        spelRepository.voegSpelerToeAanSpel(new Speler(naam, geboortejaar, aantalgewonnen, aantalgespeeld),kleur);
    }

    /**
     * Methode om een speler te verwijderen uit de gekozen spelers
     * @param naam naam van de speler
     */
    public void verwijderSpelerUitGekozenSpelers(String naam)
        {
            spelRepository.verwijderSpelerUitSpel(naam);
        }

    /**
     * @return De gekozen spelers terug
     */
    public HashMap<Speler, Kleur> getSpelendeSpelers(){
        return spelRepository.getSpelers();
    }

    /**
     * Methode om het spel te starten, initialisatie van het spel
     */
    public void startSpel(){
        spelRepository.startSpel();
    }

    /**
     * @return boolean of het spel is gedaan, true = spel gedaan, false = spel nog bezig
     */
    public boolean isEindeSpel(){
        return spelRepository.isEindeSpel();
    }

    /**
     * @return De scores van de spelers terug
     */
    public HashMap<Kleur, Integer> geefScores(){
        return spelRepository.geefScores();
    }
    /**
     *  Methode die het einde van het spel verwerkt
     */
    public void verwerkEindeSpel() {
        if (isEindeSpel()) {
            List<Speler> spelers = spelRepository.getSpelers().keySet().stream().toList();
            List<Speler> winnaars = spelRepository.geefWinnaars();

            for (Speler speler : spelers) {
                speler.setAantalGespeeld(speler.getAantalGespeeld() + 1);
                if(winnaars.contains(speler))
                    speler.setAantalGewonnen(speler.getAantalGewonnen() + 1);
                spelerRepository.updateSpeler(speler);
            }
        }
    }

    /**
     * @return het huidige spel terug
     */
    public Spel getSpel(){
        return spelRepository.getMomenteelSpel();
    }

    /**
     * @return de volgorde van de koningen in KLEUR terug
     */
    public List<Kleur> getVolgordeKoning() {
        return spelRepository.getVolgordeKoning();
    }
    public void setVolgordeKoning(List<Kleur> kleurList){spelRepository.setVolgordeKoning(kleurList);}

    /**
     * Methode om de begin speler te bepalen volgens koning
     */
    public void koningRondeEenShuffle(){
        spelRepository.koningRondeEen();
    }

    /**
     * @return de winnaars van het spel
     */
    public List<SpelerDTO> getWinnaars() {
        List<Speler> winnaars = spelRepository.geefWinnaars();
        return Arrays.stream(winnaars.toArray(new Speler[0]))
                .map(speler -> new SpelerDTO(speler.getGebruikersnaam(), speler.getGeboortejaar(), speler.getAantalGewonnen(), speler.getAantalGespeeld()))
                .toList();
    };

    /**
     * Methode om de koning op een kolom te zetten, 0 beginkolom, 1 eindkolom
     * @param kleur kleur van de speler
     * @param index positie van de tegel
     * @param kolom kolom bepaling
     */
    public void voegKoningAanKaart(Kleur kleur, int index, int kolom)
    {
        if(kolom == 0) {
            spelRepository.getBeginKolom().get(index - 1).claimTegel(kleur);
        }
        else {
            spelRepository.getEindKolom().get(index - 1).claimTegel(kleur);
        }
    }

    /**
     * @return De begin kolom terug
     */
    public List<DominoTegel> getBeginKolom(){

        return spelRepository.getBeginKolom();
    }

    /**
     * @return De eind kolom terug
     */
    public List<DominoTegel> getEindKolom() {
        return spelRepository.getEindKolom();

    }
    public void updateKaarten() {
        spelRepository.getMomenteelSpel().geefBeginKolom().clear();
        List<DominoTegel> eindKolomSwitch = spelRepository.getMomenteelSpel().geefEindKolom();
        spelRepository.getMomenteelSpel().geefBeginKolom().addAll(eindKolomSwitch);
        spelRepository.getMomenteelSpel().geefEindKolom().clear();
        spelRepository.
                getMomenteelSpel().
                geefEindKolom().
                addAll(
                        spelRepository.
                                getMomenteelSpel().
                                geefKaarten(getSpelendeSpelers().size())
                );

    }

    /**
     * @return De beschikbare tegels terug
     */
    public List<DominoTegel> getBeschikbareTegels() {
        List<DominoTegel> tegelsDeck = spelRepository.getTegelsDeck();
        return tegelsDeck;
    }

    /**
     * @return De spelers terug in een DTO
     */
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

    /**
     * Methode om een tegel te kiezen
     * @param tegelNummer nummer van de tegel
     */
    public void kiesTegel(int tegelNummer){
        spelRepository.kiesTegel(tegelNummer);
    }

    /**
     * @param kleur geselcteerde kleur van de speler
     * @return  boolean of de kleur al gekozen is, true = kleur gekozen, false = kleur nog niet gekozen
     */
    public boolean isKleurGekozen(Kleur kleur) {
        return spelRepository.isKleurGekozen(kleur);
    }

    /**
     * @param naam naam van de speler
     * @return De kleur van de speler terug
     */
    public Kleur getKleurVanSpeler(String naam){return spelRepository.getKleurVanSpeler(naam);}


    /**
     * @param kolom kolom van de tegel
     * @param rij rij van de tegel
     * @param verticaal boolean verticaal of niet
     * @param tegel tegel die verplaatst wordt
     * @param spelerIndex index van de speler
     */
    public void verplaatsDominotegel(int kolom, int rij, boolean verticaal, DominoTegel tegel, int spelerIndex)
    {
        spelRepository.verplaatsTegel(kolom, rij, verticaal, tegel, spelerIndex);
    }

    /**
     * @param tegelNummer nummer van de tegel
     * @return De foto van de achterkant van de tegel terug
     */
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

    /**
     * @param tegelNummer nummer van de tegel
     * @return De foto van de voorkant van de tegel terug
     */
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


    /**
     * @param kleur kleur van de speler
     * @return De geclaimde tegel van de speler terug in de beginkolom
     */
    public DominoTegel getGeclaimdeTegel(Kleur kleur){
        List<DominoTegel> beginKolom = getBeginKolom();
        for(DominoTegel tegel : beginKolom){
            if(tegel.getKoningVanSpeler() == kleur){
                return tegel;
            }
        }
        return null;
        //throw new IllegalArgumentException("geen geclaimde tegel gevonden");
    }

    public boolean kanTegelPlaatsen(int spelerIndex, DominoTegel tegel) {
        return spelRepository.kanTegelPlaatsen(spelerIndex, tegel);
    }
}
