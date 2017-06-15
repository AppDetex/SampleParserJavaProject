package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;

/**
 * IAppStoreEntry
 *
 * Interface for an object representing an object on an AppStore
 * Used to implement more complicated stores. In practice you should just extend from AppStoreBase
 */
public interface IAppStoreEntry {
    /**
     * Parses a JSoup document to populate the variables backing the getter functions.
     * @param doc The document to parse.
     */
    void parseDocument(Document doc);

    String getTitle();
    String getDescription();
    String getAuthor();
    String getPrice();
    String getRating();
    String toJSON();
}
