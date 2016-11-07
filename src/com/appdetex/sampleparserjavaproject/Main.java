package com.appdetex.sampleparserjavaproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) throws IOException
    {
        // If there is more than one argument passed in, give message and exit program
        if(args.length > 1){
            System.out.println("Please only use one Google Play website at a time");
            System.exit(0);
        }

        // Gets the string passed in as an argument and connects to that website using Jsoup
        String address = args[0];
        Document page = Jsoup.connect(address).get();

        // Create new PageValues object to hold the values we are interested in.
        PageValues values = new PageValues();

        String titleElem = page.getElementsByClass("id-app-title").get(0).text();
        values.setTitle(titleElem);
        //values.parseString("<div", page.select("div.id-app-title").toString(), "title");

        Elements description = page.select("[jsname=C4s9Ed]");

        values.parseString("<div", page.select("[jsname=C4s9Ed]").toString(), "description");

        String publisher = findPublisherElement(page.getElementsByClass("meta-info"));
        if(publisher != null)
            values.setPublisher(publisher);

        String price = page.select("button.price").get(0).text();
        values.setPrice(price);

        String ratingStr = page.getElementsByClass("score").get(0).text();
        double rating = Double.parseDouble(ratingStr);
        values.setRating(rating);

        values.printValuesInJSON();

    }

    private static String findDescriptionFirstPara(){

        return "";
    }

    /**
     * Due to the amount of <div> tags with class="meta-info" and class="content", we need to find the meta-info
     * div that contains the string "Offered By" so we can find its child content div which will contain the
     * publisher.  This <div> chunk is returned to pass into the parsing method in PageValues class
     *
     * @param metaGroup - An ArrayList(Jsoup Elements Object) of all the <div class="meta-info"> chunks scraped
     *                  from the site
     * @return An Element object that contains the <div class="content"> chunk that contains the publisher info
     */
    private static String findPublisherElement(Elements metaGroup)
    {
        // Loops through each meta-info div chunk in 'metaGroup'
        for(Element thisElement : metaGroup)
        {
            // If this meta-info div chunk contains "Offered By", we have found the one we're looking for and return
            // its child <div class="content"> that contains the publisher string we are looking for
            if(thisElement.toString().contains("Offered By"))
            {
                return thisElement.getElementsByClass("content").get(0).text();
            }
        }

        System.out.println("Publisher not found");
        return null;
    }

    /**
     * There are several <btn class="price"> elements on these pages, so we take an ArrayList(Jsoup Elements object)
     * of those elements and find the correct one to return for the price of this specific app.
     *
     * @param btnGroup - An ArrayList(Jsoup Elements Object) of all the <btn class="price"> chunks scraped
     *                  from the site
     * @return An Element object that contains the <span> chunk that contains the price info
     */
    private static Element findPriceElement(Elements btnGroup)
    {
        // Chooses the first <btn> tag in 'btnGroup' and selects all <span> tags from it
        Elements spans = btnGroup.get(0).select("span");
        // Chooses the last <span> in the group of them just found and returns it, which contains the price we
        // are looking for
        return spans.get(spans.size()-1);
    }

}
