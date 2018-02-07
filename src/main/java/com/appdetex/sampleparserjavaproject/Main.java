package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.text.ParseException;

import org.jsoup.helper.Validate;

import com.appdetex.sampleparserjavaproject.domain.ItemProperties;
import com.appdetex.sampleparserjavaproject.utils.GoogleAppStoreScraper;
import com.appdetex.sampleparserjavaproject.utils.Scraper;
/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        Scraper scraper = new GoogleAppStoreScraper();
        
        ItemProperties ip = scraper.scrapeUrl(url);
        System.out.println(ip.convertToJson());
    }
}
