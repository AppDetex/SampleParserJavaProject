package com.appdetex.sampleparserjavaproject;

import org.jsoup.*;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
        //Argument checking
    	if (args.length != 1) {
    		printUsage();
    	}
    	
    	new GooglePlayPage(args[0]);
    }
    
    private static void printUsage() {
    	System.err.println("Usage: java Main <URL>");
    	System.exit(1);
    }

}
