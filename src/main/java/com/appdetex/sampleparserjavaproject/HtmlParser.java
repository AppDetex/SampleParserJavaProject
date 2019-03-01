package com.appdetex.sampleparserjavaproject;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.json.JSONObject;

/**
 * This class represents a Google Play Store App HTML parser web scraper that scrapes the page for some desired fields
 * and stores them in a JSON object.
 * @author nbrinton
 */
public class HtmlParser {
    private Document doc;
    private JSONObject appJSON;

    /**
     * Construct an HtmlParser object.
     * @param url The Google Play Store url of the form: play.google.com/store.apps
     * @throws IOException
     */
    public HtmlParser(String url) throws IOException {
        this.doc = Jsoup.connect(url).get();
        this.appJSON = new JSONObject();
    }

    /**
     * Parse the title, description, publisher, rating, and price of the app and store it in JSON format.
     */
    public void parseToJSON(){
        // Title
        Elements title = doc.select(".AHFaub").select("span");// Returns Title: <span>Egg, Inc.</span>
        appJSON.put("title", title.text().trim());// Gets just the text in the span element

        // Description
        Elements description = doc.select(".PHBdkd").select(".DWPxHb").select("content").select("div");
        // Get the children nodes in this case since we just want the first paragraph and text blocks count as nodes.
        appJSON.put("description", description.first().childNodes().get(0).toString().replace("\n", "").trim());// Extra leading newline needs to be removed.

        // Publisher
        Elements publisher = doc.select(".T32cc.UAO9ie").select("a");
        appJSON.put("publisher", publisher.first().childNodes().get(0).toString().trim());

        // Rating
        Elements rating = doc.select(".BHMmbe");
        appJSON.put("rating", rating.text().trim());

        // Price
        Elements price = doc.select(".oocvOe").select("button");

        if (price.text().contains("Buy")) {
            appJSON.put("price", price.text().replace("Buy", "").trim());
        } else {
            appJSON.put("price", "$0.00");
        }
    }

    /**
     * Get the app's title from the JSON object.
     * @return the app's title
     */
    public String getTitle() {
        return appJSON.get("title").toString();
    }

    /**
     * Get the app's description from the JSON object.
     * @return the app's description
     */
    public String getDescription() {
        return appJSON.get("description").toString();
    }

    /**
     * Get the app's publisher from the JSON object.
     * @return the app's publisher
     */
    public String getPublisher() {
        return appJSON.get("publisher").toString();
    }

    /**
     * Get the app's rating from the JSON object.
     * @return the app's rating
     */
    public String getRating() {
        return appJSON.get("rating").toString();
    }

    /**
     * Get the app's price from the JSON object.
     * @return the app's price
     */
    public String getPrice() {
        return appJSON.get("price").toString();
    }

    @Override
    public String toString() {
        return appJSON.toString(4);// Passing indentFactor activates pretty printing on the JSON object.
    }

}
