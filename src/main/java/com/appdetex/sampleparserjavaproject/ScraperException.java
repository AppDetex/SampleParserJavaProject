package com.appdetex.sampleparserjavaproject;

/**
 * Represents exception rasied during retrieving or parsing of Play Store application details page.
 */
public class ScraperException extends Throwable {
    private static final long serialVersionUID = -8029927962247905340L;

    public ScraperException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
