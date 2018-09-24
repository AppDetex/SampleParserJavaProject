package com.appdetex.sampleparserjavaproject;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.lang.System;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        try {
            Document page = Jsoup.connect(args[0]).get();
            System.out.print(getResult(page).toString(4));
        } catch (IllegalArgumentException exception) {
            System.out.printf("Malformed URL: %s", args[0]);
        } catch (IOException exception) {
            System.out.printf("Could not connect to %s.", args[0]);
        }
    }

    static JSONObject getResult(Document page) {
        Scraper s = new Scraper(page);

        JSONObject result = new JSONObject();
        result.put("title", s.getForItemprop("name"));
        result.put("price", s.getForItemprop("price"));

        result.put("rating", JSONObject.NULL);
        String ratingValue = s.getForItemprop("ratingValue");
        if (ratingValue != null) {
            double rating = Math.round(10.0 * Float.parseFloat(ratingValue)) / 10.0;
            result.put("rating", rating);
        }

        result.put("description", s.getForItemprop("description").split("\n")[0]);
        result.put("publisher", s.getPublisher());

        return result;
    }

}
