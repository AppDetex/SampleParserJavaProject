package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        if (args.length < 1) {
            System.out.println("Usage: sampleparserjavaproject <URL>");
            System.exit(0);
        }

        try {
            String url = args[0];
            AppStoreEntryFactory factory = new AppStoreEntryFactory();
            IAppStoreEntry obj = factory.getInstance(url);
            System.out.println(obj.toJSON());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
