package com.appdetex.sampleparserjavaproject.parsing.stores.apple;

import com.appdetex.sampleparserjavaproject.parsing.ParsedInfo;
import com.appdetex.sampleparserjavaproject.parsing.stores.DocumentParser;
import com.appdetex.sampleparserjavaproject.parsing.stores.SelectKeys;
import org.jsoup.nodes.Document;

public class AppleStoreDocumentParser implements DocumentParser {
    public ParsedInfo parseDocument(Document document, SelectKeys selectKeys) {
        return ParsedInfo.builder().build();
    }
}
