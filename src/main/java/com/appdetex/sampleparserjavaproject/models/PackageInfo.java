package com.appdetex.sampleparserjavaproject.models;

import com.appdetex.sampleparserjavaproject.utils.Constants;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PackageInfo implements IPacakgeInfo {
    
    private String title, description, publisher, price;
    private double rating;
    
    private PackageInfo () {
    }
    
    public void setProps (String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        title = document.select(Constants.SELECTOR_TITLE)
                .attr(Constants.SELECTOR_ATTR_CONTENT);
        description = document.select(Constants.SELECTOR_DESCRIPTION)
                .attr(Constants.SELECTOR_ATTR_CONTENT);
        publisher = document.select(Constants.SELECTOR_GENRE)
                            .parents()
                            .first()
                            .previousElementSibling()
                            .children()
                            .first()
                            .text();
        
        price = document.select(Constants.SELECTOR_PRICE)
                .attr(Constants.SELECTOR_ATTR_CONTENT);
        rating = Double.parseDouble(document.select(Constants.SELECTOR_RATING)
                .attr(Constants.SELECTOR_ATTR_CONTENT).substring(1));
    }
    
    public String getTitle () {
        return title;
    }
    
    public String getDescription () {
        return description;
    }
    
    public String getPublisher () {
        return publisher;
    }
    
    public String getPrice () {
        return price;
    }
    
    public double getAverageRating () {
        return rating;
    }
    
    public static String getJSON (String url) throws IOException {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setProps(url);
        Gson gson = new Gson();
        return gson.toJson(packageInfo);
    }
}
