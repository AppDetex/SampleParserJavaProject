package com.appdetex.sampleparserjavaproject.client;

import com.appdetex.sampleparserjavaproject.domain.ScraperResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class WebClient {

    private final Document doc;

    public WebClient(String url) throws IOException {
        doc = Jsoup.connect(url).get();
    }

    public ScraperResponse getScraperResponse() {
        return new ScraperResponse() {
            {
                // extract title is based on class name
                title = extractValue(
                        findElements("AHFaub", "span")
                ).trim();

                // extract description is based on attribute
                description = extractValue(doc, "jsname", "sngebd")
                        .findFirst().orElse("").trim();

                // extract publisher is based on class name
                publisher = extractValue(doc, "hrTbp R8zArc").trim();

                // extract price is based on attribute and
                // additionally filter by "price" in description text
                // might be not applicable for some links
                price = extractValue(doc, "jsname", "sngebd")
                        .filter(s -> s.contains("price")).findFirst().orElse("")
                        // that price potentially should be
                        // normalized better way (add $ or trim any [.] out of price
                        .replaceAll("[^0-9?!.]", "");

                rating = extractValue(doc, "BHMmbe");
            }
        };
    }

    /*
    parser utils
     */
    private Stream<String> extractValue(Document doc, String attributeName, String attributeValue) {
        return Objects.requireNonNull(doc
                        .getElementsByAttributeValue(attributeName, attributeValue)
                        .first())
                .firstElementSibling()
                .html()
                .lines();
    }

    private Element findElement(Elements select) {
        return Objects.requireNonNull(select.first());
    }

    private Elements findElements(String className, String tag){
        return doc.getElementsByClass(className).select(tag);
    }

    private String extractValue(Elements select) {
        return findElement(select).text();
    }

    private String extractValue(Document doc, String className) {
        return extractValue(doc.getElementsByClass(className));
    }
}
