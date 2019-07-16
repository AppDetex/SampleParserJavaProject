package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {



    //TODO: this can be cleaned up a bit. It might be better to set up the RegEx ORs to be 1-1 with data fields we're scraping.
    public static final String REGEX =
                    "itemprop=\"([a-zA-Z0-9]*)\" content=\"([\\s\\n\\r\\t\\.\\*!@#$%^&*\\[\\]\\-,'\"_+=;&?=/:.a-zA-Z0-9]*)\"" + //match the easy stuff: Description, price
                    "|aria-label=\"(Rated) [\\s\\n\\r\\t\\.\\*!@#$%^&*\\[\\]\\-,'_+=;&?=/:.a-zA-Z0-9]*\">([0-9.]*)" + //match rating
                    "|(developer)\\?id=([a-zA-Z0-9]*)" + //match publisher
                    "|\"SoftwareApplication\",\"(name)\":\"(([\\s\\w0-9a-zA-Z]*))\",\"image\"" +
                    ""; //end of regex

    public static void main(String[] args) {
        Main scraper = new Main();
        if(args.length == 0) {
            scraper.printErrorAndUsage(new Exception("missing parameter <URL> during execution"));
            System.exit(1);
        }

        try {
            Document document = Jsoup.connect(args[1]).get();
            HashMap<String,String> items = scraper.parseStringToHashMap(document.html());
            items = scraper.standardizeMapKeys(items);
            items = scraper.tryJsoup(document, items);
            System.out.println(new Gson().toJson(items));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String,String> standardizeMapKeys(HashMap<String, String> items) {
        items.put("rating", items.remove("Rated"));
        items.put("title", items.remove("name"));
        items.put("publisher", items.remove("developer"));
        items.remove("url");
        return items;
    }

    public HashMap<String,String> parseStringToHashMap(String searchString) {
        HashMap<String,String> map = new HashMap<String,String>();
        Matcher matches = Pattern.compile(REGEX)
                .matcher(searchString);
        while (matches.find()) {
            List<String> goodGroupings = new ArrayList<String>();
            for (int i = 0; i < matches.groupCount(); i++) {
                if(matches.group(i) != null) {
                    goodGroupings.add(matches.group(i));
                }
            }
            //Group 1 is always the full matched string, the last two groups (roughly key/value) are always the last two in this array.
            map.put(goodGroupings.get(goodGroupings.size()-2), goodGroupings.get((goodGroupings.size()-1)));
        }
        return map;
    }

    public String get(String urlString) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlString);
            URLConnection urlConn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();

            //tryJsoup(urlString);
        } catch (IOException e) {
            printErrorAndUsage(e);
        } finally {
            return sb.toString();
        }
    }

    public HashMap<String,String> tryJsoup(Document document, HashMap<String,String> items) throws IOException {
        String title = document.getElementsByAttributeValueContaining("itemprop", "name").text();
        String rating = document.getElementsByAttributeValueContaining("aria-label", "Rated").text().trim();
        String publisher = document.getElementsContainingText( "SoftwareApplication").text().trim();
        String price = document.getElementsByAttributeValueContaining("itemprop", "price").attr("content");

        items.putIfAbsent("title", title);
        items.putIfAbsent("rating", rating);
        items.putIfAbsent("publisher", publisher);
        items.putIfAbsent("price",price);
//        document.getElementsByAttribute("aria-hidden");
//        document.getElementsContainingOwnText("@context");
//        Map<String,String> jsoupMatches = new HashMap<String, String>();
        return items;

    }

    private void printErrorAndUsage(Exception e) {
        System.out.println(e);
        System.out.println("Write some usage stuff here...");
    }

}
