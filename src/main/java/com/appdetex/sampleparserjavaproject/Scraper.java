package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class Scraper {
    private Document doc;

    Scraper(Document d) {
        doc = d;
    }

    String getForItemprop(String key) {
        Element element = doc.select(String.format("meta[itemprop=\"%s\"]", key)).first();

        if (element == null) {
            return null;
        }

        return element.attr("content");
    }

    private String getInnerMostText(Element element) {
        if (element.children().size() == 0) {
            return element.text();
        }

        return getInnerMostText(element.child(0));
    }

    String getPublisher() {
        Element span = doc.select("div:matchesOwn(Offered By) + span").first();

        if (span == null) {
            return null;
        }

        return getInnerMostText(span);
    }
}
