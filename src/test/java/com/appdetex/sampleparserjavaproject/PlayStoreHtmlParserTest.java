package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class PlayStoreHtmlParserTest {
    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(
                new Object[]{"weather-forecast-pro.html", new PlayStoreApp("Weather Forecast Pro", "This is pro version that does not contain ads and has premium support.", "Best App - Top Droid Team", "$0.00", 4.7)},
                new Object[]{"tower-of-infinity-vip.html", new PlayStoreApp("Tower of Infinity VIP", "■ VIP version benefits: Reward 300 gems + 300 rebrith + Remove benner.", "DAERISOFT", "$0.99", 4.0)}
        );
    }

    private final String filename;
    private final PlayStoreApp expectedApp;

    public PlayStoreHtmlParserTest(String filename, PlayStoreApp expectedApp) {
        this.filename = filename;
        this.expectedApp = expectedApp;
    }

    @Test
    public void canParseAppFromHtml() throws IOException {
        PlayStoreHtmlParser playStoreHtmlParser = new PlayStoreHtmlParser();
        Document document = getDocument(filename);
        PlayStoreApp app = playStoreHtmlParser.parse(document);

        assertThat(app, is(expectedApp));
    }

    private Document getDocument(String filename) throws IOException {
        InputStream resource = this.getClass().getResourceAsStream(filename);
        return Jsoup.parse(IOUtils.toString(resource, "UTF-8"));
    }
}