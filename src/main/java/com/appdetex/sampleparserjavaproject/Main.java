package com.appdetex.sampleparserjavaproject;

/**
 * Main Java Class
 * <p>
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar sampleparserjavaproject.jar \"url-to-scrape\"");
            System.exit(0);
        }

        System.out.println(args[0]);
    }

}
