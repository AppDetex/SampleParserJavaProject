package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


/**
 * Main Java Class
 * <p>
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static String urlForScrape;
    private static String configFileName;

    private static String scrape(Document doc, String name, WebScrapeItem scrape) {
        for (String select : scrape.getSelects()) {
            Elements elements = doc.select(select);
            if (elements.size() >= 1) {
                if (elements.size() != 1)
                    logger.warn("select found multiple items.  Sometimes they are identical. item: {}, scrape: {}", name, scrape);
                Node node = elements.first();
                if (node != null)
                    node = node.childNode(scrape.getTextOffset());
                if (node instanceof TextNode)
                    return ((TextNode) node).text();
                logger.error("scrape failure.  Found non-TextNode. item: {}, scrape: {}", name, scrape);
                return "{scrape failed}";
            }
        }
        logger.error("scrape failure.  Found with select.  item: {}, scrape: {}", name, scrape);
        return "{scrape failed}";
    }

    private static void scrapeWebPage(Map<String, WebScrapeItem> config, String urlForScrape) {
        try {
            Document doc = Jsoup.connect(urlForScrape).get();
            Map<String, String> results = new HashMap<>();

            for (Map.Entry<String, WebScrapeItem> entry : config.entrySet()) {
                String key = entry.getKey();
                WebScrapeItem webScrapeItem = entry.getValue();
                results.put(key, scrape(doc, key, webScrapeItem));
            }
            System.out.println(JsonHandler.toJSON(results));
        } catch (IOException e) {
            logger.warn("skipped scrape  due to document read failure with url: {}", urlForScrape );
        }
    }

    private static void doFancyArgParsing(String[] args) {
        /* skipped decent handling for brevity */
        if (args.length == 0) {
            System.out.println("usage: Main url [scraper config file name]");
            System.exit(1);
        }
        urlForScrape = args[0];
        if (args.length == 2)
            configFileName = args[1];
        else
            configFileName = "googlePlayJSON.txt";
    }

    public static void main(String[] args) {
        doFancyArgParsing(args);
        try {
            String input = Files.readString(Path.of(configFileName));
            Map<String, WebScrapeItem> config = JsonHandler.getWebScrapeConfig(input);

            if (testModeEnabled)
                runTestMode(config);
            else
                scrapeWebPage(config, urlForScrape);
        } catch (Exception e) {
            logger.error(e);
        }
    }


    static boolean testModeEnabled = false;
    private static void runTestMode(Map<String, WebScrapeItem> config) {
        configFileName = "googlePlayJSON.txt";

        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.mojang.minecraftedu");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.sandboxol.indiegame.bedwar");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.sandboxol.indiegame.eggwars");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.sandboxol.indiegame.skywar");

        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.facebook.orca");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.zhiliaoapp.musically");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.snapchat.android");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=com.reddit.frontpage");
        scrapeWebPage(config, "https://play.google.com/store/apps/details?id=tv.twitch.android.app");


        // handle a bad url
        scrapeWebPage(config, "https://plxxxxxxxx");

        // books and movies do not work   the fields do not exist
        scrapeWebPage(config, "https://play.google.com/store/movies/details/Toy_Story_4_Movie_Collection?id=o6HdAyDZFOk");
        scrapeWebPage(config, "https://play.google.com/store/movies/details/Spider_Man_Far_from_Home?id=5SGTus7zHwg.P");
        scrapeWebPage(config, "https://play.google.com/store/books/details/Jason_Schreier_Blood_Sweat_and_Pixels?id=-bK-DQAAQBAJ");


    }
}
