package com.appdetex.sampleparserjavaproject;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL and parse out certain
 * data, printing that data to stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) exit();
        if(!args[0].contains("https://play.google.com/store/apps/details?id=")) exit();
        
        GooglePlayAppScraper scraper = new GooglePlayAppScraper(args[0]);
        String appDataJSON = scraper.parse();
        System.out.println(appDataJSON);
    }

    public static void exit() {
        System.err.println("Please provide a valid Google play App URL as an argument");
        System.exit(1);
    }
}
