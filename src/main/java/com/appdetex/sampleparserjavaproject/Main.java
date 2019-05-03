package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.googlePlay.AppData;
import com.appdetex.sampleparserjavaproject.googlePlay.AppParser;
import com.appdetex.sampleparserjavaproject.googlePlay.AppScraper;
import org.apache.http.impl.client.HttpClients;

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

        JsonService<AppData> appDataJsonService = new JsonService<>(
                new AppScraper(HttpClients.createDefault()),
                new AppParser());

        System.out.println(appDataJsonService.getJson(args[0]));
    }

}
