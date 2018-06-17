package com.appdetex.sampleparserjavaproject;

public enum PlayStoreElement {
    TITLE("h1[class=AHFaub][itemprop=name] > span"),
    DESCRIPTION("div[class=DWPxHb][itemprop=description] > content > div"),
    PUBLISHER("a.hrTbp.R8zArc"),
    PRICE("span.LV0gI"),
    RATING("div.BHMmbe")
    ;

    private final String selector;

    PlayStoreElement(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
