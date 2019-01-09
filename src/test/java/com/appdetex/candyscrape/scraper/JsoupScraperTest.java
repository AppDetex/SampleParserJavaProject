package com.appdetex.candyscrape.scraper;

import com.appdetex.candyscrape.scraper.model.TestModel;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@RunWith(SpringRunner.class)
public class JsoupScraperTest {

    @Value("classpath:test.html")
    private Resource testHtmlResource;

    @Autowired
    private JsoupScraper scraper;

    @MockBean
    private IDocumentRetriever documentRetriever;

    private String testHtml;

    @Before
    public void setup() throws IOException {
        testHtml = new String(FileCopyUtils.copyToByteArray(testHtmlResource.getInputStream()), StandardCharsets.UTF_8);
        given(documentRetriever.fetch(anyString())).willReturn(Jsoup.parse(testHtml));
    }

    @Test
    public void testScrapeAttribute() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getCharset()).isEqualTo("UTF-8");
    }

    @Test
    public void testScrapeInnerText() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getInnerText()).isEqualTo("inner text");
    }

    @Test
    public void testScrapePseudoMatchers() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getOff()).isEqualTo("off");
        assertThat(m.getOn()).isEqualTo("on");
    }

    @Test
    public void testScrapeNumeric() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getNumeric()).isEqualTo(123.333f);
    }

    @Test
    public void testScrapeRegex() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getRegexSimple()).isEqualTo("555");
    }

    @Test
    public void testScrapeRegexNamed() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getRegexNamed()).isEqualTo("0199");
    }

    @Test
    public void testScrapeDefault() {
        TestModel m = scraper.scrape("", TestModel.class);
        assertThat(m.getDefaulted()).isEqualTo("defaulted");
    }
}
