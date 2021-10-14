package com.appdetex.sampleparserjavaproject.controller.impl;

import com.appdetex.sampleparserjavaproject.controller.ScraperController;
import com.appdetex.sampleparserjavaproject.model.GooglePlayApp;
import com.appdetex.sampleparserjavaproject.util.ScraperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author Christopher
 */
public class ScraperControllerImpl implements ScraperController {

    private ScraperConfig config;
    
    public ScraperControllerImpl(ScraperConfig config) {
        this.config = config;
    }
    
    @Override
    public String retrieveDataAsJson(String googlePlayUrl) {
                
        String result = "";
        
        try {
            // Let the user know we're getting the data from the URL (would normally be a DEBUG LOG)
            System.out.print("Loading data from: " + googlePlayUrl + " ... ");
            
            // Grab the HTML from the URL.
            Document doc = Jsoup.connect(googlePlayUrl).get();
            
            // Report done (would normally be a DEBUG LOG - with more context with multiple threads)
            System.out.println("DONE.\n");
            
            // Create our output object that we'll transform to JSON.
            GooglePlayApp game = new GooglePlayApp();
            
            // Set the title with the selector value from our configuration.
            game.setTitle(doc.select(config.getValue("title")).text());
            
            // Get the entire description in HTML form.
            String descriptionHtml = doc.select(config.getValue("description")).first().html();
            
            // Look for the first break <BR> 
            // *** Assumes the first break denotes above text is description ***
            int brIndex = descriptionHtml.indexOf("<br>");
            if (brIndex > 0) {
                // Set the description. Only the first paragraph.
                game.setDescription(descriptionHtml.substring(0, brIndex - 1));
            } else {
                // Set the description to the entire element text.
                game.setDescription(doc.select(config.getValue("description")).first().text());
            }

            // Set the publisher (assumes next elements text is the data)
            game.setPublisher(doc.select(config.getValue("publisher")).next().text());    
            
            // Set the price (assumes the elements attribute "content" is the data)
            game.setPrice(doc.select(config.getValue("price")).attr("content"));

            // Set the rating
            game.setRating(doc.select(config.getValue("rating")).text());
            
            // Create a JSon mapper.
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            
            // Create our string based result.
            result = mapper.writeValueAsString(game);
            
        } catch (IOException ioe) {
            // Lame error handling but it does report the error.
            System.out.println("OOPS! What went wrong: " + ioe.getMessage());
            ioe.printStackTrace();
        }        
        
        return result;
    }    
}
