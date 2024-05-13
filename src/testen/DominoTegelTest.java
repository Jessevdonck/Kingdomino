package testen;

import domein.DominoTegel;
import domein.Landschap;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import util.LandschapType;

public class DominoTegelTest {

    @Test
    void test_dominoTegel_maakDominoTegel() {
        DominoTegel dominoTegel = new DominoTegel(new Landschap(LandschapType.GOUDMIJN, 1), new Landschap(LandschapType.BOS, 1), 1, null, null);
        Assertions.assertEquals(LandschapType.GOUDMIJN, dominoTegel.getLandschapType1());
        Assertions.assertEquals(LandschapType.BOS, dominoTegel.getLandschapType2());
        Assertions.assertEquals(1, dominoTegel.getTegelNummer());
        Assertions.assertEquals(1, dominoTegel.getKronen1());
        Assertions.assertEquals(1, dominoTegel.getKronen2());
    }
}
