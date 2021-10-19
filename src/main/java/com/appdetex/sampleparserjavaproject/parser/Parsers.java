package com.appdetex.sampleparserjavaproject.parser;

import com.appdetex.sampleparserjavaproject.parser.elementhandler.*;

import java.util.Arrays;

/**
 * Class containing factory methods for pre-configured parsers
 */
public class Parsers {

    private Parsers(){}

    public static final HtmlParser GooglePlayStoreParser() {
        return new GenericParser(Arrays.asList(new AppTitleHandler(),
                new FirstParagraphHandler(),
                new PublisherHandler(),
                new PriceHandler(),
                new RatingHandler()
        ));
    }
}
