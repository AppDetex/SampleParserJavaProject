package com.appdetex.sampleparserjavaproject;

import static com.appdetex.sampleparserjavaproject.model.GooglePlay.SEARCH_MODEL;
import static com.appdetex.sampleparserjavaproject.model.GooglePlay.DETAIL_MODEL;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {

        if (args.length != 1) {
            System.out.println("Error: expected URL as the only argument.\"");
            return;
        }

        String url = args[0];
//        String url = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en=US";
        RetryScraper scraper = new RetryScraper(url, DETAIL_MODEL);

//        String searchUrl = "https://play.google.com/store/search?q=minecraft&c=apps";
//        String searchUrl = "https://play.google.com/store/search?q=appdetox&c=apps";
//        RetryScraper scraper = new RetryScraper(searchUrl, SEARCH_MODEL);

        String json = scraper.process();

        System.out.println(json);
    }

}
