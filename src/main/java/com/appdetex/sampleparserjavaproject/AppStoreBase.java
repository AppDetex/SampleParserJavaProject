package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

/**
 * AppStoreBase
 *
 * Abstract base class implementing the basics of an AppStoreEntry object.
 * This should be extended to add support for other app stores.
 */
public abstract class AppStoreBase implements IAppStoreEntry {

    protected String title;
    protected String author;
    protected String rating;
    protected String description;
    protected String price;


    // getters, since the values should be set in parseDocument()
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getRating() {
        return rating;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getPrice() {
        return price;
    }

    /**
     * Returns the JSON string for this object
     * @return The JSON representation of this object
     */
    @Override
    public String toJSON() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(this);
    }



    @Override
    public int hashCode() {
        return Objects.hash(this.author, this.description, this.title, this.price, this.rating);
    }

    @Override
    public boolean equals(Object obj2) {
        return this.hashCode() == obj2.hashCode();
    }
}
