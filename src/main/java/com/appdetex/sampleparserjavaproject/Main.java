package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        String response = scraper.get(args[0]);
        Map<String,String> items = scraper.parseStringToMap(response);
        items = scraper.standardizeMapKeys(items);
        System.out.println(new Gson().toJson(items));
    }

    public Map<String,String> standardizeMapKeys(Map<String, String> items) {
        items.put("rating", items.remove("Rated"));
        items.put("title", items.remove("name"));
        items.put("publisher", items.remove("developer"));
        items.remove("url");
        return items;
    }

    public Map<String,String> parseStringToMap(String searchString) {
        Map<String,String> map = new HashMap<String,String>();
        Matcher matches = Pattern.compile(REGEX)
                .matcher(searchString);
        while (matches.find()) {
            List<String> goodGroupigs = new ArrayList<String>();
            for (int i = 0; i < matches.groupCount(); i++) {
                if(matches.group(i) != null) {
                    goodGroupigs.add(matches.group(i));
                }
            }
            //Group 1 is always the full matched string, the last two groups (roughly key/value) are always the last two in this array.
            map.put(goodGroupigs.get(goodGroupigs.size()-2), goodGroupigs.get((goodGroupigs.size()-1)));
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

            tryJsoup(urlString);
        } catch (IOException e) {
            printErrorAndUsage(e);
        } finally {
            return sb.toString();
        }
    }

    public void tryJsoup(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element body  =document.body();
        document.getElementsContainingText("Rated");
        Elements elements = document.getElementsByAttribute("itemprop");
        Elements eleemnts2  = document.getElementsByAttribute("developer");
        String rating = document.getElementsByAttributeValueContaining("aria-label", "Rated").text().trim();
        String developer = document.getElementsByAttributeValueContaining("", "(developer)\\?id=([a-zA-Z0-9]*)").text();
        Map<String,String> jsoupMatches = new HashMap<String, String>();
        document.getElementsContainingOwnText("@context");
        for(int i = 0; i < elements.size(); i++ ) {
            Element e = elements.get(i);
            String itemPropName = e.attr("itemprop");
            String itemPropValue = e.attr("content");

            if(!itemPropName.equals("") && !itemPropValue.equals("")) {
                System.out.println(e.attr("itemprop"));
                System.out.println(e.attr("content"));

            }
        }
    }

    private void printErrorAndUsage(Exception e) {
        System.out.println(e);
        System.out.println("Write some usage stuff here...");
    }

}
