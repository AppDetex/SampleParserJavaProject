package com.appdetex.candyscrape.scraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


/**
 * Class used for retrieving a Jsoup document via HTTP.
 */
@Slf4j
public class HttpDocumentRetriever implements IDocumentRetriever {

    /**
     * @inheritDoc
     *
     * Path represents a fully formed URL locator.
     */
    public Document fetch(String path) {
        try {
            return Jsoup.connect(path).get();
        } catch (IOException e) {
            log.error("Failed to retrieve document from google play store: ", e);
            return null;
        }
    }
}