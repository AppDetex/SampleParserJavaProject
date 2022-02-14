package com.appdetex.sampleparserjavaproject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */

public class Main {

    public static void main( String[] googlePlayURLs) {

        for (String googlePlayURL : googlePlayURLs) {
            try {
                Document appPage = Jsoup.connect(googlePlayURL).get();
                System.out.println(getAppData(appPage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static String getAppData(Document appPage) {
        Element applicationElement = appPage.getElementsByAttributeValue("type", "application/ld+json").first();

        try {
            JSONObject fullAppDataJSON = (JSONObject) new JSONParser().parse(applicationElement.data());
            JSONObject filteredAppDataJSON = new JSONObject();

            //Add the desired keys and values to the new JSON object
            filteredAppDataJSON.put("title",fullAppDataJSON.get("name"));
            JSONObject ratingObject = (JSONObject) fullAppDataJSON.get("aggregateRating");
                String truncatedRating = ratingObject.get("ratingValue").toString().substring(0,3);
                filteredAppDataJSON.put("rating", truncatedRating);
            JSONObject authorObject = (JSONObject) fullAppDataJSON.get("author");
                filteredAppDataJSON.put("publisher", authorObject.get("name"));
            JSONArray pricingArray = (JSONArray) fullAppDataJSON.get("offers");
                JSONObject pricingObject = (JSONObject) pricingArray.get(0);
                String priceAndCurrencyString = pricingObject.get("price").toString() + " " + pricingObject.get("priceCurrency").toString();
                filteredAppDataJSON.put("price", priceAndCurrencyString);
            String firstDescriptionParagraph = fullAppDataJSON.get("description").toString().split("\n")[0];
                filteredAppDataJSON.put("description",firstDescriptionParagraph);

            //Order is not preserved for JSON objects, so this method does the ordering
            return formatAppData(filteredAppDataJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "{}";
    }


    private static String formatAppData (JSONObject object) {
        StringBuilder formatedData = new StringBuilder();

        formatedData.append("{");
        formatedData.append("\n\t\"title\": \"").append(object.get("title")).append("\",");
        formatedData.append("\n\t\"description\": \"").append(object.get("description")).append("\",");
        formatedData.append("\n\t\"publisher\": \"").append(object.get("publisher")).append("\",");
        formatedData.append("\n\t\"price\": \"").append(object.get("price")).append("\",");
        formatedData.append("\n\t\"rating\": \"").append(object.get("rating")).append("\"");
        formatedData.append("\n}");

        return formatedData.toString();
    }

}
