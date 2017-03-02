package com.appdetex.sampleparserjavaproject;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GooglePlayTop class
 *
 * Simple program to accumulate list of possible URLS from Top of the Google Play Store.
 * Used for testing MainNoSoup
 * Created by jakesmorrison on 3/2/17.
 */
public class GooglePlayTop {
    private String myUrl;

    /**
     * Constructor
     *
     * Calls retrieve_data from MainNoSoup class.
     * @param url Google Play Top url.
     * @throws Exception url
     */
    private GooglePlayTop(String url)throws Exception{
        this.myUrl = url;
        MainNoSoup gpwc = new MainNoSoup(myUrl);
        list_of_urls(gpwc.retrieve_data());
    }

    /**
     * list_of_url method
     *
     * Finds urls, clean them up, and save them to a file.
     * @param data html blocks stored in each array index.
     */
    private void list_of_urls(String[] data) {
        String reUrl = "class=\"card-click-target\"";
        Pattern patternUrl = Pattern.compile(reUrl);

        // Getting urls and storing into dictionary so there are no duplicates.
        Map<String, String> urlHash = new HashMap<String, String>();
        String line;
        for (int i = 0; i < data.length; i++) {
            line = data[i];
            Matcher matchUrl = patternUrl.matcher(line);
            if (matchUrl.find()) {
                String reHref = "href=\".*?\"";
                Pattern patternHref = Pattern.compile(reHref);
                Matcher matchHref = patternHref.matcher(line);
                if (matchHref.find()) {
                    String foo = matchHref.group(0);
                    foo = foo.replaceAll("href=\"", "");
                    foo = foo.replaceAll("\"", "");
                    foo = "https://play.google.com" + foo;
                    urlHash.put(foo, foo);
                }
            }
        }

        // Iterator to transverse hash and save keys to a file.
        try{
            PrintWriter writer = new PrintWriter("test_urls.txt", "UTF-8");
            Iterator it = urlHash.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                writer.println(pair.getKey());
            }
            writer.close();
        } catch (IOException e) {}
    }

    /**
     * main method.
     *
     * @throws Exception url
     */
    public static void main(String[] args)throws Exception{
        GooglePlayTop gpt = new GooglePlayTop("https://play.google.com/store/apps/top");
    }
}
