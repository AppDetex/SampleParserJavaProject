package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        // Finds the title and sets it in the PageValue object
        String titleElem = page.getElementsByClass("id-app-title").get(0).text();
        values.setTitle(titleElem);

        // Finds the 1st paragraph of description and sets it in PageValues object
        values.setDescription(findDescriptionFirstPara(page));

        // Finds the publisher info and sets it in the PageValues object
        values.setPublisher(findPublisherElement(page.getElementsByClass("meta-info")));

        // Finds the first <btn> with class='price'.  Selects all the <span> tags within
        Elements spanGrp = page.select("button.price").get(0).select("span");
        // Selects the last <span> tag from spanGrp containing the price and sets it in
        // the PageValues object
        String price = spanGrp.get(spanGrp.size()-1).text();
        values.setPrice(price);

        // Finds the <div> with the app rating and gets its text.  Then parses the double from
        // the text and sets it in the PageValues object
        String ratingStr = page.getElementsByClass("score").get(0).text();
        double rating = Double.parseDouble(ratingStr);
        values.setRating(rating);

        // Creates a new JSON Building object
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        // Serializes the PageValues object into JSON and prints it to console
        System.out.println(gs.toJson(values));

    }

    /**
     * Because we only want the first paragraph of the description, we have to account for two cases.  The first
     * case is when the entire description is in one paragraph.  In this case, the description is text within the
     * parent <div> that happens before the first <p> tag.  The second case happens when the description has
     * multiple paragraphs.  In this case, the description is broken up between multiple <p> tags within the parent
     * <div> tag.  This method discovers which is the case and returns only the first paragraph.
     *
     * @param page - This is the entire HTML page as a Document object
     * @return Only a string containing the first paragraph of the description
     */
    private static String findDescriptionFirstPara(Document page){
        // Grabs the entire <div> containing the description of the app
        Element descBlock = page.select("[jsname=C4s9Ed]").get(0);
        // Grabs all the <p> tags from within the above block
        Elements ptags = descBlock.getElementsByTag("p");

        // Grabs the text of the entire block including <p> tags and the text from only
        // the <p> tags and removes white space from front and rear of string
        String descBlockText = descBlock.text().trim();
        String ptagsText = ptags.text().trim();

        // If the text from the <p> tags is not equal to the beginning of the entire <div> then
        // there is text before the first <p> tag and we have a description with only one paragraph
        if(descBlockText.indexOf(ptagsText) != 0)
        {
            // Subtracts the length of the text from the entire <div> section from the length of
            // the text from <p> tags to give us the length of what is in front of the <p> tags
            int firstParaLength = descBlockText.length() - ptagsText.length();

            // Using that length, we take a substring of the <div> section to give us only the
            // first paragraph without the reviews or anything
            return descBlockText.substring(0, firstParaLength);
        }

        // There are multiple paragraphs in the description and returns the first <p> tag's text
        else
            return ptags.get(0).text();

    }

    /**
     * Due to the amount of <div> tags with class="meta-info" and class="content", we need to find the meta-info
     * div that contains the string "Offered By" so we can find its child content div which will contain the
     * publisher.  This <div> chunk is returned to pass into the parsing method in PageValues class
     *
     * @param metaGroup - An ArrayList(Jsoup Elements Object) of all the <div class="meta-info"> chunks scraped
     *                  from the site
     * @return A string that contains the publisher info
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

        return "**Publisher not Found**";
    }

}
