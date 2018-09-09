/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author nmousa
 */
public class GooglePlayAppScraper {

    private final String url;
    private final LinkedHashMap<String, Object> appData = new LinkedHashMap<String, Object>();

    public GooglePlayAppScraper(String url) {
        this.url = url;
    }

    /**
     * Parse app title, first paragraph of the description, publisher name,
     * price, and rating
     *
     * @return Stringified JSON
     */
    public String parse() {
        try {
            Document page = Jsoup.connect(url).get();

            String title = parseTitle(page);
            String description = parseDescription(page);
            String publisher = parsePublisher(page);
            String price = parsePrice(page);
            Double rating = parseRating(page);

            appData.put("title", title);
            appData.put("description", description);
            appData.put("publisher", publisher);
            appData.put("price", price);
            appData.put("rating", rating == null ? "" : rating);

        } catch (IOException ex) {
            Logger.getLogger(GooglePlayAppScraper.class.getName()).log(Level.SEVERE, "Something went wrong parsing URL provided", ex);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String appDataJSON = gson.toJson(appData, LinkedHashMap.class);
        return appDataJSON;
    }

    /**
     *
     * @param page
     * @return
     */
    public String parseTitle(Document page) {
        return page.getElementsByAttributeValue("itemprop", "name").text().trim();
    }

    /**
     *
     * @param page
     * @return
     */
    public String parseDescription(Document page) {
        String description = "";

        String html = page.getElementsByAttributeValue("itemprop", "description").html();
        int paragraphStart = html.indexOf("jsname=\"sngebd\"") + 16;
        int paragraphEnd = html.indexOf("<br>");

        if (paragraphStart > 0 && paragraphEnd > 0) {
            description = html.substring(html.indexOf("jsname=\"sngebd\"") + 16, html.indexOf("<br>"));
        }

        return description.trim();
    }

    /**
     *
     * @param page
     * @return
     */
    public String parsePublisher(Document page) {
        Element publisher = page.select("span[class=T32cc UAO9ie]").first();
        if (publisher == null) return "";
        return page.select("span[class=T32cc UAO9ie]").first().text().trim();
    }

    /**
     * Assume currency to be USD
     *
     * @param page
     * @return
     */
    public String parsePrice(Document page) {
        String priceText = page.getElementsByClass("oocvOe").text();

        Pattern pattern = Pattern.compile("(\\$[0-9]+(\\.[0-9]{2})?)");
        Matcher matcher = pattern.matcher(priceText);
        if (matcher.find()) {
            priceText = matcher.group(0);
        } else {
            priceText = "$0.00";
        }
        return priceText;
    }

    /**
     *
     * If there is no rating, I'd return an empty string. - Peter
     *
     * @param page
     * @return
     */
    public Double parseRating(Document page) {
        Double rating = null;

        try {
            String ratingString = page.getElementsByClass("BHMmbe").text();
            if(!ratingString.isEmpty()) rating = Double.parseDouble(ratingString);
        } catch (NullPointerException nullPointerException) {
            // do nothing since rating is already null
        } catch (NumberFormatException numberFormatException) {
            Logger.getLogger(GooglePlayAppScraper.class.getName()).log(Level.SEVERE, "Unable to parse rating value", numberFormatException);
        }
        return rating;
    }

}
