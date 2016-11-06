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
        String address = args[0];
        Document page = Jsoup.connect(address).get();

        PageValues values = new PageValues();

        values.parseString("<div", page.select("div.id-app-title").toString(), "title");
        values.parseString("<div", page.select("[jsname=C4s9Ed]").toString(), "description");

        Elements publisherElement = findPublisherElement(page.select("div.meta-info"));
        if(publisherElement != null)
            values.parseString("<div", publisherElement.toString(), "publisher");

        Elements btnGroup = page.select("button.price");
        values.parseString("<span", findPriceElement(btnGroup).toString(), "price");


        values.parseString("<div", page.select("div.score").toString(), "rating");

        values.printValuesInJSON();

    }

    private static Elements findPublisherElement(Elements metaGroup)
    {
        for(Element thisElement : metaGroup)
        {
            if(thisElement.toString().contains("Offered By"))
            {
                return thisElement.select("div.content");
            }
        }

        System.out.println("Publisher not found");
        return null;
    }

    private static Element findPriceElement(Elements btnGroup)
    {
        Elements spans = btnGroup.get(0).select("span");
        return spans.get(spans.size()-1);
    }

}
