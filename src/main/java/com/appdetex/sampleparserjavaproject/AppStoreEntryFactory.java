package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


/**
 * AppStoreEntryFactory
 *
 * Factory class for instantiating instances that implement IAppStoreEntry
 * Can be extended to add support for additional app stores.
 */
public class AppStoreEntryFactory {
    /**
     * Returns an object that implemetns IAppStoreEntry based on the passed in URL
     * @param url The URL to load from
     * @return Instance that implements IAppStoreEntry
     * @throws IOException
     */
    public IAppStoreEntry getInstance(String url) throws IOException {
        return getInstance(url, true);
    }

    public Document loadDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }


    /**
     * Returns an object that implemetns IAppStoreEntry based on the passed in URL
     * @param url The URL to load from
     * @param load True to load from the URL, false to skip the call to loadDocument() (used for testing)
     * @return Instance of an object that implements IAppStoreEntry
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public IAppStoreEntry getInstance(String url, Boolean load) throws IOException, IllegalArgumentException {
        IAppStoreEntry obj = null;
        Document doc = null;

        if (load) {
            doc = this.loadDocument(url);
        }

        // Do instantiation based on URL
        if (url.contains("play.google.com")) {
            obj = new PlayStoreEntry();
        }

        if (obj != null) {
            if (doc != null) {
                obj.parseDocument(doc);
            }
            return obj;
        }
        // Throw this if we can't determine the type.
        throw new IllegalArgumentException("Could not determine type based on url: " + url);
    }
}
