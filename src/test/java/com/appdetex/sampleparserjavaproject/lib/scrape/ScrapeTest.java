package com.appdetex.sampleparserjavaproject.lib.scrape;
import com.appdetex.sampleparserjavaproject.lib.json.Json;
import com.appdetex.sampleparserjavaproject.lib.json.JsonStrategyGson;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * https://play.google.com/store/apps/details?id=com.ustwo.monumentvalley
 * https://play.google.com/store/apps/details?id=org.ovh.SpaceSTG3&hl=en-US
 */
public class ScrapeTest {

    JsonStrategyGson jsonStrategyGson = new JsonStrategyGson();
    Json json = new Json(jsonStrategyGson);
    ScrapeStrategyJsoup scrapeStrategy = new ScrapeStrategyJsoup();
    Scrape scrape = new Scrape(json, scrapeStrategy);

    @Test
    public void attributes_MissingUrl_Fail() throws IOException {
        List<String> attributes = new ArrayList<String>();
        attributes.add("PARAM1");
        attributes.add("PARAM2");
        attributes.add("PARAM3");

        try {
            scrape.queryAttributes(attributes);

        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void attributes_Simple_Pass() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ScrapeParameterException {
        List<String> attributes = new ArrayList<String>();
        attributes.add("title");
        attributes.add("description");
        attributes.add("publisher");
        attributes.add("price");
        attributes.add("rating");

        scrape.init("https://play.google.com/store/apps/details?id=org.ovh.SpaceSTG3&hl=en-US");
        scrape.queryAttributes(attributes);
    }

    @Test
    public void attributes_BadHost_Fail() throws IOException {
        List<String> attributes = new ArrayList<String>();
        attributes.add("PARAM1");

        try {
            scrape.init("http://.............");
            scrape.queryAttributes(attributes);

        } catch (Exception e) {
            assertTrue(true);
            return;
        }

        fail("Worked when it shouldn't have.");
    }

    @Test
    public void init() {
    }
}