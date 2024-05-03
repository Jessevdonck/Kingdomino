package domein;

import util.LandschapType;

public class TegelGebied
{
    private final Landschap[][] gebied;

    public TegelGebied() {
        this.gebied = maakGebied();
    }

    // Grid herwerkt van String[25] naar DominoTegel[5][5]
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
        if (rij < 0 || kolom < 0 || rij + 1 >= 5 || kolom + 1 >= 5) {
            throw new IllegalArgumentException("Plaatsing ligt buiten gebied.");
        }

        if (verticaal) { // VERTICAAL
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

            gebied[kolom][rij] = tegel.getLandschapType1();
            gebied[kolom][rij+1] = tegel.getLandschapType2();
        } else { // HORIZONTAAL
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
            if (gebied[kolom][rij+1] != null && gebied[kolom][rij+1].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            if (gebied[kolom+1][rij+1] != null && gebied[kolom+1][rij+1].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            // Tegel boven komt niet overeen met landschap
            if(gebied[kolom][rij-1] != null && gebied[kolom][rij-1].getType() != tegel.getLandschapType1().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");
            if (gebied[kolom+1][rij-1] != null && gebied[kolom+1][rij-1].getType() != tegel.getLandschapType2().getType())
                throw new IllegalArgumentException("De tegel komt niet overeen met de aanliggende tegel");

            gebied[kolom][rij] = tegel.getLandschapType1();
            gebied[kolom+1][rij] = tegel.getLandschapType2();
        }

    }

    public int berekenScore() {
        int score = 0;
        boolean[][] bezocht = new boolean[gebied.length][gebied[0].length];

        for (int kolom = 0; kolom < gebied.length; kolom++) {
            for (int rij = 0; rij < gebied[kolom].length; rij++) {
                if (!bezocht[kolom][rij] && gebied[kolom][rij] != null) {
                    LandschapType type = gebied[kolom][rij].getType();
                    int aantalVakjes = zoekDomein(kolom, rij, type, bezocht);
                    int aantalKronen = gebied[kolom][rij].getAantalKronen();
                    score += aantalVakjes * aantalKronen;
                }
            }
        }
        return score;
    }

    private int zoekDomein(int kolom, int rij, LandschapType type, boolean[][] bezocht) {
        if (kolom < 0 || rij < 0 || kolom >= gebied.length || rij >= gebied[0].length || bezocht[kolom][rij] || gebied[kolom][rij] == null || gebied[kolom][rij].getType() != type)
            return 0;

        bezocht[kolom][rij] = true;
        int count = 1;

        // Zoek aangrenzende vakjes in alle richtingen
        count += zoekDomein(kolom + 1, rij, type, bezocht); // Rechts
        count += zoekDomein(kolom - 1, rij, type, bezocht); // Links
        count += zoekDomein(kolom, rij + 1, type, bezocht); // Onder
        count += zoekDomein(kolom, rij - 1, type, bezocht); // Boven

        return count;
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
