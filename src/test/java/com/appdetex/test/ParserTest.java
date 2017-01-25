package com.appdetex.test;

import com.appdetex.sampleparserjavaproject.AppData;
import com.appdetex.sampleparserjavaproject.Parser;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;


/**
 * Test class for the parser
 */
@PrepareForTest(Jsoup.class)
public class ParserTest {

    private Parser parser;

    private static final String PAID_APP_FILE_NAME = "paidAppTest.html";
    private static final String FREE_APP_FILE_NAME = "freeAppTest.html";

    private static final String FAKE_URL = "http://fakeurl.com";

    /**
     * We need a special {@link IObjectFactory}.
     *
     * @return {@link PowerMockObjectFactory}.
     */
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    /**
     * Test an app document that costs money to download
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testPaidApp() throws IOException, ParseException {
        String html = getTestWebPageAsString(PAID_APP_FILE_NAME);

        Document document = Jsoup.parse(html);

        // Mock out the Jsoup class so we can control what document is passed back.
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.parse(Mockito.eq(new URL(FAKE_URL)), Mockito.anyInt())).thenReturn(document);

        parser = new Parser(document);
        AppData data = parser.parse();

        Assert.assertEquals(data.getTitle(), "Carcassonne", "The Title is incorrectly parsed.");
        String expectedDescription = "The award-winning tile based board game is finally here on Android! Just a few years after its release, Carcassonne became a modern classic and a must-play. Turn by turn, the players create a landscape by placing tiles with roads, cities, fields, and cloisters. The players strategically deploy their followers, so called \"Meeples\", as knights, monks, thieves, or farmers to earn points. The player with the most points after the final scoring wins the game. The ever-changing landscape makes each game a new experience. You can play against clever AI opponents or with up to 5 other players in an online or local multiplayer match.";
        Assert.assertEquals(data.getDescription(), expectedDescription, "The description is incorrectly parsed.");
        Assert.assertEquals(data.getPublisher(), "Exozet", "The publisher is incorrectly parsed.");
        Assert.assertEquals(data.getPrice(), "$4.99", "The rating is incorrectly parsed.");
        Assert.assertEquals(data.getRating(), 4.3f, "The rating is incorrectly parsed.");
    }

    /**
     * Test an app document that is free
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testFreeApp() throws IOException, ParseException {
        String html = getTestWebPageAsString(FREE_APP_FILE_NAME);

        Document document = Jsoup.parse(html);

        // Mock out the Jsoup class so we can control what document is passed back.
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.parse(Mockito.eq(new URL(FAKE_URL)), Mockito.anyInt())).thenReturn(document);

        parser = new Parser(document);
        AppData data = parser.parse();

        Assert.assertEquals(data.getTitle(), "Dino Island", "The Title is incorrectly parsed.");
        String expectedDescription = "Dino Island is a totally new raising experience with smooth controls lovingly handcrafted for your Android phones and tablets.";
        Assert.assertEquals(data.getDescription(), expectedDescription, "The description is incorrectly parsed.");
        Assert.assertEquals(data.getPublisher(), "Doodle Mobile Ltd.", "The publisher is incorrectly parsed.");
        Assert.assertEquals(data.getPrice(), "Free", "The rating is incorrectly parsed.");
        Assert.assertEquals(data.getRating(), 4.4f, "The rating is incorrectly parsed.");
    }

    @Test
    public void testNullValues() {
        AppData data = new AppData(null, null, null, null, 0.0f);
        String json = data.toJson();
        String expected = "{\n" +
                "  \"title\": null,\n" +
                "  \"description\": null,\n" +
                "  \"publisher\": null,\n" +
                "  \"price\": null,\n" +
                "  \"rating\": 0.0\n" +
                "}";
        Assert.assertEquals(json, expected, "The null based app data did not populate correctly.");
    }

    /**
     * Helper to read the test file from resources
     *
     * @param fileName the name of the file within the "resources/testFiles" directory
     * @return the contects of the file as a String
     * @throws IOException
     * @throws ParseException
     */
    private String getTestWebPageAsString(String fileName) throws IOException, ParseException {
        return IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("testFiles/" + fileName), "UTF-8");
    }
}