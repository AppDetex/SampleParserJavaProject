package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;


public class AppInfoParser {

    private String parsedApp;
    private String appUrl;

    public AppInfoParser(String url) throws IOException {
        appUrl = url;
    }

    public String parseAppInfo() throws IOException {

        try {
            /**
             * For Title, Publisher, Price and Rating, we can select the values from the translated HTML page
             * and check for null values using the Strings.nullToEmpty method. For Description, we will
             * invoke a secondary method to loop through an HTML Element and extract the 1st paragraph
             */
            Document appDoc = Jsoup.connect(appUrl).get();
            String title = Strings.nullToEmpty(appDoc.select("h1[itemprop=name]").text().trim());
            String publisher = Strings.nullToEmpty(appDoc.select("span[class=T32cc UAO9ie]").text().trim());
            String rating = Strings.nullToEmpty(appDoc.select("div[class=BHMmbe]").text().trim());
            String price = Strings.nullToEmpty(appDoc.select("meta[itemprop=price]").attr("content").trim());

            String fullAppDescription = Strings.nullToEmpty(appDoc.select("div[itemprop=description]").first().toString());
            String description = parsedAppDescription(fullAppDescription);

//            Use an ApplicationInfo Class as a Getter/Setter for the parsed app object
            ApplicationInfo parsedAppObject = new ApplicationInfo(title, description, publisher, price, rating);

            ObjectMapper mapper = new ObjectMapper();

            try {
//             Map the translated application to a JSON string
                parsedApp =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parsedAppObject);
            } catch (JsonProcessingException e) {
                System.err.println("Error: Cannot convert Parsed Application to JSON");
                System.err.println(e.getMessage());
                System.exit(9);
            }

        } catch (IOException e) {
            System.err.println("Error: Problem parsing Application with JSoup");
            System.err.println(e.getMessage());
            System.exit(9);
        }

        return parsedApp;
    }

/**
 *   To get the first paragraph of the description, we need to parse the page in lieu of simply selecting values.
 *   Then by utilizing the select/after convention from JSoup, we can set a variable to serve as the line break
 *   within the application's description and get the string preceding the double <br><br>
 */

    private String parsedAppDescription(String fullAppDescription){
        if(!fullAppDescription.equals("")){
            Document parsedDescription = Jsoup.parse(fullAppDescription);
            parsedDescription.select("br").after("nl ");
            String applicationDescription = parsedDescription.text();
            String[] descriptionParagraphs = applicationDescription.split("nl nl");
            return descriptionParagraphs[0].replaceAll("nl ", "").trim();
        }else{
            return fullAppDescription;
        }
    }
}

