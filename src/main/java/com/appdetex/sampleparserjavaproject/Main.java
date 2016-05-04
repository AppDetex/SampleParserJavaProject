package com.appdetex.sampleparserjavaproject;


/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {

        if (args.length != 1)   {
            System.out.println("Invalid number of arguments. This program expects a Google Play Store URL as the only argument");
            System.exit(1);
        }

        String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String url = args[0];
        if (!url.matches(urlRegex))    {
            System.out.println("Invalid URL was supplied. This program expects a Google Play Store URL as the only argument");
            System.exit(2);
        }

        GooglePlayScraper scraper = new GooglePlayScraper(url);
        System.out.println(scraper.getJSON());
    }
}
