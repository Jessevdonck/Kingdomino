package domein;

public class TegelGebied
{
    private final DominoTegel[][] gebied;

    public TegelGebied() {
        this.gebied = maakGebied();
    }

    // Grid herwerkt van String[25] naar DominoTegel[5][5]
    private DominoTegel[][] maakGebied() {
        DominoTegel[][] grid = new DominoTegel[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = null;
            }
        }
        return grid;
    }

    public DominoTegel[][] getGebied() {
        return gebied;
    }

    public void plaatsTegel(int rij, int kolom, boolean verticaal,DominoTegel tegel) {
        if (rij < 0 || kolom < 0 || rij + 1 >= 5 || kolom + 1 >= 5) {
            throw new IllegalArgumentException("Plaatsing ligt buiten gebied.");
        }

        if (verticaal) { // VERTICAAL
            if (rij + 1 >= 5 || gebied[rij][kolom] != null || gebied[rij + 1][kolom] != null) {
                throw new IllegalArgumentException("Overlapping met andere tegel verticaal.");
            }

            gebied[rij][kolom] = tegel;
            gebied[rij + 1][kolom] = tegel;
        } else { // HORIZONTAAL
            if (kolom + 1 >= 5 || gebied[rij][kolom] != null || gebied[rij][kolom + 1] != null) {
                throw new IllegalArgumentException("Overlapping met andere tegel horizontaal.");
            }

            gebied[rij][kolom] = tegel;
            gebied[rij][kolom + 1] = tegel;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < gebied.length; i++) {
            for (int j = 0; j < gebied[i].length; j++) {
                if (gebied[i][j] == null) {
                    string.append("Leeg\t");
                } else {
                    string.append(String.format("%s\t", gebied[i][j]));
                }
            }
            string.append("\n");
        }
        return string.toString();
    }
}
