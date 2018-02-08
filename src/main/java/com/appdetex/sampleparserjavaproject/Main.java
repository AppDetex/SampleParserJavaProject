package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.googleplay.AppParser;
import com.appdetex.sampleparserjavaproject.googleplay.model.App;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
    	if (args.length > 0 && args[0].startsWith("http")){
			App app = AppParser.scrapeGooglePlayApp(args[0]);
			System.out.println(app.toJSON());
		}else {
			System.out.println("You need to provide a Google Play Store link "
					+ "in the first command line argument.");
		}
    }
}
