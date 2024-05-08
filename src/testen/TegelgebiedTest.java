package testen;

import domein.DominoTegel;
import domein.Landschap;
import domein.Speler;
import domein.TegelGebied;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import util.LandschapType;

public class TegelgebiedTest {

    Landschap landschap1 = new Landschap(LandschapType.BOS, 1);
    Landschap landschap2 = new Landschap(LandschapType.BOS, 0);
    DominoTegel dominoTegel = new DominoTegel(landschap1, landschap2, 1, null, null);

    @Test
    public void maaktegelgebied() {
        TegelGebied tegelgebied = new TegelGebied();
        Assertions.assertEquals(new Landschap[5][5], tegelgebied.getGebied());
    }

    @Test
    public void plaatsTegel() {
        TegelGebied tegelgebied = new TegelGebied();
        tegelgebied.plaatsTegel(0, 0, true, dominoTegel);
        Assertions.assertEquals(new Landschap[5][5], tegelgebied.getGebied());
    }
}
