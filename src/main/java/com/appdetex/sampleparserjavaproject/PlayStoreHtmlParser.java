package com.appdetex.sampleparserjavaproject;

import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static com.appdetex.sampleparserjavaproject.PlayStoreElement.DESCRIPTION;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.PRICE;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.PUBLISHER;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.RATING;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.TITLE;

public class PlayStoreHtmlParser {
    public PlayStoreApp parse(Document document) {
        String title = parseTitle(document);
        String description = parseDescription(document);
        String publisher = parsePublisher(document);
        String price = parsePrice(document);
        double rating = parseRating(document);

        return new PlayStoreApp(title, description, publisher, price, rating);
    }

    private double parseRating(Document document) {
        Element ratingElement = extractElement(document, RATING);
        return Double.parseDouble(ratingElement.text());
    }

    private String parsePrice(Document document) {
        Element priceElement = extractElement(document, PRICE);
        String price = priceElement.attr("content");
        if ("0".equals(price)) {
            price = "$0.00";
        }
        return price;
    }

    private String parsePublisher(Document document) {
        Element publisherElement = extractElement(document, PUBLISHER);
        return publisherElement.text();
    }

    private String parseDescription(Document document) {
        Element descriptionElement = extractElement(document, DESCRIPTION);
        String fullDescription = descriptionElement.html();
        return Arrays.stream(fullDescription.split("<br>")).findFirst().orElse("").trim();
    }

    private String parseTitle(Document document) {
        Element nameElement = extractElement(document, TITLE);
        return nameElement.text();
    }

    private Element extractElement(Document document, PlayStoreElement element) {
        return document.select(element.getSelector()).get(0);
    }
}
