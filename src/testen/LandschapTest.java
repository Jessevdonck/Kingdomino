package testen;

import domein.Landschap;
import org.junit.Assert;
import org.junit.Test;
import util.LandschapType;

public class LandschapTest {

    @Test
    void test_maakLandschap() {
        Landschap landschap = new Landschap(LandschapType.BOS, 1);
        Assert.assertEquals(LandschapType.BOS, landschap.getType());
        Assert.assertEquals(1, landschap.getAantalKronen());
    }
}
