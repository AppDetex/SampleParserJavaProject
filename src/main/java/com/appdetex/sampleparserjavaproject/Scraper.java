package com.appdetex.sampleparserjavaproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;


public class Scraper implements Callable<String> {
    private String url;
    private Map<String, String> model;

    public Scraper (String url, Map<String, String> model) {
        this.url = url;
        this.model = model;
    }

    @Override
    public String call() throws IOException, IllegalStateException {
        Document doc = Jsoup.connect(url).get();

        Element titleElement = doc.select(model.get("title")).first();
        Element publisherElement = doc.select(model.get("publisher")).first();
        Element descriptionElement = doc.select(model.get("description")).first();
        Element ratingElement = doc.select(model.get("rating")).first();
        Element priceElement = doc.select(model.get("price")).first();

        if (titleElement == null) {
            throw new IllegalStateException("Not title information. Wrong website model?");
        }

        ScraperData data = new ScraperData(
                titleElement.text(),
                publisherElement.text(),
                ratingElement.textNodes().get(0).text(),
                priceElement.textNodes().get(0).text(),
                descriptionElement.childNode(0).toString());

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(data);
    }
}
