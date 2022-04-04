package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.client.WebClient;
import com.appdetex.sampleparserjavaproject.domain.ScraperResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.jsoup.helper.Validate;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String url = getUrl(args);

        WebClient client = new WebClient(url);
        ScraperResponse response = client.getScraperResponse();

        // convert to json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        // out result
        System.out.println();
        System.out.println(json);
    }

    private static String getUrl(String[] args) {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch scrapper response");
        String url = args[0];
        System.out.printf("Fetching %s...", url);
        return url;
    }

}
