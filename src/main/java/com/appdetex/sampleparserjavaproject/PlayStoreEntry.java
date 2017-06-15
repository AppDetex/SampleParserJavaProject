package com.appdetex.sampleparserjavaproject;

import org.jsoup.nodes.Document;


/**
 * PlayStoreEntry
 * Implementation of AppStoreBase for the Google Play store
 */
public class PlayStoreEntry extends AppStoreBase {
    /**
     * Parses the JSoup document and populates the backing variables
     * @param doc The JSoup document to parse
     */
   @Override
   public void parseDocument(Document doc) {
       this.title = doc.select(".id-app-title").first().text();
       this.author = doc.select("div[itemprop=\"author\"] a span[itemprop=\"name\"]").first().text();
       this.description = doc.select("div.text-body div").first().text();
       // This seemed like the best place to get this information.
       this.price = doc.select("span[itemprop=\"offers\"] meta[itemprop=\"price\"]").first().attr("content");
       this.rating = doc.select("div.score").first().text();
   }
}
