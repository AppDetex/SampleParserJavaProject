package com.appdetex.sampleparserjavaproject;

public enum PlayStoreElement {
    TITLE("h1[class=AHFaub][itemprop=name] > span");

    private final String selector;

    PlayStoreElement(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}
