import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MainNoSoup Class
 *
 * The MainNoSoup takes a URL from the command line and returns html.
 * The html is then processed and the valuable information is extracted into a json formatted string.
 * The valuable info includes: title, description, publisher, price, and rating.
 */
public class MainNoSoup {
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
    public MainNoSoup(String url){
        this.myUrl = url;
    }

    /**
     * retrieve_data method
     *
     * Converts the raw string into an array (each block is an index).
     * @return outputs an array of block values.
     * @throws Exception to deal with URL.
     */
    public String[] retrieve_data() throws Exception{
        // Pulling HTML content.
        URL url = new URL(myUrl);
        InputStreamReader isr = new InputStreamReader(url.openStream());
        BufferedReader br = new BufferedReader(isr);

        // Converting to string.
        String inputLine;
        String entireHtml = "";
        while ((inputLine = br.readLine()) != null)
            entireHtml = entireHtml + inputLine;

        // Splitting by the > and using look behind to keep character ('<=').
        // Each index in the array will look like "<.*>" or ".*<.*>".
        return entireHtml.split("(?<=\\>)");
    }

    /**
     * clean_data method
     *
     * This method iterates through the array and matches specific regex.
     * The regex found is the parent block of the wanted data. A while loop
     * retrieves the children blocks.
     * The if statement checks if start of a block; if it is increment up.
     * If it is closing increment down. When the end of the parent block is found (cont=0) escape
     * the while loop. <br> has no close tags so do not increment of decrement.
     * @param data Array from retrieve_data()
     */
    public void clean_data(String[] data){
        // Regex Patterns
        String reTitle = "class=\"id-app-title\"";
        Pattern patternTitle = Pattern.compile(reTitle);
        String reDescription= "itemprop=\"description\"";
        Pattern patternDescription = Pattern.compile(reDescription);
        String rePublisher = "Offered By <";
        Pattern patternPublisher = Pattern.compile(rePublisher);
        String rePrice = "\\$[\\d]*\\.\\d\\d";
        Pattern patternPrice = Pattern.compile(rePrice);
        String reRating = "<div class=\"score\".*Rated.*stars.*?>";
        Pattern patternRating = Pattern.compile(reRating);

        String line;
        for(int i=0; i<data.length; i++){
            line = data[i];
            //Regex Matching
            Matcher matchTitle = patternTitle.matcher(line);
            Matcher matchPublisher = patternPublisher.matcher(line);
            Matcher matchPrice = patternPrice.matcher(line);
            Matcher matchRating = patternRating.matcher(line);
            Matcher matchDescription= patternDescription.matcher(line);

            if(matchTitle.find()){
                int countUp = i;
                int cont = 1;
                // Getting children
                while (cont>0){
                    title = title + data[countUp];
                    countUp++;
                    if(data[countUp].replaceAll("\\s","").startsWith("<") && !data[countUp].replaceAll("\\s","").startsWith("</") && !data[countUp].replaceAll("\\s","").startsWith("<br>")) cont++;
                    else if(data[countUp].replaceAll("\\s","").startsWith("<br>") || data[countUp].replaceAll("\\s","").endsWith("<br>")) cont = cont;
                    else cont--;
                }
                title = title + data[countUp];
            }
            else if (matchDescription.find()){
                int countUp = i;
                int cont = 1;
                // Getting children
                while (cont>0){
                    description = description + data[countUp];
                    countUp++;
                    if(data[countUp].replaceAll("\\s","").startsWith("<") && !data[countUp].replaceAll("\\s","").startsWith("</") && !data[countUp].replaceAll("\\s","").startsWith("<br>")) cont++;
                    else if(data[countUp].replaceAll("\\s","").startsWith("<br>") || data[countUp].replaceAll("\\s","").endsWith("<br>")) cont = cont;
                    else cont--;

                    // Remove to get entire description.
                    if(data[countUp].replaceAll("\\s","").startsWith("<br>")){
                        break;
                    }
                }
                description = description + data[countUp];
            }
            else if(matchPublisher.find()){
                int countUp = i;
                int cont = 1;
                // Getting children
                while (cont>0){
                    publisher = publisher + data[countUp];
                    countUp++;
                    if(data[countUp].replaceAll("\\s","").startsWith("<") && !data[countUp].replaceAll("\\s","").startsWith("</") && !data[countUp].replaceAll("\\s","").startsWith("<br>")) cont++;
                    else if(data[countUp].replaceAll("\\s","").startsWith("<br>") || data[countUp].replaceAll("\\s","").endsWith("<br>")) cont = cont;
                    else cont--;
                }
                publisher = publisher + data[countUp];
            }
            else if(matchPrice.find()){
                price = matchPrice.group(0);
            }
            else if(matchRating.find()){
                int countUp = i;
                int cont = 1;
                // Getting children
                while (cont>0){
                    rating = rating + data[countUp];
                    countUp++;
                    if(data[countUp].replaceAll("\\s","").startsWith("<") && !data[countUp].replaceAll("\\s","").startsWith("</") && !data[countUp].replaceAll("\\s","").startsWith("<br>")) cont++;
                    else if(data[countUp].replaceAll("\\s","").startsWith("<br>") || data[countUp].replaceAll("\\s","").endsWith("<br>")) cont = cont;
                    else cont--;
                }
                rating = rating + data[countUp];
            }
        }
    }

    /**
     * output data method
     *
     * This method finishes the cleaning process
     * @return json formatted data.
     */
    public String output_data(){


        String[] arrTitle = title.split("[<>]");
        title = arrTitle[2];

        String[] arrDescription = description.replaceAll("<br>", " ").split("[<>]");
        description = arrDescription[4].trim();

        String[] arrPublisher = publisher.split("[<>]");
        publisher = arrPublisher[4];

        if (price.equals("")){
            price = "free";
        }

        String[] arrRating = rating.split("[<>]");
        rating = arrRating[2];

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
     * This is the main method that uses the MainNoSoup instance.
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
            scrapping_url = "https://play.google.com/store/apps/details?id=com.trueaxis.trueskate";
        }
        MainNoSoup wc = new MainNoSoup(scrapping_url);
        String[] rd = wc.retrieve_data();
        wc.clean_data(rd);
        System.out.println(wc.output_data());
    }
}


