package com.appdetex.sampleparserjavaproject;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
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
        }
    }

}
