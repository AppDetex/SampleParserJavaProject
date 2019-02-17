package com.appdetex.sampleparserjavaproject;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.validator.routines.UrlValidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public final class Main {

    private Main() { }

    /**
     * Main method that runs when the program is started.
     * @param args command-line arguments
     */
    public static void main(final String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java -jar Sampleparser.jar url");
            System.exit(0);
        }

        UrlValidator urlValidator = new UrlValidator();

        if (!urlValidator.isValid(args[0])) {
            System.out.println("Provided URL is not valid.");
            System.exit(0);
        }

        if (!args[0].startsWith("https://play.google.com/store/apps/details?id=")) {
            System.out.println("Provided URL is not a valid Play Store url.");
            System.exit(0);
        }

        try {
            URL url = new URL(args[0]);
            AppDetails details = Scraper.parse(url);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String result = gson.toJson(details);
            System.out.println(result);

        } catch (ScraperException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
