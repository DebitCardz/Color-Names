package me.tech.colornames;

import me.aroze.colornames.ColorNames;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameTests {
    private static ColorNames colorNames;

    @BeforeAll
    public static void setUpBeforeClass() {
        colorNames = new ColorNames();
        colorNames.initialize();
    }

    @Test
    public void hexStringTest() {
        final String white = colorNames.closestColorName("#FFFFFF");
        assertEquals("White", white);

        final String black = colorNames.closestColorName("#000000");
        assertEquals("Black", black);

        final String whiteWithoutPound = colorNames.closestColorName("FFFFFF");
        assertEquals("White", whiteWithoutPound);

        final String random = colorNames.closestColorName("#3d0000");
        assertEquals("Burnt Maroon", random);
    }

    @Test
    public void rgbTest() {
        final String white = colorNames.closestColorName(255, 255, 255);
        assertEquals("White", white);

        final String black = colorNames.closestColorName(0, 0, 0);
        assertEquals("Black", black);

        final String random = colorNames.closestColorName(59, 39, 100);
        assertEquals("Christalle", random);
    }
}
