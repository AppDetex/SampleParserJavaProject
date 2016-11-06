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

    PageValues(){
        this.title = "";
        this.description = "";
        this.publisher = "";
        this.price = "";
        this.rating = 0;
    }

    public void parseString(String tag, String wholeString, String valueName)
    {
        int begOfOpenTagIndex = wholeString.indexOf(tag);
        int endOfOpenTagIndex = wholeString.indexOf('>', begOfOpenTagIndex);
        int begOfCloseTagIndex = wholeString.indexOf('<', endOfOpenTagIndex);

        String res = wholeString.substring(endOfOpenTagIndex+1, begOfCloseTagIndex);

        switch (valueName)
        {
            case "title":
                setTitle(res);
                break;
            case "description":
                setDescription(res);
                break;
            case "publisher":
                setPublisher(res);
                break;
            case "price":
                setPrice(res);
                break;
            case "rating":
                setRating(Double.parseDouble(res));
                break;
            default:
                System.out.println("Unexpected value name.");
        }
    }

    // /////////////// Getters and Setters /////////////////////////

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title.replaceAll("\n", "").substring(1);
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description.replaceAll("\n", "").substring(1);
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher.replaceAll("\n", "").substring(1);
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        if(price.contains("Install"))
            this.price = "free";
        else {
            String res = price.substring(0, price.length() - " Buy".length());
            this.price = res.replaceAll("\n", "");
        }
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public void printValuesInJSON() {
        String res = "{\n" +
                "\t\"title\": \"" + this.title + "\",\n" +
                "\t\"description\": \"" + this.description + "\",\n" +
                "\t\"publisher\": \"" + this.publisher + "\",\n" +
                "\t\"price\": \"" + this.price + "\",\n" +
                "\t\"rating\": \"" + this.rating + "\",\n" +
                "}";

        System.out.println(res);
    }
}
