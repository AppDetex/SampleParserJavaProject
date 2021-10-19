package com.appdetex.sampleparserjavaproject.parser.selectors;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class AppTitleSelector implements ElementSelector {
    @Override
    public String seek(Document doc) {
        // Seek 'app title' - appears to be nested in a span in an h1 with a itemprop attr
        Elements els = doc.select("h1[itemprop=name] > span");
        log.debug("Found {} 'app title' elements", els.size());
        return els.stream()
                .map(Element::text)
                .findFirst()
                .orElse("NONE FOUND");
    }
}
