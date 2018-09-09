/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nmousa
 */
public class GooglePlayAppScraperTest {

    private static GooglePlayAppScraper appScraper;
    private static Document page;
    private static final String APP_URL = "https://play.google.com/store/apps/details?id=com.flyersoft.moonreaderp";

    public GooglePlayAppScraperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        appScraper = new GooglePlayAppScraper(APP_URL);
        try {
            page = Jsoup.connect(APP_URL).get();
        } catch (IOException ex) {
            Logger.getLogger(GooglePlayAppScraperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of parseTitle method, of class GooglePlayAppScraper.
     */
    @Test
    public void testParseTitle() {
        System.out.println("parseTitle");
        String expResult = "Moon+ Reader Pro";
        String result = appScraper.parseTitle(page);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseDescription method, of class GooglePlayAppScraper.
     */
    @Test
    public void testParseDescription() {
        System.out.println("parseDescription");
        String expResult = "****************";
        String result = appScraper.parseDescription(page);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePublisher method, of class GooglePlayAppScraper.
     */
    @Test
    public void testParsePublisher() {
        System.out.println("parsePublisher");
        String expResult = "Moon+";
        String result = appScraper.parsePublisher(page);
        assertEquals(expResult, result);
    }

    /**
     * Test of parsePrice method, of class GooglePlayAppScraper.
     */
    @Test
    public void testParsePrice() {
        System.out.println("parsePrice");
        String expResult = "$4.99";
        String result = appScraper.parsePrice(page);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseRating method, of class GooglePlayAppScraper. NOTE: this
     * test will fail as ratings of this app changes over time.
     */
    @Test
    public void testParseRating() {
        System.out.println("parseRating");
        Double expResult = 4.6;
        Double result = appScraper.parseRating(page);
        assertEquals(expResult, result);
    }

}
