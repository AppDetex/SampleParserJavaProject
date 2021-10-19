package com.appdetex.sampleparserjavaproject.parser;

import com.appdetex.sampleparserjavaproject.parser.elementhandler.*;

import java.util.Arrays;

public class GooglePlayStoreParser extends GenericParser {

    public GooglePlayStoreParser() {
        super(Arrays.asList(new AppTitleHandler(),
                new FirstParagraphHandler(),
                new PublisherHandler(),
                new PriceHandler(),
                new RatingHandler()
        ));
    }
}
