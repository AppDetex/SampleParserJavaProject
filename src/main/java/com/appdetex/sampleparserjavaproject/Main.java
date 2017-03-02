package com.appdetex.sampleparserjavaproject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {
    private String myUrl;
    private String title="";
    private String description="";
    private String publisher="";
    private String price="";
    private String rating="";

    /**
     * Constructor.
     *
     * @param url Google Play url.
     */
    public Main(String url){
        this.myUrl = url;
    }

    /**
     * retrieve_data method
     *
     * Use jSoup functions to find information.
     * @throws Exception to deal with URL.
     */
    public void retrieve_data() throws Exception{
        // Pulling HTML content.

        Document doc = Jsoup.connect(myUrl).get();
        // Need to keep newlines and breaks intact so I can get first paragraph.
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
        doc.select("br").append("\\n");
        doc.select("p").prepend("\\n\\n");

        // Getting Title
        Elements elementsTitle = doc.getElementsByClass("id-app-title");
        title = elementsTitle.text();

        // Getting Description
        Elements elementsDescription = doc.getElementsByClass("description");
        String arrDescription [];
        // Need to replace so I can split and get only the first paragraph.
        arrDescription = elementsDescription.text().replaceAll("\\\\n", "\n").split("\\n");
        description = arrDescription[0].trim();

        // Getting Publisher
        Element elementsPublisher = doc.select("div.meta-info > div.title:contains(Offered By)").first();
        publisher = elementsPublisher.nextElementSibling().text();

        // Getting Price
        String rePrice = "\\$[\\d]*\\.\\d\\d";
        Pattern patternPrice = Pattern.compile(rePrice);
        Matcher matchPrice = patternPrice.matcher(doc.html());
        if (matchPrice.find()) price = matchPrice.group(0);
        else price = "free";

        //Getting Rating
        Elements elementsRating = doc.getElementsByClass("score");
        rating = elementsRating.text();
    }

    /**
     * output_data method
     *
     * Store values in dictionary.
     * @return json formatted data.
     */
    public String output_data(){
        Map<String, String> appInfoHash = new HashMap<String, String>();
        appInfoHash.put("title", title);
        appInfoHash.put("description", description);
        appInfoHash.put("publisher", publisher);
        appInfoHash.put("price", price);
        appInfoHash.put("rating", rating);

        return hash_to_json(appInfoHash);
    }

    /**
     * hash_to_json method
     *
     * Convert Hashmap into JSON formatted string.
     * @return json formatted string.
     * @param hashMap hashMap from output_data
     */
    public String hash_to_json(Map hashMap){
        String jsonString = "";

        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Boolean isDouble=false;
            Boolean isInt=false;
            //Checking if double or int.
            try {
                Double.parseDouble(pair.getValue().toString());
                isDouble = true;
            }
            catch(NumberFormatException eD){
                isDouble = false;
                try{
                    Integer.parseInt(pair.getValue().toString());
                    isInt = true;
                }
                catch (NumberFormatException eI){
                    isInt = false;
                }

            }
            // If double or int no ''. Checking for last element to exclude comma.
            if (isDouble || isInt){
                if (it.hasNext()) jsonString = jsonString+"\""+pair.getKey()+"\": "+pair.getValue()+",\n";
                else jsonString = jsonString+"\""+pair.getKey()+"\": "+pair.getValue()+"\n";
            }
            else{
                if (it.hasNext()) jsonString = jsonString+"\""+pair.getKey()+"\": \""+pair.getValue()+"\",\n";
                else jsonString = jsonString+"\""+pair.getKey()+"\": \""+pair.getValue()+"\"\n";
            }
        }
        return "{\n"+jsonString+"}";
    }

    /**
     * main method
     *
     * This is the main method that uses the Main instance.
     * @param args User input from command line.
     * @throws Exception url needs exception handling.
     */
    public static void main(String[] args) throws Exception{
        // Checking for user command line input.
        String scrapping_url;
        if (args.length > 0){
            scrapping_url = args[0];
        }
        else{
            scrapping_url = "https://play.google.com/store/apps/details?id=com.exozet.game.carcassonne";
        }
        Main wc = new Main(scrapping_url);
        wc.retrieve_data();
        System.out.println(wc.output_data());
    }
}

