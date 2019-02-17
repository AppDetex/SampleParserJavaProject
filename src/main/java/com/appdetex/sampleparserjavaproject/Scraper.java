package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Selector.SelectorParseException;

/**
 * Provides untily to scrap a Play Store app details page for data.
 */
public final class Scraper {

    private static final int TIMEOUT = 30000;
    private static final String CONTENT_ATTR = "content";

    private Scraper() { }

    /**
     * Retreives the html at the given URL and parses it.
     * @param url url address of App in Play Store
     * @return Application details
     */
    public static AppDetails parse(URL url) throws ScraperException {

        try {

            Document doc = Jsoup.parse(url, TIMEOUT);

            // <meta itemprop="name" content="Minecraft">
            String title = doc.selectFirst("meta[itemprop=name]").attr(CONTENT_ATTR);

            // <meta itemprop="price" content="$6.99">
            String price = doc.selectFirst("meta[itemprop=price]").attr(CONTENT_ATTR);

            if ("0".equals(price)) {
                price = "$0.00";
            }

            // <meta itemprop="ratingValue" content="4.505034446716309">
            String ratingValue = doc.selectFirst("meta[itemprop=ratingValue]").attr(CONTENT_ATTR);
            BigDecimal rating = new BigDecimal(ratingValue);
            rating = rating.setScale(2, RoundingMode.DOWN); // Play store rounds ratings down

            // <meta itemprop="description" content="Explore infinite worlds...
            String description = doc.selectFirst("meta[itemprop=description]").attr(CONTENT_ATTR).split("\n")[0];

            // <a href="https://play.google.com/store/apps/developer?id=Mojang"
            String publisher = doc.selectFirst("a[href*=developer?id]").text();

            return new AppDetails(title, description, publisher, price, rating);

        } catch (IOException ex) {
            throw new ScraperException("Problem retrieving html", ex);
        } catch (SelectorParseException | NullPointerException ex) {
            throw new ScraperException("Problem parsing html", ex);
        }

    }

}
