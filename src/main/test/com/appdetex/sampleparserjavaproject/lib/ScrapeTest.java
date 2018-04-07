package com.appdetex.sampleparserjavaproject.lib;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * https://play.google.com/store/apps/details?id=com.ustwo.monumentvalley
 * https://play.google.com/store/apps/details?id=org.ovh.SpaceSTG3&hl=en-US
 */
class ScrapeTest {

    private Json json = new Json(new Gson());
    private Scrape scrape = new Scrape(json);

    @Test
    void attributes_MissingUrl_Fail() throws IOException {

        List<String> attributes = new ArrayList<String>();
        attributes.add("PARAM1");
        attributes.add("PARAM2");
        attributes.add("PARAM3");

        try {
            scrape.attributes(attributes);

        } catch(IllegalArgumentException e) {
            assertTrue(true);

        } catch (Exception e) {
            fail("Unknown error");
        }
    }

    @Test
    void attributes_Simple_Pass() throws IOException {

        List<String> attributes = new ArrayList<String>();
        attributes.add("title");
        attributes.add("description");
        attributes.add("publisher");
        attributes.add("price");
        attributes.add("rating");

        scrape.init("https://play.google.com/store/apps/details?id=org.ovh.SpaceSTG3&hl=en-US");
        scrape.attributes(attributes);
    }

    @Test
    void attributes_BadHost_Fail() throws IOException {

        List<String> attributes = new ArrayList<String>();
        attributes.add("PARAM1");

        scrape.init("http://........");

        try {
            scrape.attributes(attributes);

        } catch(java.net.UnknownHostException e) {
            assertTrue(true);
            return;

        } catch (Exception e) {
            fail("Unknown error");
        }

        fail("Worked when it shouldn't have.");
    }

    @Test
    void init() {
    }
}