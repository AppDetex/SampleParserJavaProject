package com.appdetex.sampleparserjavaproject.parser;

import com.appdetex.sampleparserjavaproject.parser.elementhandler.ElementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class GenericParser implements HtmlParser {

    private final List<ElementHandler> handlers;

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
