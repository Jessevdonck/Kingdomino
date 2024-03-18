package domein;

import com.sun.source.tree.ReturnTree;
import util.LandschapType;

public class DominoTegel {
    private Landschap landschapType1;
    private Landschap landschapType2;
    private int tegelNummer;
    private Speler koningVanSpeler;


    public DominoTegel(Landschap l1, Landschap l2, int nummer) {

        this.landschapType1 = l1;
        this.tegelNummer = nummer;
        this.landschapType2 = l2;
        this.koningVanSpeler = null;
    }

    public LandschapType getLandschapType1() {
        return landschapType1.getType();
    }
    public Speler getKoningVanSpeler(){return koningVanSpeler;}
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
    private void setKoningVanSpeler(Speler speler){
        this.koningVanSpeler = speler;
    }

    public void claimTegel(Speler speler){
        if (koningVanSpeler == null){
            setKoningVanSpeler(speler);
        }else{
            throw new IllegalArgumentException("Deze tegel is al bezet");
        }
    }

    @Override
    public String toString()
    {

        if (this.koningVanSpeler == null) {
            return "[" + landschapType1.toString() + "|" + getKronen1() + "][" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        } else {
            return "[" + landschapType1.toString() + "|" + getKronen1() + "]" +
                    " " + this.koningVanSpeler.getGebruikersnaam() + " " +
                    "[" + landschapType2 + "|" + getKronen2() + "]" + "\n";
        }
    }
}
