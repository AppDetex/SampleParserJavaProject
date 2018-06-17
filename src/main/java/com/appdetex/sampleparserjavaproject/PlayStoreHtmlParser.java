package com.appdetex.sampleparserjavaproject;

import java.util.Arrays;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.appdetex.sampleparserjavaproject.PlayStoreElement.DESCRIPTION;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.PRICE;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.PUBLISHER;
import static com.appdetex.sampleparserjavaproject.PlayStoreElement.TITLE;

public class PlayStoreHtmlParser {
    public PlayStoreApp parse(Document document) {
        Elements nameElements = document.select(TITLE.getSelector());
        String title = nameElements.get(0).text();

        Elements descriptionElements = document.select(DESCRIPTION.getSelector());
        String fullDescription = descriptionElements.get(0).html();
        String description = Arrays.stream(fullDescription.split("\\n<br>")).findFirst().orElse("");

        Elements publisherElements = document.select(PUBLISHER.getSelector());
        String publisher = publisherElements.get(0).text();

        Elements priceElements = document.select(PRICE.getSelector());
        String price = priceElements.text();

        return new PlayStoreApp(title, description, publisher, price);
    }
}
