package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.controller.ScraperController;
import com.appdetex.sampleparserjavaproject.controller.impl.ScraperControllerImpl;
import com.appdetex.sampleparserjavaproject.util.ScraperConfig;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) throws Exception {
        
        // Validate we have a URL passed to us. (Kinda)
        if (hasValidArgs(args)) {
            
            // Create the controller and give it the selector configuration.
            ScraperController controller = new ScraperControllerImpl(new ScraperConfig());

            // Get our data for the URL provided.
            String result = controller.retrieveDataAsJson(args[0]);

            // Report the result.
            System.out.println(result);
        } else {
            
            // Didn't have valid args, so remind the user to provide a URL.
            printUsage();
        }
    }

    // Simple single argument handler.
    private static boolean hasValidArgs(String[] args) {
        return args.length > 0 && args[0].startsWith("http");
    }
    
    // Give the user some feedback
    private static void printUsage() {              
        System.out.println("\nUSAGE: java -jar sampleparserjavaproject.jar <URL>\n");
    }   
}
