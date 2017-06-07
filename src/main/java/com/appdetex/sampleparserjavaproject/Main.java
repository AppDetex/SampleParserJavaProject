package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Main Java Class
 * <p>
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) {
        CommandLine line = null;
        Options options = new Options();

        // Option u is for URL to the Android Play Store
        options.addOption("u", true, "Android PlayStore URL");
        CommandLineParser parser = new DefaultParser();

        try {
            line = parser.parse(options, args);
        } catch (Exception e) {
            System.err.printf("Loading parser failed: " + e.getMessage());
            System.exit(1);
        }

        if (!line.hasOption("u")) {
            System.err.printf("A URL was not provided.  Provide a URL with the -u option.");
            System.exit(1);
        }

        String requestedUrl = line.getOptionValue("u");
        Document doc = null;

        try {
            doc = Jsoup.connect(requestedUrl).get();
        } catch (IOException ioe) {
            System.err.printf("HTML connection failed: " + ioe.getMessage());
            System.exit(1);
        }

        // Get Elements from Document
        Elements description = doc.select("div[itemprop=description]>div[jsname]");
        Elements title = doc.select(".id-app-title");
        Elements publisher = doc.select("span[itemprop=name]");
        Elements price = doc.getElementsByAttributeValue("itemprop", "price");
        Elements rating = doc.select("div.score-container>div.score");

        // Build SampleParserBean
        SampleParserBean SampleParserBean = new SampleParserBean();
        SampleParserBean.setTitle(title.text().trim());
        // The description div has more data then what is needed.  Split on the first line break to get correct data.
        SampleParserBean.setDescription(description.html().split("\\n", 2)[0].trim());
        SampleParserBean.setPublisher(publisher.text().trim());
        SampleParserBean.setPrice(price.attr("content").trim());
        SampleParserBean.setRating(rating.text().trim());

        // Return JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(gson.toJson(SampleParserBean));
        System.out.printf("\n" + gson.toJson(jsonElement));
    }
}
