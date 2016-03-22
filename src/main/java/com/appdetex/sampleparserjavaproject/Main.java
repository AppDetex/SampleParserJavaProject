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
        if (args.length != 1) {
        	printUsage();
        	System.exit(1);
        }
        MobileApp mobileApp = new MobileApp(args[0]);
        mobileApp.parseInfo();
    }

    private static void printUsage() {
        System.out.println("Usage: Main [ https://play.google.com/store/your/url ]");
    }
}

