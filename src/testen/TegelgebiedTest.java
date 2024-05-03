package testen;

import domein.Landschap;
import domein.Speler;
import domein.TegelGebied;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TegelgebiedTest {
    @Test
    public void maaktegelgebied() {
        tegelgebied = new TegelGebied();
        Assertions.assertEquals(new Landschap[5][5], tegelgebied.getGebied());
    }
}
