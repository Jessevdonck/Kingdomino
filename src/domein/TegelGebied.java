package domein;

import util.LandschapType;

public class TegelGebied
{
    private static int[][] richting = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private final Landschap[][] gebied;
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
            if(rij == 3 && kolom == 2){
                throw new IllegalArgumentException("kan niet plaatsen in het midden van het bord");
            }
            if(rij == 2 && kolom == 2){
                throw new IllegalArgumentException("kan niet plaatsen in het midden van het bord");
            }
            
            if (kolom + 1 >= 5 || gebied[kolom][rij] != null || gebied[kolom + 1][rij] != null) {
                throw new IllegalArgumentException("Overlapping met andere tegel verticaal.");
            }
            // Tegel links komt niet overeen met landschap
            if (gebied[kolom-1][rij] != null && gebied[kolom-1][rij].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            if(gebied[kolom-1][rij+1] != null && gebied[kolom-1][rij+1].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // Tegel rechts komt niet overeen met landschap
            if (gebied[kolom+1][rij] != null && gebied[kolom+1][rij].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            if (gebied[kolom+1][rij+1] != null && gebied[kolom+1][rij+1].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // tegel onder komt niet overeen met landschap
            if (gebied[kolom][rij+2] != null && gebied[kolom][rij+2].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // Tegel boven komt niet overeen met landschap
            if(gebied[kolom][rij-1] != null && gebied[kolom][rij-1].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");

            gebied[rij][kolom] = tegel.getLandschapType1();
            gebied[rij - 1][kolom] = tegel.getLandschapType2();
        } else { // HORIZONTAAL
            System.out.println("PLAATSTEGEL" + kolom +" rij :" + rij);
            if(rij == 2 && kolom == 2){
                throw new IllegalArgumentException("kan niet plaatsen in het midden van het bord");
            }
            if(rij == 2 && kolom == 1){
                throw new IllegalArgumentException("kan niet plaatsen in het midden van het bord");
            }
            if (kolom + 1 >= 5 || gebied[kolom][rij] != null || gebied[kolom+1][rij] != null) {
                throw new IllegalArgumentException("Overlapping met andere tegel horizontaal.");
            }
            // Tegel links komt niet overeen met landschap
            if (gebied[kolom-1][rij] != null && gebied[kolom][rij].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // Tegel rechts komt niet overeen met landschap
            if (gebied[kolom+2][rij] != null && gebied[kolom+2][rij].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // Tegel onder komt niet overeen met landschap
            if (rij + 1 < gebied[0].length && gebied[kolom][rij+1] != null && gebied[kolom][rij+1].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            if (gebied[kolom+1][rij+1] != null && gebied[kolom+1][rij+1].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // Tegel boven komt niet overeen met landschap
            if(gebied[kolom][rij-1] != null && gebied[kolom][rij-1].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            if (gebied[kolom+1][rij-1] != null && gebied[kolom+1][rij-1].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");



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
