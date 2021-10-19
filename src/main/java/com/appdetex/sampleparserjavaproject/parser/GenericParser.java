package com.appdetex.sampleparserjavaproject.parser;

import com.appdetex.sampleparserjavaproject.dto.GooglePlayStorePageSummaryDto;
import com.appdetex.sampleparserjavaproject.parser.elementhandler.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GooglePlayStoreParser implements HtmlParser {

    private final List<ElementHandler> handlers = new ArrayList<>();

    public GooglePlayStoreParser() {
        handlers.add(new AppTitleHandler());
        handlers.add(new FirstParagraphHandler());
        handlers.add(new PublisherHandler());
        handlers.add(new PriceHandler());
        handlers.add(new RatingHandler());
    }

    private final AppTitleHandler appTitleHandler = new AppTitleHandler();
    private final FirstParagraphHandler firstParagraphHandler = new FirstParagraphHandler();
    private final PublisherHandler publisherHandler = new PublisherHandler();
    private final PriceHandler priceHandler = new PriceHandler();
    private final RatingHandler ratingHandler = new RatingHandler();

    /**
     * Parse a document obtained from a Google PlayStore page and extract
     * desired elements.
     * @param doc
     * @return
     */
    public Map<String, String> parseRawDocument(final Document doc) {

        final Map<String, String> outputData = new HashMap<>();

        for (final ElementHandler eh : this.handlers) {
            Elements els = eh.seek(doc);
            eh.parse(els, outputData);
        }

        return outputData;
    }
}
