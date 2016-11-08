package com.appdetex.sampleparserjavaproject;


/**
 * Created by bwgar on 11/5/2016.
 *
 */
public class PageValues {

    private String title;
    private String description;
    private String publisher;
    private String price;
    private double rating;

    /**
     * Constructor -
     *      Takes no arguments and only initializes all the class variables.
     */
    PageValues(){
        this.title = "";
        this.description = "";
        this.publisher = "";
        this.price = "";
        this.rating = 0;
    }

    // /////////////// Getters and Setters /////////////////////////

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        // If we find "Install" in 'price', the app is free.
        if(price.contains("Install"))
            this.price = "Free";
        // Else, we remove " Buy" from the end of 'price' and use the remaining string as the
        // value we need
        else
            this.price = price.substring(0, price.length() - " Buy".length());

    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

}
