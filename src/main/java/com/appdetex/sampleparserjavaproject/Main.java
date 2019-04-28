package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 *
 * <p>
 * Note: Since this challenge was fairly small everything was placed within the main class for simplicity. Given more
 * advanced or production like requirements, the following changes would be desired:
 *
 * <p><ul>
 * <li> A standard parse interface that could be implemented by classes for handling different "types" of URL
 *      parsing. eg, a Google Play App store class could be created implementing the standard parse interface to handle
 *      parsing specific details unique to Google Play App store URLs.
 * <li> There are likely performance inadequacies with the current Jsoup interaction as the entire document gets
 *      searched every time when looking for content. Ideally the search would be more structured such that it would
 *      minimize duplication of work.
 * <li> A Java JSON library for handling more complex JSON interactions.
 * <li> The parsing functions themselves should be decoupled from the JSON key:value data store format. ie, the parsing
 *      classes should only provide parsing functions. These functions should then be mapped/associated onto the desired
 *      data store format.
 * </ul><p>
 * </p>
 */
public class Main {

    /**
     * The coding challenge main method. Takes command line argument strings containing Google Play App store URLs and
     * prints the parsed result as a formatted JSON output string.
     *
     * @param args The command line argument strings array containing the Google Play App store URLs to be parsed.
     */
    public static void main( String[] args ) {
        // Loop through each CLI URL argument and print the parsed formatted JSON output
        for (int urlArgIndex = 0; urlArgIndex < args.length; urlArgIndex++) {
            System.out.println(Main.scanGooglePlayUrl(args[urlArgIndex]));
        }
    }

    /**
     * Scan a Google Play App URL and return a JSON formatted string with the following entries:
     *
     * <p><ul>
     * <li> title       - The App title.
     * <li> publisher   - The App publisher.
     * <li> description - The App description.
     * <li> price       - The App price.
     * <li> rating      - The App rating.
     * </ul><p>
     *
     * @param url The Google Play URL to scan.
     *
     * @return A JSON formatted string of the parsed Google Play URL. Empty string is returned on error.
     */
    public static String scanGooglePlayUrl(String url) {
        // Initialize as empty string to return if error is encountered
        String jsonString = "";

        try {
            // The only exception we should encounter is from Jsoup parsing the URL
            Document doc = Jsoup.connect(url).get();

            // Create a simple JSON string of the parsed document
            jsonString += "{\n";
            jsonString += String.format("    \"title\": \"%s\",\n",       Main.docParseTitle(doc));
            jsonString += String.format("    \"publisher\": \"%s\",\n",   Main.docParsePublisher(doc));
            jsonString += String.format("    \"description\": \"%s\",\n", Main.docParseDescription(doc));
            jsonString += String.format("    \"price\": \"%s\",\n",       Main.docParsePrice(doc));
            jsonString += String.format("    \"rating\": %s\n",           Main.docParseRating(doc));
            jsonString += "}";
        }
        catch (Exception e) {
            System.out.println("Error Accessing URL: " + e.getMessage());
        }

        return jsonString;
    }

    /**
     * Get the Google Play App title from a Jsoup document.
     *
     * @param doc The Jsoup document.
     *
     * @return A string of the Google Play App title. Empty string is returned if not found.
     */
    public static String docParseTitle(Document doc) {
        Elements elements = doc.select("meta[itemprop=name]");
        return (elements.isEmpty()) ? "" : elements.first().attr("content").toString().replaceAll("[\\n\\r]", "\\\\n");
    }

    /**
     * Get the Google Play App publisher from a Jsoup document.
     *
     * @param doc The Jsoup document.
     *
     * @return A string of the Google Play App publisher. Empty string is returned if not found.
     */
    public static String docParsePublisher(Document doc) {
        Elements elements = doc.select("div:contains(Offered By) + span");
        return (elements.isEmpty()) ? "" : elements.text().toString().replaceAll("[\\n\\r]", "\\\\n");
    }

    /**
     * Get the first paragraph of the Google Play App description from a Jsoup document.
     *
     * @param doc The Jsoup document.
     *
     * @return A string of the first paragraph for a Google Play App description. Empty string is returned if not found.
     */
    public static String docParseDescription(Document doc) {
        Elements elements = doc.select("meta[itemprop=description]");
        return (elements.isEmpty()) ? "" : elements.first().attr("content").toString().split("\\r?\\n")[0];
    }

    /**
     * Get a Google Play App price from a Jsoup document.
     *
     * @param doc The Jsoup document.
     *
     * @return A string of the Google Play App price. Empty string is returned if not found.
     */
    public static String docParsePrice(Document doc) {
        Elements elements = doc.select("meta[itemprop=price]");
        return (elements.isEmpty()) ? "" : elements.first().attr("content").toString().replaceAll("[\\n\\r]", "\\\\n");
    }

    /**
     * Get a Google Play App rating from a Jsoup document.
     *
     * @param doc The Jsoup document.
     *
     * @return A string of the Google Play App rating. Empty string is returned if not found.
     */
    public static String docParseRating(Document doc) {
        Elements elements = doc.select("meta[itemprop=ratingValue]");
        return (elements.isEmpty()) ? "" : elements.first().attr("content").toString().replaceAll("[\\n\\r]", "\\\\n");
    }
}
