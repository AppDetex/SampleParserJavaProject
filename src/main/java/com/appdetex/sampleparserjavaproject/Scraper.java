package com.appdetex.sampleparserjavaproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/* Small scraping tool for parsing mobile app information from the Google Play app store's web page. This program accepts
 a URL for a Google Play app page as a command line parameter, and prints out a JSON-formatted string containing relevant
 data for the app. Data includes: app title, first paragraph of the description, publisher name, price, and rating
 (average review score).*/
public class Scraper {

    public static void main( String[] args ) {
        if (args.length == 0 || args.length > 1) {
            System.out.println("Url to scrape is required.  It must be the only argument passed to the application.");
            return;
        }

        //In a real application, of course I'd handle exceptions better.  For instance, creating exceptions specific to
        //the application and logging/printing out nicer error messages.
        try {
            Scraper scraper = new Scraper();
            System.out.println(scraper.scrapeUrl(args[0]));
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private String scrapeUrl(String url) throws IOException {
        Document doc;
        ObjectMapper objectMapper = new ObjectMapper();

        doc = Jsoup.connect(url).get();
        String title = doc.select("div.id-app-title")
                            .first()
                            .text();
        String description = doc.select("div.text-body")
                                .first()
                                .text();
        String publisher = doc.select("a.document-subtitle.primary > span")
                                .first()
                                .text();
        Element priceElement = doc.select("button.price > span:contains(Buy)")
                                    .first();

        String price;
        if (priceElement != null) {
            price = priceElement.text();
            price = price.substring(0, price.indexOf(' '));
        }
        else
            price = "$0.00";

        double rating = Double.parseDouble(doc.select("div.score")
                                                .first()
                                                .text());

        AppDescription appDescription = new AppDescription(title, description, publisher, price, rating);
        return objectMapper.writeValueAsString(appDescription);
    }
}
