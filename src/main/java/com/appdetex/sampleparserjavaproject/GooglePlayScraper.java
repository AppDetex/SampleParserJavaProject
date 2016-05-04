package com.appdetex.sampleparserjavaproject;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Parses and stores information from a Google Play Store web page in a JSON format.
 */
public class GooglePlayScraper {

	private Document doc;
    private JSONObject data = new JSONObject();

	public GooglePlayScraper(String url)	{
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("Failed to find specified URL");
            e.printStackTrace();
        }
        data.put("title", getAppTitle());
        data.put("description", getAppParagraph());
        data.put("publisher", getAppPublisher());
        data.put("price", getAppPrice());
        data.put("rating", getAppRating());

	}

    private String getAppTitle()  {
        Elements appTitle = doc.select(".id-app-title");
        return appTitle.text();
    }

    private double getAppRating()  {
        Elements rating = doc.select(".star-rating-non-editable-container");
        String[] ratingWords = rating.attr("aria-label").split(" ");
        String appRating = ratingWords[2]; //2 is the position of the numerical rating inside the aria label
        return Double.parseDouble(appRating);
    }

    private String getAppParagraph() {
        Elements description = doc.getElementsByAttributeValue("itemprop", "description").select("div > div");
        description.select("p").remove();
        return description.text();
    }

    private String getAppPublisher() {
        Elements publisher = doc.getElementsByAttributeValue("itemprop", "author");
        publisher = publisher.select("[itemprop=name]");
        return publisher.text();
    }

    private String getAppPrice() {
        Elements price = doc.getElementsByAttributeValue("itemprop", "price");
        return price.attr("content");
    }

    public JSONObject getJSON() {
       return data;
    }

}
