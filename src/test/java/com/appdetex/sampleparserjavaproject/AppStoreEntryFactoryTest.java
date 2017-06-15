package com.appdetex.sampleparserjavaproject;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class AppStoreEntryFactoryTest {

    @Test(expected=IllegalArgumentException.class)
    public void TestURLParsingThrowsOnUnknown() throws IOException, IllegalArgumentException {
        // Ensure we throw a IllegalArgumentException on an unknown url
        AppStoreEntryFactory factory = new AppStoreEntryFactory();
        factory.getInstance("foobar");
    }

    @Test(expected=IllegalArgumentException.class)
    public void TestThrowsErrorWithBadURL() throws IOException {
        // Ensure we throw an IllegalArgumentException with a bad URL that contains a known good string.
        AppStoreEntryFactory factory = new AppStoreEntryFactory();
        factory.getInstance("play.google.comfoo");
    }

    @Test(expected=IOException.class)
    public void TestThrowsIOException() throws IOException {
        // Ensure it raises an IOException when given a bad URL
        AppStoreEntryFactory factory = new AppStoreEntryFactory();
        factory.getInstance("http://play.google.com:9999");
    }

    @Test
    public void TestGetPlayStoreEntry() throws IOException {
        // Ensure we get the right object type with a play store URL
        AppStoreEntryFactory factory = new AppStoreEntryFactory();
        IAppStoreEntry obj = factory.getInstance("https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne", true);
        Assert.assertEquals(obj.getClass(), PlayStoreEntry.class);
    }
}