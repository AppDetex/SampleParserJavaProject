package com.appdetex.sampleparserjavaproject.parsing.stores;

import com.appdetex.sampleparserjavaproject.parsing.ParsedInfo;
import org.jsoup.nodes.Document;

public interface DocumentParser {
    ParsedInfo parseDocument(Document document, SelectKeys selectKeys);
}
