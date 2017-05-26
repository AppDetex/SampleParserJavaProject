package com.appdetex.sampleparserjavaproject;

import java.io.IOException;
import java.math.BigDecimal;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;

/**
 * Extract the app details using the given URL.
 * @author Srisarguru
 *
 */
public class Extractor {
	private String appURL;
	
	public Extractor(String url){
		this.appURL=url;
	}
	
	public String extractAndBuildJSON() throws IOException{
		Document doc = Jsoup.connect(appURL).get(); 
		AppDetails details=new AppDetails();
		details.setAppTitle(doc.select("div.id-app-title").text());
		details.setAppDescription(extractDescription(doc));
		details.setAppPrice(doc.select("meta[itemprop=price]").first().attr("content"));
		details.setAppPublisher(doc.select("a[class=document-subtitle primary]").first().text());
		details.setAppRating(new BigDecimal(doc.select("div.score").text()));
		
		return jsonObject(details);

	}
	
	/**
	 * Extract the first paragraph of the app description.
	 * @param doc
	 * @return
	 */
	private String extractDescription(Document doc){
		
		Document jsoupDoc = Jsoup.parse(doc.select("div[jsname=C4s9Ed]").first().toString());
        jsoupDoc.outputSettings(new OutputSettings().prettyPrint(false));
        jsoupDoc.select("br").after("lb ");
        String description1 = jsoupDoc.text();
        String[] des=description1.split("lb lb");
        
		return des[0].replaceAll("lb", "");
	}
	
	/**
	 * Convert app details to a json object
	 * @param details
	 * @return
	 */
	private String jsonObject(AppDetails details){
		
		JSONObject jsonAppDetails = new JSONObject();
		jsonAppDetails.put("title",details.getAppTitle());
		jsonAppDetails.put("description",details.getAppDescription());
		jsonAppDetails.put("publisher",details.getAppPublisher());
		jsonAppDetails.put("price",details.getAppPrice());
		jsonAppDetails.put("rating",details.getAppRating());
		
		return jsonAppDetails.toString(5).replace("\\", "");
	}
}
