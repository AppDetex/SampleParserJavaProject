package com.appdetex.sampleparserjavaproject.parser.elementhandler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

/**
 * Interface for element handler.  Keep data seeking and data parsing separate for testability
 */
public interface ElementHandler {
    Elements seek(final Document doc);
    String parse(final Elements els, final Map<String, String> outputData);
}
