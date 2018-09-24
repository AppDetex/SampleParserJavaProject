package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class Scraper {
    private Document page;

    Scraper(Document page) {
        this.page = page;
    }

    String getForItemprop(String key) {
        Element element = page.select(String.format("meta[itemprop=\"%s\"]", key)).first();

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
        Element span = page.select("div:matchesOwn(Offered By) + span").first();

        if (span == null) {
            return null;
        }

        return getInnerMostText(span);
    }
}
