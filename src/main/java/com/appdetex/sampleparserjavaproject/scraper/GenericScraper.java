package com.appdetex.sampleparserjavaproject.scraper;

import com.appdetex.sampleparserjavaproject.util.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Basic Implementation of a scraper
 */
@Slf4j
public class GenericScraper implements HtmlScraper {

    public Document scrapeURL(final String url, HttpMethod method) throws ScrapingException {
        log.info("Attempting to scrape: {}", url);

        try {
            Connection jConn = Jsoup.connect(url)
                    .timeout(3000);

            // Ugly, probably should use predicate if this gets any more complex.
            if (HttpMethod.GET == method) {
                return jConn.get();
            } else if (HttpMethod.POST == method) {
                return jConn.post();
            }

        } catch (final IOException e) {
            throw new ScrapingException(e.getMessage(), e);
        }
        // else
        throw new ScrapingException("Unknown HttpMethod: " + method);
    }

}
