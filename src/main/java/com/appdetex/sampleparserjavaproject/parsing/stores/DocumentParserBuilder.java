package com.appdetex.sampleparserjavaproject.parsing.stores;

import com.appdetex.sampleparserjavaproject.parsing.stores.apple.AppleStoreDocumentParser;
import com.appdetex.sampleparserjavaproject.parsing.stores.google.GooglePlayDocumentParser;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
public class DocumentParserBuilder {
    public DocumentParser buildDocumentParser(StoreEnum storeEnum) {
        switch(storeEnum) {
            case GOOGLE_PLAY:
                return new GooglePlayDocumentParser();
            case APPLE:
                return new AppleStoreDocumentParser();
            default:
                throw new NotImplementedException();
        }
    }
}
