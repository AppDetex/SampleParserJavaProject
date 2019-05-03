package com.appdetex.sampleparserjavaproject;

public interface Scraper {

    /**
     * Given a non-empty url, return the html content retrieved at that location, as a String.
     *
     * @param url
     * @return
     */
    String getHtml(String url);
}
