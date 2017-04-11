package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Missing URL parameter");
            return;
        }

        GooglePlayParser parser = new GooglePlayParser();
        ResultsModel model = parser.getDataForUrl(args[0]);

        if (model == null) {
            System.err.println("Invalid response while fetching data for URL");
            return;
        }

        System.out.println(new Gson().toJson(model));
    }

}
