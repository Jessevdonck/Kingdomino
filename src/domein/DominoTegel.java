package domein;

import com.sun.source.tree.ReturnTree;
import util.Kleur;
import util.LandschapType;

public class DominoTegel {
    private Landschap landschapType1;
    private Landschap landschapType2;
    private int tegelNummer;
    private Kleur kleurVanKoning;

/*
    De fotoAchterkant en fotoVoorkant zijn gewoon de paden naar de foto's voor de kaart.
    Dit om GUI makkelijker te maken.

*/
    private String fotoAchterkant;

    private String fotoVoorkant;


    /**
     * @param l1 Landschap van de linker zijde
     * @param l2 Landschap van de rechterzijde
     * @param nummer Nummer van de tegel
     * @param fotoAchterkant Foto van de achterkant van de tegel
     * @param fotoVoorkant Foto van de voorkant van de tegel
     */
    public DominoTegel(Landschap l1, Landschap l2, int nummer, String fotoAchterkant, String fotoVoorkant) {

        this.landschapType1 = l1;
        this.tegelNummer = nummer;
        this.landschapType2 = l2;
        this.kleurVanKoning = null;
        this.fotoAchterkant = fotoAchterkant;
        this.fotoVoorkant = fotoVoorkant;
    }

    /**
     * @return Landschap van linkerzijde
     */
    public Landschap getLandschapType1() {
        return landschapType1;
    }

    /**
     * @return Kleur van de speler die de tegel heeft geclaimed
     */
    public Kleur getKoningVanSpeler(){return kleurVanKoning;}

    /**
     * @return Nummer van de tegel
     */
    public int getTegelNummer(){
        return tegelNummer;
    }

    /**
     * @return Kronen van de linkerzijde
     */
    public int getKronen1() {
        return landschapType1.getAantalKronen();
    }

    /**
     * @return Landschap van de rechterzijde
     */
    public Landschap getLandschapType2() {
        return landschapType2;
    }

    /**
     * @return Kronen van de rechterzijde
     */
    public int getKronen2() {
        return landschapType2.getAantalKronen();
    }

    /**
     * @param kleur Kleur van de speler die de tegel heeft geclaimed
     */
    private void setKleurVanKoning(Kleur kleur){
        this.kleurVanKoning = kleur;
    }

    /**
     * @return Foto van de achterkant van de tegel
     */
    public String getFotoAchterkant(){
        return fotoAchterkant;
    }

    /**
     * @return Foto van de voorkant van de tegel
     */
    public String getFotoVoorkant(){
        return fotoVoorkant;
    }

    /**
     * @param kleur Kleur van de speler die de tegel claimt
     */
    public void claimTegel(Kleur kleur){
        if (kleurVanKoning == null){
            setKleurVanKoning(kleur);
        }else{
            throw new IllegalArgumentException("Deze tegel is al bezet");
        }
    }

    @Override
    public String toString()
    {
        if (this.kleurVanKoning == null) {
            return "[" + landschapType1.toString() + "|" + getKronen1() + "] [" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        } else {
            return "[" + landschapType1.toString() + "|" + getKronen1() + "]" +
                    " [ " + this.kleurVanKoning.toString() + " ] " +
                    "[" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        }
    }
}
