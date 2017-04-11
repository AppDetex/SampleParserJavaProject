package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by justin on 4/10/17.
 */
public class GooglePlayParser {
    public ResultsModel getDataForUrl(String url) {

        if (!url.contains("play.google.com/store/apps/details")) {
            System.err.println("URL must be a valid Google Play store detail page");
            return null;
        }

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("Error looking up URL " + url);
            return null;
        }

        ResultsModel model = new ResultsModel();
        model.setTitle(doc.select(".id-app-title").text());
        model.setDescription(doc.select("[itemprop=\"description\"] > div").html().split("(<br */?>)")[0]);
        model.setPublisher(doc.select("[itemprop=\"author\"] [itemprop=\"name\"]").text());
        model.setPrice(doc.select("[itemprop=\"price\"]").attr("content"));
        model.setRating(Double.valueOf(doc.select(".score-container .score").text()));

        return model;
    }
}
