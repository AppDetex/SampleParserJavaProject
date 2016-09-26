import java.io.IOException;

import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {

    public static void main( String[] args ) {
    	
    	Scanner reader = new Scanner(System.in);
    	System.out.println("Enter a App Store URL: ");
    	String game = reader.next();
    	appPage(game);
    	reader.close();
    }
    
    public static void appPage(String page){
	  	try {

	    		Document doc = Jsoup.connect(page).get();
				String score = doc.select("div[class=score]").text();
				String desc2 = doc.select("div[class=show-more-content text-body]").first().text();
				String desc = desc2.substring(0, 400);
				desc = desc.replaceAll("”", " ");
				desc = desc.replaceAll("“", "");
				desc = desc.replaceAll("’", "");
				desc = desc.replaceAll("'", "");
				desc = desc.replaceAll("\"", "");
				String title = doc.select("div[class=id-app-title]").text();
				Elements priceLink = doc.select("meta[itemprop=price]");
				String price = priceLink.attr("content");
				String author = doc.select("span[itemprop=name]").text();

				JSONObject obj = new JSONObject();
				
				try {
					obj.put("title", title);
					obj.put("author", author);
					obj.put("description", desc);
					obj.remove("\u201c");
					obj.put("price", price);
					obj.put("score", score);
					System.out.println(obj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    

}
