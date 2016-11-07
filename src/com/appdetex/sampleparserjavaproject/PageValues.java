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

    /**
     * This method takes entire html from a section of the page and parses the actual value
     * contained within a certain html tag that, it then assigns that value to one of the
     * class variables.
     *
     * @param tag - The beginning of the html tag under which the value we're looking
     *            for can be found.  This string should omit the closing html angle brace
     *            in order to account for any html tag attributes such as classes or ids
     *            EX:  "<div" ,"<span", "<p"
     * @param wholeString - The entire string of html for the section of the page we are
     *                    looking in
     * @param valueName - Tells which class variable this is going to be assigned to.
     */
    public void parseString(String tag, String wholeString, String valueName)
    {
        // Finds the first instance of 'tag' in the 'wholeString'
        int begOfOpenTagIndex = wholeString.indexOf(tag);
        // Finds the closing angle brace for 'tag' (this accounts for html tag attributes)
        int endOfOpenTagIndex = wholeString.indexOf('>', begOfOpenTagIndex);
        // Finds the beginning of the next html tag, which is the end of the value we are
        // parsing for
        int begOfCloseTagIndex = wholeString.indexOf('<', endOfOpenTagIndex);

        // Using the indices found above, finds the exact string value we need from
        // 'wholeString' without any html tags
        String res = wholeString.substring(endOfOpenTagIndex+1, begOfCloseTagIndex);

        // Assigns the resulting string to the correct value based on 'valueName'
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
        else {
            String res = price.substring(0, price.length() - " Buy".length());
            this.price = res;
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

    /**
     * Prints all of the class variables in a string formatted for JSON.
     */
    public void printValuesInJSON() {
        String res = "{\n" +
                "\t\"title\": \"" + this.title + "\",\n" +
                "\t\"description\": \"" + this.description + "\",\n" +
                "\t\"publisher\": \"" + this.publisher + "\",\n" +
                "\t\"price\": \"" + this.price + "\",\n" +
                "\t\"rating\": \"" + this.rating + "\"\n" +
                "}";

        System.out.println(res);
    }
}
