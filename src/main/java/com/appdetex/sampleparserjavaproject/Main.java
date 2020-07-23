package com.appdetex.sampleparserjavaproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 1) { // keep it simple, just error if num arguments isn't exactly 1
            printUsage();
            System.exit(1);
        }

        String url = args[0];
        AppStoreDetailParser parser = new AppStoreDetailParser(); // inits Jsoup
        MobileApp mobileApp = null;

        try {
            mobileApp = parser.parse(url);

            // output mobileApp result (pretty) to STDOUT
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mobileApp);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            System.out.println(String.format("Failure occurred while converting MobileApp to json: %s", mobileApp));
            e.printStackTrace();
        }

    }

    private static void printUsage() {
        System.out.println("You must supply a single url to parse.");
    }

}
