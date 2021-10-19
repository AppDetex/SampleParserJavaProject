package com.appdetex.sampleparserjavaproject.parser;

import org.jsoup.nodes.Document;

import java.util.Map;

public interface HtmlParser {
    Map<String, String> parseRawDocument(final Document doc);
}
