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
    private TestModel testModel;

    @Before
    public void setup() throws IOException {
        testHtml = new String(FileCopyUtils.copyToByteArray(testHtmlResource.getInputStream()), StandardCharsets.UTF_8);
        given(documentRetriever.fetch(anyString())).willReturn(Jsoup.parse(testHtml));
        testModel = scraper.scrape("", TestModel.class);
    }

    @Test
    public void testScrapeAttribute() {
        assertThat(testModel.getCharset()).isEqualTo("UTF-8");
    }

    @Test
    public void testScrapeInnerText() {
        assertThat(testModel.getInnerText()).isEqualTo("inner text");
    }

    @Test
    public void testScrapePseudoMatchers() {
        assertThat(testModel.getOff()).isEqualTo("off");
        assertThat(testModel.getOn()).isEqualTo("on");
    }

    @Test
    public void testScrapeNumeric() {
        assertThat(testModel.getNumeric()).isEqualTo(123.333f);
    }

    @Test
    public void testScrapeRegex() {
        assertThat(testModel.getRegexSimple()).isEqualTo("555");
    }

    @Test
    public void testScrapeRegexNamed() {
        assertThat(testModel.getRegexNamed()).isEqualTo("0199");
    }

    @Test
    public void testScrapeDefault() {
        assertThat(testModel.getDefaulted()).isEqualTo("defaulted");
    }
}
