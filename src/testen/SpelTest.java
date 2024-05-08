import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import util.Kleur;
import domein.*;

public class SpelTest {

    HashMap<Speler, Kleur> spelers = new HashMap<>();

    @BeforeEach
    public void before() {
        spelers.put(new Speler("Speler 1", 2004), Kleur.ROOS);
        spelers.put(new Speler("Speler 2", 2004), Kleur.BLAUW);
        spelers.put(new Speler("Speler 3", 2004), Kleur.GEEL);
    }

    @Test
    public void test_spel_voldoendeSpelers() {
        Spel spel = new Spel(spelers);
        assertEquals(3, spel.getSpelers().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_spel_onvoeldoeneSpelers() {
        spelers.remove(0);
        new Spel(spelers);
    }

    @Test
    public void test_spel_getTegelgebieden() {
        HashMap<Speler, Kleur> spelers = new HashMap<>();
        Spel spel = new Spel(spelers);
        HashMap<Kleur, TegelGebied> tegelGebieden = spel.getTegelGebieden();
        assertNotNull(tegelGebieden.get(Kleur.ROOS));
        assertNotNull(tegelGebieden.get(Kleur.BLAUW));
        assertNotNull(tegelGebieden.get(Kleur.GEEL));
    }

}
