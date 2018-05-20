package com.appdetex.sampleparserjavaproject.parsing.stores.google;

import com.appdetex.sampleparserjavaproject.parsing.ParsedInfo;
import com.appdetex.sampleparserjavaproject.parsing.stores.DocumentParser;
import com.appdetex.sampleparserjavaproject.parsing.stores.SelectKeys;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;

public class GooglePlayDocumentParser implements DocumentParser {
    public ParsedInfo parseDocument(Document document, SelectKeys selectKeys) {
        Element titleElement = document.select(selectKeys.getTitleKey()).first();
        Element descriptionElement = document.select(selectKeys.getDescriptionKey()).first();
        Element publisherElement = document.select(selectKeys.getPublisherKey()).first();
        Element priceElement = document.select(selectKeys.getPriceKey()).first();
        Element ratingElement = document.select(selectKeys.getRatingKey()).first();

        String price = priceElement.attr("content").equals("0") ? "FREE!" : priceElement.attr("content");
        BigDecimal rating = new BigDecimal(ratingElement.text());

        return ParsedInfo.builder()
                .title(titleElement.text())
                .description(descriptionElement.text())
                .publisher(publisherElement.text())
                .price(price)
                .rating(rating)
                .build();
    }
}
