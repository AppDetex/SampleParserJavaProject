package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONValue;
import org.jsoup.select.Elements;


/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main ( String[] args ) {
        Document doc = null; //will not remain null past try catch
        Map<String,String> json; //will contain elements of output

        //handle invalid amount of command line arguments
        if (args.length != 1){
            printUsage();
            System.exit(1);
        }

        try{
            doc = Jsoup.connect(args[0]).get();
        }catch(IOException ioe){
            System.err.println("Encountered an IO Exception. Please verify the URL and try again.");
            ioe.printStackTrace();
            System.exit(1);
        }

        json = new LinkedHashMap<String,String>(); //data structure containing output elements

        /* begin building JSON output - alternates getting String from
        document and adding that string to the JSON object.
         */

        //title
        String title = doc.title();
        json.put("title", title);

        //description
        Elements descriptionElements = doc.select("div.show-more-content");
        String description = "";
        // compile the elements into a single string
        for (Element elem : descriptionElements){
            description = description + elem.text();
        }
        json.put("description", description);

        //publisher
        Elements docSubtitle = doc.select("a.document-subtitle");
        Elements publisherElems = docSubtitle.select("span[itemprop=name]");
        String publisher = publisherElems.last().text();
        json.put("publisher", publisher);

        //price
        Elements priceButtonElems = doc.select("button.price");
        String priceBuy = priceButtonElems.first().text();
        // take substring because we don't need "Buy" in our output
        String price = priceBuy.substring(0, priceBuy.length()-4);
        json.put("price", price);

        //rating
        Elements rating = doc.select("div.score");
        String score = rating.last().text();
        json.put("rating", score);

        // put data structures contents into JSON format and print
        String jsonText = JSONValue.toJSONString(json);
        System.out.print(jsonText);
    }// end of main() method

    /**
     * This method is used to instruct the user on proper usage of the program when they have entered
     * an invalid amount of command line arguments.
     */
    private static void printUsage(){
        System.err.println();
        System.err.println("Usage: java Main <URL>");
        System.err.println("The <URL> field should have a valid/reachable URL.");
        System.err.println();
    }

}// end of Main class
