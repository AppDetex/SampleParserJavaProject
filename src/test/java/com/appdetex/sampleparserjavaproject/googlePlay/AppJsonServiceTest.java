package com.appdetex.sampleparserjavaproject.googlePlay;

import com.appdetex.sampleparserjavaproject.Parser;
import com.appdetex.sampleparserjavaproject.Scraper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

@ExtendWith(MockitoExtension.class)
class AppJsonServiceTest {
    @Mock
    private Scraper scraper;

    @Mock
    private Parser<AppData> parser;

    @InjectMocks
    private AppJsonService appJsonService;

    @Test
    void getJson_allFieldsFilled() throws JSONException {
        when(scraper.getHtml("input")).thenReturn("test");
        when(parser.parse("test")).thenReturn(
                new AppData("title", "description", "publisher", "price", 1.23f));

        JSONAssert.assertEquals(
                "{\"title\":\"title\",\"description\":\"description\",\"publisher\":\"publisher\",\"price\":\"price\",\"rating\":1.23}",
                appJsonService.getJson("input"),
                JSONCompareMode.STRICT);
    }


    @Test
    void getJson_nullFields() throws JSONException {
        when(scraper.getHtml("input")).thenReturn("test");
        when(parser.parse("test")).thenReturn(
                new AppData("title", null, "publisher", null, 1.23f));

        JSONAssert.assertEquals(
                "{\"title\":\"title\",\"publisher\":\"publisher\",\"rating\":1.23}",
                appJsonService.getJson("input"),
                JSONCompareMode.STRICT);
    }
}
