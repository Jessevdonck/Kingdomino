package domein;

import exceptions.OverlappingHorizontaalException;
import exceptions.OverlappingVerticaalException;
import exceptions.PlaatsenMiddenVanHetBordException;
import exceptions.TegelNietOvereenMetAanliggendeTegelException;
import util.LandschapType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TegelGebied
{
    private static int[][] richting = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private Landschap[][] gebied;
    private boolean[][] bezocht = new boolean[5][5];

    /**
     * Constructor voor TegelGebied
     */
    public TegelGebied()
    {
        this.gebied = maakGebied();
    }

    /**
     * Methode om een gebied te maken
     * @return een 5x5 grid met landschappen
     */
    private Landschap[][] maakGebied()
    {
        Landschap[][] grid = new Landschap[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = null;
            }
            grid[2][2] = new Landschap(LandschapType.KONING, 0);
        }
        return grid;
    }

    /**
     * @return het gebied 5x5 grid met landschappen
     */
    public Landschap[][] getGebied()
    {
        return gebied;
    }

    /**
     * Methode om een tegel te plaatsen in het gebied
     * @param kolom de kolom waar de tegel geplaatst moet worden
     * @param rij de rij waar de tegel geplaatst moet worden
     * @param verticaal of de tegel verticaal geplaatst moet worden
     * @param tegel de tegel die geplaatst moet worden
     */
    public void plaatsTegel(int kolom, int rij, boolean verticaal, DominoTegel tegel)
    {
        if (verticaal) {
            if (kanTegelPlaatsenOpSpecifiekePlek(tegel, rij, kolom, verticaal)) {
                gebied[rij][kolom] = tegel.getLandschapType1();
                gebied[rij + 1][kolom] = tegel.getLandschapType2();
            } else {
                throw new TegelNietOvereenMetAanliggendeTegelException();
            }

        } else {
            if (kanTegelPlaatsenOpSpecifiekePlek(tegel, rij, kolom, verticaal)) {
                gebied[rij][kolom] = tegel.getLandschapType1();
                gebied[rij][kolom + 1] = tegel.getLandschapType2();
            } else {
                throw new TegelNietOvereenMetAanliggendeTegelException();
            }
        }
    }

    /**
     * @param x de x coordinaat
     * @param y de y coordinaat
     * @param type het landschapstype
     * @return de score van het gebied
     */
    public List<Integer> berekenScore(int x, int y, LandschapType type)
    {
        List<Integer> punten = new ArrayList<>();
        punten.add(0);
        punten.add(0);
        if (x < 0 || y < 0 || x >= 4 || y >= 4 || bezocht[x][y] || gebied[x][y] == null) {
            return punten;
        }
        if(gebied[x][y].getType() != type){
            return punten;
        }

        bezocht[x][y] = true;
        int aantalKronen = gebied[x][y].getAantalKronen();
        int size = 1;


        for (int[] dir : richting) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && ny >= 0 && nx < 5 && ny < 5) {
                punten = berekenScore(nx, ny, type);
                aantalKronen += punten.get(1);
                size += punten.get(0);

            }
        }

        return punten;
    }

    /**
     * @return een hashmap met de scores van elk landschapstype
     */
    public HashMap<LandschapType, Integer> zoekDomein()
    {


        HashMap<LandschapType, Integer> puntenMap = new HashMap<>();



        for (LandschapType type : LandschapType.values()) {
            int size = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!bezocht[i][j]) {
                        List<Integer> data = berekenScore(i, j, type);
                        size += data.get(0) * data.get(1);

                    }
                }
            }
            puntenMap.put(type, size);
        }
        return puntenMap;
    }

    /**
     * @return het aantal kronen in het gebied
     */
    public int getAantalKronen()
    {
        int aantalKronen = 0;
        for (Landschap[] rij : gebied) {
            for (Landschap landschap : rij) {
                if (landschap != null) {
                    aantalKronen += landschap.getAantalKronen();
                }
            }
        }
        return aantalKronen;
    }

    /**
     * @return de grootte van het gebied
     */
    public int getGrootteGebied()
    {
        int count = 0;
        for (Landschap[] rij : gebied) {
            for (Landschap landschap : rij) {
                if (landschap != null) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @param tegel de tegel die geplaatst moet worden
     * @param rij de rij waar de tegel geplaatst moet worden
     * @param kolom de kolom waar de tegel geplaatst moet worden
     * @param verticaal of de tegel verticaal geplaatst moet worden
     * @return of de tegel geplaats kan worden op een specifieke plek, true als het kan, false als het niet kan
     */
    private boolean kanTegelPlaatsenOpSpecifiekePlek(DominoTegel tegel, int rij, int kolom, boolean verticaal)
    {
        LandschapType type1 = tegel.getLandschapType1().getType();
        LandschapType type2 = tegel.getLandschapType2().getType();
        if (!verticaal) {
            if (gebied[rij][kolom] == null && gebied[rij][kolom + 1] == null) {
                // type 1 boven
                if ((rij <= 3) && (gebied[rij + 1][kolom] != null)
                        && ((gebied[rij + 1][kolom].getType() == type1)
                        || (gebied[rij + 1][kolom].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 1 boven is niet hetzelfde.");
                // type 1 onder
                if ((rij >= 1) && (gebied[rij - 1][kolom] != null)
                        && ((gebied[rij - 1][kolom].getType() == type1)
                        || (gebied[rij - 1][kolom].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 1 onder is niet hetzelfde");
                // type 1 links
                if ((kolom >= 1) && (gebied[rij][kolom - 1] != null)
                        && ((gebied[rij][kolom - 1].getType() == type1)
                        || (gebied[rij][kolom - 1].getType() == LandschapType.KONING))) {
                    return true;
                }else System.out.println("type 1 links is niet hetzelfde");

                // type 2 onder
                if ((rij <= 3) && (kolom <= 3) && (gebied[rij + 1][kolom + 1] != null)
                        && ((gebied[rij + 1][kolom + 1].getType() == type2)
                        || (gebied[rij + 1][kolom + 1].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 2 boven is niet hetzelfde");
                // type 2 boven
                if ((rij >= 1) && (kolom <= 3) && (gebied[rij - 1][kolom + 1] != null)
                        && ((gebied[rij - 1][kolom + 1].getType() == type2)
                        || (gebied[rij - 1][kolom + 1].getType() == LandschapType.KONING))) {
                    return true;
                }else System.out.println("type 2 onder is niet hetzelfde");
                // type 2 rechts
                if ((kolom <= 2) && (gebied[rij][kolom + 2] != null)
                        && ((gebied[rij][kolom + 2].getType() == type2)
                        || (gebied[rij][kolom + 2].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 2 rechts is niet hetzelfde");
            }
        } else {
            if (gebied[rij][kolom] == null && gebied[rij + 1][kolom] == null) {
                //type 1 links
                if ((kolom >= 1) && (gebied[rij][kolom - 1] != null) &&
                        ((gebied[rij][kolom - 1].getType() == type1)
                                || (gebied[rij][kolom - 1].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 1 links is niet hetzelfde");
                //type 1 rechts
                if ((kolom <= 3) && (gebied[rij][kolom + 1] != null) &&
                        ((gebied[rij][kolom + 1].getType() == type1)
                                || (gebied[rij][kolom + 1].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 1 rechts is niet hetzelfde");
                //type 1 boven
                if ((rij >= 1) && (gebied[rij - 1][kolom] != null) &&
                        ((gebied[rij - 1][kolom].getType() == type1)
                                || (gebied[rij - 1][kolom].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 1 boven is niet hetzelfde");
                //type 2 links
                if ((kolom >= 1) && (rij <= 3) && (gebied[rij + 1][kolom - 1] != null) &&
                        ((gebied[rij + 1][kolom - 1].getType() == type2)
                                || (gebied[rij + 1][kolom - 1].getType() == LandschapType.KONING))) {
                    return true;
                }else System.out.println("type 2 links is niet hetzelfde");
                //type 2 rechts
                if ((kolom <= 3) && (rij <= 3) && (gebied[rij + 1][kolom + 1] != null) &&
                        ((gebied[rij + 1][kolom + 1].getType() == type2)
                                || (gebied[rij + 1][kolom + 1].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 2 rechts is niet hetzelfde");
                // type 2 onder
                if ((rij <= 2) && (gebied[rij + 2][kolom] != null) &&
                        ((gebied[rij + 2][kolom].getType() == type2)
                                || (gebied[rij + 2][kolom].getType() == LandschapType.KONING))) {
                    return true;
                } else System.out.println("type 2 onder is niet hetzelfde");
                System.out.println(toString());
                System.out.println("type1: " + type1 + " type2: " + type2);
                // boven midden


            }
        }
        return false;
    }

    /**
     * @param tegel een tegel
     * @return of de tegel nog geplaats kan worden
     */
    public boolean kanTegelPlaatsen(DominoTegel tegel)
    {
        // Horizontaal
        LandschapType type1 = tegel.getLandschapType1().getType();
        LandschapType type2 = tegel.getLandschapType2().getType();

        for (int rij = 0; rij < gebied.length; rij++) {
            for (int kolom = 0; kolom < gebied[rij].length - 1; kolom++) {
                if (kanTegelPlaatsenOpSpecifiekePlek(tegel, rij, kolom, false)) {
                    return true;
                }

            }
        }


        for (int kolom = 0; kolom < gebied[0].length; kolom++) {
            for (int rij = 0; rij < gebied.length - 1; rij++) {
                if (kanTegelPlaatsenOpSpecifiekePlek(tegel, rij, kolom, true)) {
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder();
        for (int kolom = 0; kolom < gebied.length; kolom++) {
            for (int rij = 0; rij < gebied[kolom].length; rij++) {
                if (gebied[kolom][rij] == null) {
                    string.append("Leeg\t");
                } else {
                    string.append(String.format("%s\t", gebied[kolom][rij].getType()));
                }
            }
            string.append("\n");
        }
        return string.toString();
    }
}
