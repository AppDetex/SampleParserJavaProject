package com.appdetex.sampleparserjavaproject;

import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.appdetex.sampleparserjavaproject.PlayStoreElement.DESCRIPTION;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.PRICE;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.PUBLISHER;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.RATING;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.TITLE;

public class PlayStoreHtmlParser {
    public PlayStoreApp parse(Document document) {
        Elements nameElements = document.select(TITLE.getSelector());
        String title = nameElements.get(0).text();

        Elements descriptionElements = document.select(DESCRIPTION.getSelector());
        String fullDescription = descriptionElements.get(0).html();
        String description = Arrays.stream(fullDescription.split("<br>")).findFirst().orElse("").trim();

        Elements publisherElements = document.select(PUBLISHER.getSelector());
        String publisher = publisherElements.get(0).text();

        Elements priceElements = document.select(PRICE.getSelector());
        String price = priceElements.get(0).attr("content");
        if ("0".equals(price)) {
            price = "$0.00";
        }

        Elements ratingElements = document.select(RATING.getSelector());
        double rating = Double.parseDouble(ratingElements.text());

        return new PlayStoreApp(title, description, publisher, price, rating);
    }
}
