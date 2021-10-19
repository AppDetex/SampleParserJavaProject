package com.appdetex.sampleparserjavaproject.coordinator;

import com.appdetex.sampleparserjavaproject.formatter.DataFormatter;
import com.appdetex.sampleparserjavaproject.formatter.FormattingException;
import com.appdetex.sampleparserjavaproject.parser.HtmlParser;
import com.appdetex.sampleparserjavaproject.scraper.HtmlScraper;
import com.appdetex.sampleparserjavaproject.scraper.ScrapingException;
import com.appdetex.sampleparserjavaproject.util.HttpMethod;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;

/**
 * Class that coordinates scraping, parsing, converting and formatting
 */
@Slf4j
@Builder
public class DataMiningCoordinator {

    private final HtmlScraper scraper;
    private final HtmlParser parser;
    private final Function<Map<String, String>, Object> converter; //Can be made optional since formatter can accept output of parser directly
    private final DataFormatter formatter;

    public final String execute(final String url, final HttpMethod method) throws ScrapingException, FormattingException {
        //This might be easier to read if it was flattened out.
        return formatter.format(converter.apply(parser.parseRawDocument(scraper.scrapeURL(url, method))));
    }
}
