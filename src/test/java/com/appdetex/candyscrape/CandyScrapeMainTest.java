package com.appdetex.candyscrape;

import com.appdetex.candyscrape.scraper.IScraper;
import com.appdetex.candyscrape.scraper.model.GooglePlayAppModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CandyScrapeMainTest {

    @MockBean
    private IScraper scraper;

    @Autowired
    private CandyScrapeMain app;

    private ByteArrayOutputStream stdOut;

    private final String TARGET = "https://play.google.com/store/apps/details?id=com.jds.batim";
    private final String BAD_TARGET = "https://bae.google.com/some/other/resource/not/mapped";

    @Before
    public void setup() {
        stdOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stdOut));
    }

    @Test
    public void testMainSuccess() throws IOException {
        given(scraper.<GooglePlayAppModel>scrape(TARGET, GooglePlayAppModel.class))
                .willReturn(new GooglePlayAppModel());

        app.run(TARGET);
        assertThat(stdOut.toString().replaceAll("\\r?\\n", ""))
                .isEqualTo("{  \"title\" : null,  \"url\" : null,  \"description\" : null,  \"publisher\" : null,  " +
                        "\"rating\" : null,  \"price\" : null}");
    }

    @Test
    public void testMainUsage() {
        app.run();
        assertThat(stdOut.toString().trim()).isEqualTo("usage: candyscrape-1.0-SNAPSHOT <URL>");
    }

    @Test
    public void testMainBadArgs() {
        app.run(BAD_TARGET, BAD_TARGET, BAD_TARGET);
        assertThat(stdOut.toString().trim()).isEqualTo("usage: candyscrape-1.0-SNAPSHOT <URL>");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMainTargetNotMapped() throws IllegalArgumentException {
        app.run(BAD_TARGET);
    }
}
