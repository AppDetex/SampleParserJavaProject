package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class to store Data from a google app play store url
 */
public class AppData {

    private String title;
    private String description;
    private String publisher;
    private String price;
    private float rating;

    public AppData(String title, String description, String publisher, String price, float rating) {
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.price = price;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Use Gson to translate this object into a json string
     * We do serialize nulls
     *
     * @return this objects data as a json string
     */
    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(this);
    }
}
