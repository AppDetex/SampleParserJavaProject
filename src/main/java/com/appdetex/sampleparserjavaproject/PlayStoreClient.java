package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import org.jsoup.Jsoup;

public class PlayStoreClient {
    private final PlayStoreHtmlParser playStoreHtmlParser;

    public PlayStoreClient(PlayStoreHtmlParser playStoreHtmlParser) {
        this.playStoreHtmlParser = playStoreHtmlParser;
    }

    public PlayStoreApp get(String url) throws IOException {
        return playStoreHtmlParser.parse(Jsoup.connect(url).get());
    }
}
