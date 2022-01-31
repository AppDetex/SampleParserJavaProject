package com.appdetex.sampleparserjavaproject;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.simple.JSONObject;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) throws IOException {

        Scanner ins = new Scanner(System.in);
        String url = "";

        while (url.equals("")) {
            System.out.print("Input URL: ");
            url = ins.next();
        }

        Document doc = Jsoup.connect(url).get();

        Elements appDataSelector = doc.select("script[type=application/ld+json]");
        String appData = appDataSelector.html();
        if(appData.length()==0){
            System.out.print("No Linked Data on this page");
            url = "";
            System.exit(0);
        };
        JSONParser parser = new JSONParser();
        DecimalFormat df = new DecimalFormat("#.#");
        JSONObject jsonLD = new JSONObject();

        try {
            jsonLD = (JSONObject) parser.parse(appData);
        }catch (ParseException e) {
            System.out.println("Linked Data from this page can't be read.");
            System.out.println(e);
        }

        JSONObject author  = (JSONObject) jsonLD.get("author");
        JSONArray offersArray = (JSONArray) jsonLD.get("offers");
        JSONObject offers  = (JSONObject) offersArray.get(0);
        JSONObject aggregateRating = (JSONObject) jsonLD.get("aggregateRating");
        Double rawRating = 0.0;
        if (aggregateRating != null){
            rawRating = Double.parseDouble((String) aggregateRating.get("ratingValue"));
        }

        JSONObject jsonOut = new JSONObject();

        jsonOut.put("title", jsonLD.get("name"));
        jsonOut.put("rating", df.format(rawRating));
        jsonOut.put("price", offers.get("price"));
        jsonOut.put("publisher", author.get("name"));
        jsonOut.put("description", jsonLD.get("description"));

        System.out.println(jsonOut.toJSONString());
    }

}
