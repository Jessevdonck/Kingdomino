package domein;

import com.sun.source.tree.ReturnTree;
import util.Kleur;
import util.LandschapType;

public class DominoTegel {
    private Landschap landschapType1;
    private Landschap landschapType2;
    private int tegelNummer;
    private Kleur kleurVanKoning;


    public DominoTegel(Landschap l1, Landschap l2, int nummer) {

        this.landschapType1 = l1;
        this.tegelNummer = nummer;
        this.landschapType2 = l2;
        this.kleurVanKoning = null;
    }

    public LandschapType getLandschapType1() {
        return landschapType1.getType();
    }
    public Kleur getKoningVanSpeler(){return kleurVanKoning;}
    public int getTegelNummer(){
        return tegelNummer;
    }
    public int getKronen1() {
        return landschapType1.getAantalKronen();
    }

    public LandschapType getLandschapType2() {
        return landschapType2.getType();
    }

    public int getKronen2() {
        return landschapType2.getAantalKronen();
    }
    private void setKleurVanKoning(Kleur kleur){
        this.kleurVanKoning = kleur;
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
                    " " + this.kleurVanKoning.toString() + " " +
                    "[" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        }
    }
}
