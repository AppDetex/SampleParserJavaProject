package com.appdetex.sampleparserjavaproject.test;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.appdetex.sampleparserjavaproject.HtmlParser;

import java.io.IOException;

/**
 * This class has test methods for the HTMLParser class.
 * @author nbrinton
 */
public class HtmlParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void parseToJSON_eggInc_properlyParsesValues() throws IOException{
        HtmlParser parser = new HtmlParser("https://play.google.com/store/apps/details?id=com.auxbrain.egginc");
        parser.parseToJSON();

        // Title
        String expectedTitle = "Egg, Inc.";
        String actualTitle = parser.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        // Description
        String expectedDescription = "In the near future, the secrets of the universe will be unlocked in the chicken egg. You have decided to get in on the gold rush and sell as many eggs as you can.";
        String actualDescription = parser.getDescription();
        Assert.assertEquals(expectedDescription, actualDescription);

        // Publisher
        String expectedPublisher = "Auxbrain Inc";
        String actualPublisher = parser.getPublisher();
        Assert.assertEquals(expectedPublisher, actualPublisher);

        // Rating (This may change...)
        String expectedRating = "4.7";
        String actualRating = parser.getRating();
        Assert.assertEquals(expectedRating, actualRating);

        // Price
        String expectedPrice = "$0.00";
        String actualPrice = parser.getPrice();
        Assert.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void parseToJSON_dungeonCards_properlyParsesValues() throws IOException{
        HtmlParser parser = new HtmlParser("https://play.google.com/store/apps/details?id=com.The717pixels.DungeonCards");
        parser.parseToJSON();

        // Title
        String expectedTitle = "Dungeon Cards";
        String actualTitle = parser.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        // Description
        String expectedDescription = "Dungeon Cards is an exciting mix of puzzle, card game and a classical roguelike. Each movement of your card creates a unique and challenging situation, a mini-puzzle which requires strategic thinking to solve, and solving all that puzzles rewards you with the sense of achievement that quickly becomes addictive.";
        String actualDescription = parser.getDescription();
        Assert.assertEquals(expectedDescription, actualDescription);

        // Publisher
        String expectedPublisher = "717 pixels";
        String actualPublisher = parser.getPublisher();
        Assert.assertEquals(expectedPublisher, actualPublisher);

        // Rating (This may change...)
        String expectedRating = "4.7";
        String actualRating = parser.getRating();
        Assert.assertEquals(expectedRating, actualRating);

        // Price
        String expectedPrice = "$0.00";
        String actualPrice = parser.getPrice();
        Assert.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void parseToJSON_artRage_properlyParsesValues() throws IOException{
        HtmlParser parser = new HtmlParser("https://play.google.com/store/apps/details?id=com.ambientdesign.artrage.playstore");
        parser.parseToJSON();

        // Title
        String expectedTitle = "ArtRage: Draw, Paint, Create";
        String actualTitle = parser.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        // Description
        String expectedDescription = "ArtRage for Android is a realistic painting and drawing app that gives you a toolkit filled with tools that work just like the real thing. Smear and blend thick oils on the screen, sketch with pencils and smudge the strokes to create gradients, work like you would on canvas or paper.";
        String actualDescription = parser.getDescription();
        Assert.assertEquals(expectedDescription, actualDescription);

        // Publisher
        String expectedPublisher = "Ambient Design Ltd.";
        String actualPublisher = parser.getPublisher();
        Assert.assertEquals(expectedPublisher, actualPublisher);

        // Rating (This may change...)
        String expectedRating = "4.3";
        String actualRating = parser.getRating();
        Assert.assertEquals(expectedRating, actualRating);

        // Price
        String expectedPrice = "$4.99";
        String actualPrice = parser.getPrice();
        Assert.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void HtmlParserConstructor_givenInvalidUrl_throwsIOException() throws IOException{
        exception.expect(IllegalArgumentException.class);
        HtmlParser parser = new HtmlParser("garbageUrl");
    }
}