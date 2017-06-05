package com.appdetex.sampleparserjavaproject;

import java.math.BigDecimal;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve the provided URL(s)
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static double roundit(String s) {
        return roundit(s, 2);
    }

    public static double roundit(String s, int scale) {
        Double t = new Double(s);
        return new BigDecimal(t).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static List<String> loadUrls(String[] args) throws IOException {
        List<String> urls = new ArrayList<String>();
        if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
            urls.add(args[0]);
        } else {
            try {
                Path filePath = new File(args[0]).toPath();
                Charset charset = Charset.defaultCharset();
                urls = Files.readAllLines(filePath, charset);
            } catch (java.nio.file.NoSuchFileException | java.io.FileNotFoundException ex) {
                System.out.println("Bad file path!");
                usage();
            }
        }
        return urls;
    }

    public static void usage() {
        System.out.println(
            "Usage: app <url> | <path to file>\n\n" +
            "Where <path to file> contains one URL per line");
    }

    @SuppressWarnings("unchecked")
    public static void main( String[] args ) throws IOException {
        if (args.length == 0) {
            usage();
            return;
        }

        List<String> urls = loadUrls(args);
        if (urls.size() == 0) return;

        JSONArray scraped = new JSONArray();

        Iterator it = urls.iterator();
        while (it.hasNext()) {
            String url = (String)it.next();
            JSONObject jo = new JSONObject();
            try {
                Document doc = org.jsoup.Jsoup.connect(url).timeout(10*1000).get();

                Elements el = doc.select("h1[itemprop = name]");
                jo.put("title", el.text());

                el = doc.select("div[itemprop = description] div");
                jo.put("description", el.text());

                el = doc.select("span[itemprop = name]");
                jo.put("publisher", el.text());

                el = doc.select("meta[itemprop = price]");
                jo.put("price", el.attr("content"));

                el = doc.select("meta[itemprop = ratingValue]");
                jo.put("rating", roundit(el.attr("content")));

                scraped.add(jo);
            } catch (java.net.UnknownHostException
                     | java.net.SocketTimeoutException
                     | java.net.ConnectException ex) {
                System.err.println(ex.toString());
            } catch (java.lang.NumberFormatException ex) {
                // Means we got something back from our request, but couldn't
                // parse it. Die horribly.
                System.out.println(ex.toString());
                System.exit(1);
            }
        }
        if (scraped.size() > 0) System.out.println(scraped);
    }
}
