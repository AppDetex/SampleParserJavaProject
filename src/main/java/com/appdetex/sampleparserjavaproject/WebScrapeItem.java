package com.appdetex.sampleparserjavaproject;

import java.util.ArrayList;

public class WebScrapeItem {
    private int textOffset;
    private ArrayList<String> selects;

    public int getTextOffset() {
        return textOffset;
    }

    public ArrayList<String> getSelects() {
        return selects;
    }

    @Override
    public String toString() {
        return "WebScrapeItem{" +
                "textOffset=" + textOffset +
                ", selects=" + selects +
                '}';
    }
}
