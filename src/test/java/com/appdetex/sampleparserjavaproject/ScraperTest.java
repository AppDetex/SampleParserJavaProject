package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class ScraperTest {

    @Test
    void noItemprop() {
        Scraper s = new Scraper(Jsoup.parse("<html></html>"));
        Assertions.assertNull(s.getForItemprop("key"));
    }

    @Test
    void noContent() {
        Scraper s = new Scraper(Jsoup.parse("<meta itemprop=\"key\">"));
        Assertions.assertEquals("", s.getForItemprop("key"));
    }

    @Test
    void singleItemprop() {
        Scraper s = new Scraper(Jsoup.parse(
            "<meta itemprop=\"key\" content=\"value\">"
        ));
        Assertions.assertEquals("value", s.getForItemprop("key"));
    }

    @Test
    void multipleItemprops() {
        Scraper s = new Scraper(Jsoup.parse(
            "<meta itemprop=\"key\" content=\"value\">" +
            "<meta itemprop=\"key\" content=\"other-value\">"
        ));
        Assertions.assertEquals("value", s.getForItemprop("key"));
    }

    @Test
    void noPublisher() {
        Scraper s = new Scraper(Jsoup.parse("<html></html>"));
        Assertions.assertNull(s.getPublisher());
    }

    @Test
    void emptyPublisher() {
        Scraper s = new Scraper(Jsoup.parse(
            "<html><div>Offered By</div>" +
            "<span></span></html>"));
        Assertions.assertEquals("", s.getPublisher());
    }

    @Test
    void unnestedPublisher() {
        Scraper s = new Scraper(Jsoup.parse(
            "<html><div>Offered By</div>" +
            "<span>publisher</span></html>"
        ));
        Assertions.assertEquals("publisher", s.getPublisher());
    }

    @Test
    void nestedPublisher() {
        Scraper s = new Scraper(Jsoup.parse(
            "<html><div>Offered By</div>" +
            "<span><div><p>publisher</p></div></span></html>"
        ));
        Assertions.assertEquals("publisher", s.getPublisher());
    }
}