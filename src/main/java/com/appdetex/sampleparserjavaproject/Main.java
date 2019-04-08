package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
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
 */
public class Main {

    private static boolean debug_ = false;

    public static void main( String[] args ) throws IOException {

        // DEBUG - test url
        String testURL = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US";

        String url = null;

        for (String arg : args) {
            if (arg.startsWith("--")) {
                if (arg.substring(2).equals("debug")) {
                    debug_ = true;
                }
            } else {
                if (url == null) {
                    url = arg;
                }
            }
        }
        if (url == null) {
            if (debug_) {
                url = testURL;
            }
        }
        if (url == null) {
            System.err.printf("No URL specified\n");
            System.exit(1);
        }

        DEBUG("Fetching: %s\n", url);

        Document doc = null;
        try {

            doc = Jsoup.connect(url).get();

        } catch (Exception e) {
            System.err.printf("Could not fetch %s: %s\n", url, e);
            System.exit(1);
        };

        Element body = doc.body();
        Element head = doc.head();

        String title = null;
        String publisherName = null;
        String description = null;
        String price = null;
        String rating = null;

        // Title
        title = doc.title();
        if (title != null) {
            int x = title.indexOf(" - Apps on Google Play");
            if (x > 0) {
                title = title.substring(0, x);
            }
        }

        // Description
        Element desc = head.select("meta[name='description']").first();

        if (desc != null) {
            if (desc.hasAttr("content")) {
                description = desc.attr("content");
                int x = description.indexOf("\n");
                description = description.substring(0, x);
            }
        }

        // Publisher name
        Elements links = body.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            if (linkHref.contains("developer?id=")) {
                publisherName = link.text();
                break;
            }
        }

        // Price
        Elements buttons = body.getElementsByTag("button");
        for (Element button : buttons) {
            String buttonText = button.text();
            if (buttonText.contains(" Buy")) {
                int x = buttonText.indexOf(" Buy");
                price = buttonText.substring(0, x);
                break;
            }
        }
        if (price == null) {
            price = "0.00";
        }

        // Rating
        Elements lbls = body.select("[aria-label]");
        for (Element l : lbls) {
            String text = l.attr("aria-label");
            if (text.startsWith("Rated ") && text.endsWith(" stars")) {
                rating = text.replace("Rated ", "").replace(" stars out of five stars", "");
                break;
            }
        }

        // Format JSON output
        System.out.printf("{\n");
        System.out.printf("    \"title\": \"%s\",\n", title);
        System.out.printf("    \"description\": \"%s\",\n", description);
        System.out.printf("    \"publisher\": \"%s\",\n", publisherName);
        System.out.printf("    \"price\": \"%s\",\n", price);
        System.out.printf("    \"rating\": %s\n", rating);
        System.out.printf("}\n");

        System.exit(0);
    }

    private static void DEBUG(String msg, Object... args) {
        if (debug_) {
            System.out.printf("DEBUG: " + msg, args);
        }
    }

}
