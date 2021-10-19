package com.appdetex.sampleparserjavaproject.scraper;

import com.appdetex.sampleparserjavaproject.util.HttpMethod;
import org.jsoup.nodes.Document;

public interface HtmlScraper {
    Document scrapeURL(final String url, HttpMethod method) throws ScrapingException;
}
