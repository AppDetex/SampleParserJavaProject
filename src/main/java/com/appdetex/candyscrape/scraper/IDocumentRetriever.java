package com.appdetex.candyscrape.scraper;

import org.jsoup.nodes.Document;

public interface IDocumentRetriever {

    /**
     * Retrieves a Jsoup document given a path.
     *
     * @param path the path. Locator type depends on concrete implementation.
     * @return a Jsoup document on success, null on failure.
     */
    Document fetch(String path);
}
