package com.appdetex.sampleparserjavaproject;

import com.google.gson.GsonBuilder;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        if(args.length != 1) {
            System.err.println("Usage: program <url to fetch>");
            System.exit(1);
        }

        var pageParser = PageScraper.fromUrl(args[0]);
        var applicationData = pageParser.scrape();

        var builder = new GsonBuilder();
        builder.setPrettyPrinting();
        var js = builder.create().toJson(applicationData);
        System.out.println(js);
    }

}
