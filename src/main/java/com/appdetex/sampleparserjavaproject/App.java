package com.appdetex.sampleparserjavaproject;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;

/**
 * This program takes a google playstore app uri as a command-line argument and parses important information about the
 * app into a JSON object.
 * @author nbrinton
 */
public class App {

    public static void main(String args[]) {
        String usage = "Usage:\njava Main <google playstore app uri>\nExample:\njava App https://play.google.com/store/apps/details?id=com.auxbrain.egginc";

        if (args.length != 1) {
            System.out.println(usage);
            System.exit(0);
        }

        UrlValidator validator = new UrlValidator();
        String url = args[0].trim();

        if (!validator.isValid(url)) {
            System.out.println("The given url is not valid, please rerun with a valid url");
            System.out.println(usage);
            System.exit(0);
        } else if (!url.contains("play.google.com/store/apps/")) {
            System.out.println("The given url is not the url for a google app on the google play store. Please rerun with a valid google app url.");
            System.out.println(usage);
            System.exit(0);
        }

        System.out.println("Running...");
        try {
            System.out.println("Parsing: " + url);
            HtmlParser parser = new HtmlParser(url);
            parser.parseToJSON();
            System.out.println(parser.toString());
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Running");

    }
}

