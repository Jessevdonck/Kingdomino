package domein;

import util.LandschapType;

public class DominoTegel {
    private Landschap landschapType1;
    private Landschap landschapType2;

    public DominoTegel(Landschap l1, Landschap l2) {

        this.landschapType1 = l1;

        this.landschapType2 = l2;

    }

    public LandschapType getLandschapType1() {
        return landschapType1.getType();
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

    @Override
    public String toString() {
        return "[" + landschapType1 + "|" + getKronen1() + "] [" + landschapType2 + "|" + getKronen2() + "]";
    }
}
