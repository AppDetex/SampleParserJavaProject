package com.appdetex.candyscrape.scraper.model;

import com.appdetex.candyscrape.meta.CssSelectorPath;
import lombok.Data;

/**
 * Data model for a Google Play Store app.
 */
@Data
public class GooglePlayAppModel {

    @CssSelectorPath(value="meta[itemprop=\"name\"]", attribute="content")
    private String title;

    @CssSelectorPath(value="div[itemscope] meta[itemprop=\"url\"]", attribute="content")
    private String url;

    @CssSelectorPath(
            value="div[itemscope] meta[itemprop=\"description\"]",
            attribute="content",
            regex="^(.*?)\\n"
    )
    private String description;

    @CssSelectorPath("a[href^=\"https://play.google.com/store/apps/developer?id=\"]:not(:has(div))")
    private String publisher;

    @CssSelectorPath("c-wiz div[aria-label^=\"Rated \"]:not(:has(div))")
    private Float rating;

    @CssSelectorPath(value="button meta[itemprop=\"price\"]", attribute="content")
    private String price;
}
