package com.appdetex.sampleparserjavaproject.utils;

public final class Constants {
    
    public final static String MISSING_URL="Please provide a URL as a command-line parameter when starting this application.";
    public final static String INVALID_URL="Sorry, but the URL you provided is not valid.";
    public final static String EXPR_URL = "^(https?://play.google.com/store/apps/details\\?id=)(.*)$";
    public final static String NOT_A_PLAY_STORE_URL = "The URL provided does not point to the Google Play Store.";
    public final static String SELECTOR_TITLE           = "meta[itemprop=name]";
    public final static String SELECTOR_DESCRIPTION     = "meta[itemprop=description]";
    /**
     * The following selector is named appropriately, even though there aren't any requirements to retrive the "Genre".
     * This selector is used to find a close relative to the element containing the Publisher name.
     */
    public final static String SELECTOR_GENRE           = "a[itemprop=genre]";
    public final static String SELECTOR_PRICE           = "meta[itemprop=price]";
    public final static String SELECTOR_RATING          = "meta[itemprop=ratingValue]";
    public final static String SELECTOR_ATTR_CONTENT    = "content";
}
