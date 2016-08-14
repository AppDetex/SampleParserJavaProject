package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
    	GooglePlayPage page;
    	
        //Argument checking
    	if (args.length != 1) {
    		printUsage();
    	}
    	
    	page = null;
    	
    	try {
			page = new GooglePlayPage(args[0]);
		} catch (MalformedURLException e) {
			System.err.println("Error trying to get: " + args[0]);
			printUsage();
		} catch (IOException e) {
			System.err.println("Error trying to get: " + args[0]);
			printUsage();
		}
    	
    	System.out.println(page.getJson());
    }
    
    /**
     * Prints a usage statement and exits on bad input.
     */
    private static void printUsage() {
    	System.err.println("Usage: java Main <URL>");
    	System.exit(1);
    }

}
