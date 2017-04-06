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

    public static void main( String[] args ) {

        try {

            Document doc = Jsoup.connect(args[0]).get();

            ResultsModel model = new ResultsModel();
            model.setTitle(doc.select(".id-app-title").text());
            model.setDescription(doc.select("[itemprop=\"description\"] > div").html().split("(<br */?>)")[0]);
            model.setPublisher(doc.select("[itemprop=\"author\"] [itemprop=\"name\"]").text());
            model.setPrice(doc.select("[itemprop=\"price\"]").attr("content"));
            model.setRating(Double.valueOf(doc.select(".score-container .score").text()));

            System.out.println(new Gson().toJson(model));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
