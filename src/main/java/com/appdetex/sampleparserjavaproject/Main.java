package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.service.App;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        App.Companion.run();
    }

}
