package domein;

public class DominoTegel {
    private String landschapType1;
    private int kronen1;
    private String landschapType2;
    private int kronen2;

    public DominoTegel(String landschapType1, int kronen1, String landschapType2, int kronen2) {
        if (kronen1 < 0 || kronen1 > 3 || kronen2 < 0 || kronen2 > 3) {
            throw new IllegalArgumentException("Het aantal kronen moet tussen 0 en 3 liggen.");
        }
        this.landschapType1 = landschapType1;
        this.kronen1 = kronen1;
        this.landschapType2 = landschapType2;
        this.kronen2 = kronen2;
    }

    public String getLandschapType1() {
        return landschapType1;
    }

    public int getKronen1() {
        return kronen1;
    }

    public String getLandschapType2() {
        return landschapType2;
    }

    public int getKronen2() {
        return kronen2;
    }

    @Override
    public String toString() {
        return "[" + landschapType1 + "|" + kronen1 + "] [" + landschapType2 + "|" + kronen2 + "]";
    }
}
