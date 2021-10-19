package com.appdetex.sampleparserjavaproject.parser.elementhandler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class AppTitleHandler implements ElementHandler {
    @Override
    public Elements seek(Document doc) {
        // Seek 'app title' - appears to be nested in a span in an h1 with a itemprop attr
        return doc.select("h1[itemprop=name] > span");
    }

    @Override
    public String parse(Elements els) {
        return els.stream()
                .map(Element::text)
                .findFirst()
                .orElse("NONE FOUND");
    }
}
