package com.appdetex.sampleparserjavaproject;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by justin on 4/10/17.
 */
public class MainTest {

    @Test
    public void testGooglePlayParser() throws Exception {

        GooglePlayParser parser = new GooglePlayParser();

        // Bogus values...
        assertNull(parser.getDataForUrl(""));
        assertNull(parser.getDataForUrl("https://www.google.com/"));

        // Should be good...
        ResultsModel model = parser.getDataForUrl("https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne");
        assertEquals("Carcassonne", model.getTitle());
        assertNotNull(model.getPrice());
    }

}
