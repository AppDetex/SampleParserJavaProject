package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlayStoreHtmlParserTest {

    @Test
    public void canParseTitleFromHtml() throws IOException {
        PlayStoreHtmlParser playStoreHtmlParser = new PlayStoreHtmlParser();
        Document document = getDocument("weather-forecast-pro.html");
        PlayStoreApp app = playStoreHtmlParser.parse(document);

        assertThat(app.getTitle(), is("Weather Forecast Pro"));
    }

    private Document getDocument(String filename) throws IOException {
        InputStream resource = this.getClass().getResourceAsStream(filename);
        return Jsoup.parse(IOUtils.toString(resource, "UTF-8"));
    }
}