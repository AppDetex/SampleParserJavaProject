package com.appdetex.sampleparserjavaproject.parsing;

import com.appdetex.sampleparserjavaproject.parsing.stores.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebParsingService {

    private final StoreResolver storeResolver;
    private final DocumentParserBuilder documentParserBuilder;
    private final SelectKeysBuilder selectKeysBuilder;

    public ParsedInfo parseWebsite(ParseQuery parseQuery) throws IOException {
        Document doc = Jsoup.connect(parseQuery.getWebsiteUrl()).get();

        StoreEnum storeEnum = storeResolver.resolveStore(parseQuery.getWebsiteUrl());
        SelectKeys selectKeys = selectKeysBuilder.buildSelectKeys(storeEnum);
        DocumentParser documentParser = documentParserBuilder.buildDocumentParser(storeEnum);

        return documentParser.parseDocument(doc, selectKeys);
    }

}