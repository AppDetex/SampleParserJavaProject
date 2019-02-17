package com.appdetex.sampleparserjavaproject;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test exception from the Scraper.
 */
public class ExceptionTest {

    /**
     * ExpectedException none.
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test for a bad url.
     */
    @Test
    public void testBadUrl() throws MalformedURLException, ScraperException {
        exception.expect(ScraperException.class);
        exception.expectMessage("Problem retrieving html");

        Scraper.parse(new URL("https://play.google.com/store/apps/details?id="
        + "SomeBogusApp_kjwdqcv3421riohu"));
    }
}
