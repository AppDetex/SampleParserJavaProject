package com.appdetex.sampleparserjavaproject;

import org.json.JSONObject;
import org.jsoup.Jsoup;

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
            Scraper s = new Scraper(Jsoup.connect(args[0]).get());

            JSONObject result = new JSONObject();
            result.put("title", s.getForItemprop("name"));
            result.put("price", s.getForItemprop("price"));
            result.put("rating", s.getForItemprop("ratingValue"));
            result.put("description", s.getForItemprop("description").split("\n")[0]);
            result.put("publisher", s.getPublisher());

            System.out.print(result.toString(4));
        } catch (IllegalArgumentException exception) {
            System.out.printf("Malformed URL: %s", args[0]);
        } catch (IOException exception) {
            System.out.printf("Could not connect to %s.", args[0]);
        }
    }

}
