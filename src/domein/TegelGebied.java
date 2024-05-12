package domein;

import exceptions.OverlappingHorizontaalException;
import exceptions.OverlappingVerticaalException;
import exceptions.PlaatsenMiddenVanHetBordException;
import exceptions.TegelNietOvereenMetAanliggendeTegelException;
import util.LandschapType;

public class TegelGebied
{
    private static int[][] richting = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private Landschap[][] gebied;
    private boolean[][] bezocht = new boolean[5][5];

    public TegelGebied() {
        this.gebied = maakGebied();
    }

    private Landschap[][] maakGebied() {
        Landschap[][] grid = new Landschap[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = null;
            }
        }
        return grid;
    }

    public Landschap[][] getGebied() {
        return gebied;
    }

    public void plaatsTegel(int kolom, int rij, boolean verticaal,DominoTegel tegel) {
        if (verticaal) { // VERTICAAL
            if(rij == 1 && kolom == 2){
                throw new PlaatsenMiddenVanHetBordException();
            }
            if(rij == 2 && kolom == 2){
                throw new PlaatsenMiddenVanHetBordException();
            }
            if(gebied[kolom][rij+1] != null && rij <= 3 || gebied[kolom][rij] != null)
                {
                    throw new OverlappingVerticaalException();
                }

            if (kolom-1 >0) {
                // Tegel links komt niet overeen met landschap
                if (gebied[kolom - 1][rij] != null && gebied[kolom - 1][rij].getType() != tegel.getLandschapType1().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
                if (gebied[kolom - 1][rij + 1] != null && gebied[kolom - 1][rij + 1].getType() != tegel.getLandschapType2().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
            }
            // Tegel rechts komt niet overeen met landschap
            if(kolom+1 <= 4 ) {
                if (gebied[kolom + 1][rij] != null && gebied[kolom + 1][rij].getType() != tegel.getLandschapType1().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
                if (gebied[kolom + 1][rij + 1] != null && gebied[kolom + 1][rij + 1].getType() != tegel.getLandschapType2().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
            }
            // tegel onder komt niet overeen met landschap
            if(rij+2 <= 4) {
                if (gebied[kolom][rij + 2] != null && gebied[kolom][rij + 2].getType() != tegel.getLandschapType2().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
            }
            // Tegel boven komt niet overeen met landschap
            if (rij -1 >= 0) {
                if (gebied[kolom][rij - 1] != null && gebied[kolom][rij - 1].getType() != tegel.getLandschapType1().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
            }
            gebied[rij][kolom] = tegel.getLandschapType1();
            gebied[rij + 1][kolom] = tegel.getLandschapType2();
        } else { // HORIZONTAAL
            System.out.println("PLAATSTEGEL" + kolom +" rij :" + rij);
            if(rij == 2 && kolom == 2){
                throw new PlaatsenMiddenVanHetBordException();
            }
            if(rij == 2 && kolom == 1){
                throw new PlaatsenMiddenVanHetBordException();
            }
            if (kolom + 1 >= 5 || gebied[kolom][rij] != null || gebied[kolom+1][rij] != null) {
                throw new OverlappingHorizontaalException();
            }
            // Tegel links komt niet overeen met landschap
            if (gebied[kolom+1][rij] != null && gebied[kolom][rij].getType() != tegel.getLandschapType1().getType() && kolom >= 1)
                throw new TegelNietOvereenMetAanliggendeTegelException();
            // Tegel rechts komt niet overeen met landschap
            if (kolom <= 2) {
                if (gebied[kolom + 2][rij] != null && gebied[kolom + 2][rij].getType() != tegel.getLandschapType2().getType()) {
                    throw new TegelNietOvereenMetAanliggendeTegelException();
                }
            }
            if(rij <= 3)
            {
                // Tegel onder komt niet overeen met landschap
                if (rij + 1 < gebied[0].length && gebied[kolom][rij + 1] != null && gebied[kolom][rij + 1].getType() != tegel.getLandschapType1().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
                if (gebied[kolom + 1][rij + 1] != null && gebied[kolom + 1][rij + 1].getType() != tegel.getLandschapType2().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
            }

            if(rij >= 1)
            {
                // Tegel boven komt niet overeen met landschap
                if (gebied[kolom][rij - 1] != null && gebied[kolom][rij - 1].getType() != tegel.getLandschapType1().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
                if (gebied[kolom + 1][rij - 1] != null && gebied[kolom + 1][rij - 1].getType() != tegel.getLandschapType2().getType())
                    throw new TegelNietOvereenMetAanliggendeTegelException();
            }



            gebied[rij][kolom] = tegel.getLandschapType1();
            gebied[rij][kolom + 1] = tegel.getLandschapType2();
        }

    }

    public int berekenScore(int x, int y) {
        if (x < 0 || y < 0 || x >= 5 || y >= 5 || bezocht[x][y] || gebied[x][y] == null ) {
            return 0;
        }


        bezocht[x][y] = true;


        int size = 1;


        for (int[] dir : richting) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && ny >= 0 && nx < 5 && ny < 5) {
                size += berekenScore(nx, ny);
            }
        }

        return size;
    }

    public int zoekDomein() {

        int size = 0;


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (!bezocht[i][j]) {
                    int grootte = berekenScore(i, j);
                    size += grootte + 1;
                }
            }
        }

        return size;
    }

    public int getAantalKronen() {
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

    public int getGrootteGebied() {
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

    public boolean kanTegelPlaatsen(DominoTegel tegel) {
        // Horizontaal
        LandschapType type1 = tegel.getLandschapType1().getType();
        LandschapType type2 = tegel.getLandschapType2().getType();

        for (int rij = 0; rij < gebied.length; rij++) {
            for (int kolom = 0; kolom < gebied[rij].length - 1; kolom++) {
                if (gebied[rij][kolom] == null && gebied[rij][kolom + 1] == null) {
                    // type 1 boven
                    if((rij <= 3) && (gebied[rij + 1][kolom] != null) &&  gebied[rij + 1][kolom].getType() == type1){
                        return true;
                    }
                    // type 1 onder
                    if((rij >= 1) && (gebied[rij - 1][kolom] != null) && gebied[rij - 1][kolom].getType() == type1){
                        return true;
                    }
                    // type 1 links
                    if((kolom >= 1) && (gebied[rij][kolom - 1] != null) &&  gebied[rij][kolom - 1].getType() == type1){
                        return true;
                    }

                    // type 2 boven
                    if( (rij <= 3) && (kolom <= 3) &&(gebied[rij + 1][kolom + 1] != null) &&  gebied[rij + 1][kolom + 1].getType() == type1 ){
                        return true;
                    }
                    // type 2 onder
                    if((rij >= 1) && (kolom <= 3) && (gebied[rij - 1][kolom + 1] != null) && gebied[rij - 1][kolom + 1].getType() == type1){
                        return true;
                    }
                    // type 2 rechts
                    if((kolom <= 2 ) && (gebied[rij][kolom + 2] != null) && gebied[rij][kolom + 2].getType() == type1 ){
                        return true;
                    }

                }
            }
        }
        // Verticaal
        for (int kolom = 0; kolom < gebied[0].length; kolom++) {
            for (int rij = 0; rij < gebied.length - 1; rij++) {
                if (gebied[rij][kolom] == null && gebied[rij + 1][kolom] == null) {
                    //type 1 links
                    if((kolom >= 1) && (gebied[rij][kolom - 1] != null) && gebied[rij][kolom - 1].getType() == type1){
                        return true;
                    }
                    //type 1 rechts
                    if((kolom <= 3) && (gebied[rij][kolom + 1] != null) && gebied[rij][kolom + 1].getType() == type1){
                        return true;
                    }
                    //type 1 boven
                    if((rij >= 1) && (gebied[rij - 1][kolom] != null) && gebied[rij - 1][kolom].getType() == type1){
                        return true;
                    }
                    //type 2 links
                    if((kolom >= 1) && (rij >= 1) && (gebied[rij + 1][kolom - 1] != null) && gebied[rij + 1][kolom - 1].getType() == type2){
                        return true;
                    }
                    //type 2 rechts
                    if((kolom <= 3) && (rij >= 1) && (gebied[rij + 1][kolom + 1] != null) && gebied[rij + 1][kolom + 1].getType() == type2){
                        return true;
                    }
                    // type 2 onder
                    if((rij <= 2) && (gebied[rij + 2][kolom] != null) && gebied[rij + 2][kolom].getType() == type1){
                        return true;
                    }

                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
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
