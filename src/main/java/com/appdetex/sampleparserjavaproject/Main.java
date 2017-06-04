package com.appdetex.sampleparserjavaproject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) throws IOException {
        CommandLine line = null;
        Options options = new Options();

        options.addOption("url", true, "The Google Play Application URL you wish to Query.");

        CommandLineParser parser = new DefaultParser();
        try {
            line = parser.parse(options, args);
        } catch (Exception e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }

        if (line.hasOption("url")) {
            System.out.print(line.getOptionValue("url"));
            String requestedUrl = line.getOptionValue("url");
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
            Document doc = Jsoup.connect(requestedUrl).userAgent(userAgent).get();
            System.out.print(doc.toString());
            // TODO: Make HTTP Call
            // TODO: Parse HTTP Call
            // TODO: Return JSON
        }
    }

}
