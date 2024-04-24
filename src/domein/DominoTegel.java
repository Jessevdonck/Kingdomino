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


    public DominoTegel(Landschap l1, Landschap l2, int nummer, String fotoAchterkant, String fotoVoorkant) {

        this.landschapType1 = l1;
        this.tegelNummer = nummer;
        this.landschapType2 = l2;
        this.kleurVanKoning = null;
        this.fotoAchterkant = fotoAchterkant;
        this.fotoVoorkant = fotoVoorkant;
    }

    public Landschap getLandschapType1() {
        return landschapType1;
    }
    public Kleur getKoningVanSpeler(){return kleurVanKoning;}
    public int getTegelNummer(){
        return tegelNummer;
    }
    public int getKronen1() {
        return landschapType1.getAantalKronen();
    }

    public Landschap getLandschapType2() {
        return landschapType2;
    }

    public int getKronen2() {
        return landschapType2.getAantalKronen();
    }
    private void setKleurVanKoning(Kleur kleur){
        this.kleurVanKoning = kleur;
    }

    public String getFotoAchterkant(){
        return fotoAchterkant;
    }

    public String getFotoVoorkant(){
        return fotoVoorkant;
    }

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
            return "[" + landschapType1.toString() + "|" + getKronen1() + "][" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        } else {
            return "[" + landschapType1.toString() + "|" + getKronen1() + "]" +
                    " [ " + this.kleurVanKoning.toString() + " ] " +
                    "[" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        }
    }
}
