import java.io.InputStream;
import java.net.URL;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class Main 
{
	
	/* Through 42matters.com Android API, I can connect to any app and get details of that app in the form of JSON Data. 
	 * By replacing com.xample.airnavigation with com.facebook.katana in the URL we will get the title, description, publisher, price, rating of Facebook app.  
	 */
	public static void main(String args[]) throws Exception 
	{
	 URL url = new URL("https://42matters.com/api/1/apps/lookup.json?p=com.xample.airnavigation&access_token=722e1644744dabedf118fdde9a3f185c4d4f8b28");
	 try (InputStream is = url.openStream();
	       JsonReader rdr = Json.createReader(is)) 
	       {
		 	JsonObject obj = rdr.readObject();
		 
		 	System.out.println("{");
		 	System.out.print("	\"title\":");
		 	System.out.print(" \"");
		 	System.out.print(obj.getString("title"));
		 	System.out.println("\",");
		 	
		 	System.out.print("	\"description\":");
		 	System.out.print(" \"");
		 	System.out.print(obj.get("description"));
		 	System.out.println("\",");
		 	
		 	System.out.print("	\"publisher\":");
		 	System.out.print(" \"");
		 	System.out.print(obj.get("developer"));
		 	System.out.println("\",");

		 	System.out.print("	\"price\":");
		 	System.out.print(" \"");
		 	System.out.print(obj.get("price"));
		 	System.out.println("\",");
		 	
		 	System.out.print("	\"rating\": ");
		 	System.out.println(obj.get("rating"));
		 	System.out.print("}");
		 	
		 	
	
	 }
}
}
