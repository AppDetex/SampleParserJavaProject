package com.appdetex.candyscrape.scraper;

import java.io.IOException;

public interface IScraper {
    <T> T scrape(String targetUrl, Class<T> modelClass) throws IOException;
}
