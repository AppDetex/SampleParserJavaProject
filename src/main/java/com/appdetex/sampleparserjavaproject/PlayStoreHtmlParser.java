package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.appdetex.sampleparserjavaproject.PlayStoreElement.TITLE;

public class PlayStoreHtmlParser {
    public PlayStoreApp parse(Document document) {
        Elements nameElements = document.select(TITLE.getSelector());
        String title = nameElements.get(0).text();

        return new PlayStoreApp(title);
    }
}
