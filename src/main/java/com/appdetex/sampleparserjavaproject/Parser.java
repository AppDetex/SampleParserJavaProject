package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Will parse data from a google play web page using Jsoup
 */
public class Parser {

    private static final String TITLE_SELECTOR = ".id-app-title";
    private static final String DESCRIPTION_SELECTOR = "[jsname=C4s9Ed]";
    private static final String PUBLISHER_SELECTOR = ".document-subtitle.primary";
    private static final String PRICE_SELECTOR = ".details-actions-right";
    private static final String RATING_SELECTOR = ".score";

    private Document document;

    public Parser(Document document) throws IOException {
        this.document = document;
    }

    public AppData parse() {
        AppData parsedData = new AppData(
                parseTitle(),
                parseDescription(),
                parsePublisher(),
                parsePrice(),
                parseRating());
        return parsedData;
    }

    /**
     * Parse the title from the html document
     *
     * @return The title as a String
     */
    private String parseTitle() {
        String title = null;
        Element titleElement = document.select(TITLE_SELECTOR).first();

        //Get the text from title element
        if (titleElement != null && titleElement.hasText()) {
            title = titleElement.text();
        }

        return title;
    }

    /**
     * Parse the first paragraph from the html document
     *
     * @return The first paragraph as a String
     */
    private String parseDescription() {
        String description = null;
        Element descriptionElement = document.select(DESCRIPTION_SELECTOR).first();

        // Choose the text from the first element of the description excluding the following <p> tags
        if (descriptionElement != null && descriptionElement.hasText()) {
            description = descriptionElement.textNodes().get(0).text().trim();
        }

        return description;
    }

    /**
     * Parse the publisher from the html document
     *
     * @return The publisher as a String
     */
    private String parsePublisher() {
        String publisher = null;
        Element publisherElement = document.select(PUBLISHER_SELECTOR).first();

        //Get the text from publisher element
        if (publisherElement != null && publisherElement.hasText()) {
            publisher = publisherElement.text();
        }

        return publisher;
    }

    /**
     * Parse the price from the html document
     *
     * @return The price as a String or Free if no price
     */
    private String parsePrice() {
        String price = null;
        Element priceElementContainer = document.select(PRICE_SELECTOR).first();

        if (priceElementContainer != null) {
            Elements spans = priceElementContainer.select("span");
            // The last span has the text (at the time of writing) but that isn't very safe so lets filter the list
            for (Element element : spans) {
                if (element.hasText() && element.text().startsWith("$")) {
                    price = element.text();
                    break;
                }
            }

            // If the price string is "Install" then change it to "Free"
            // if the string contains "Buy" parse out the price
            // else leave the empty string
            if (price != null && !price.isEmpty() && price.contains("Buy")){
                // price string comes in the format "$x.xx Buy"
                // splitting on a space to separate the price
                price = price.split(" ")[0];
            } else {
                price = "Free";
            }
        }



        return price;
    }

    /**
     * Parse the app average rating from the html document
     *
     * @return The rating as a float
     */
    private float parseRating() {
        String rating = "0.0";
        Element ratingElement = document.select(RATING_SELECTOR).first();

        //Get the text from title element
        if (ratingElement != null && ratingElement.hasText()) {
            rating = ratingElement.text();
        }

        return Float.parseFloat(rating);
    }
}
