package com.appdetex.sampleparserjavaproject.models;

import java.io.IOException;

/**
 * Yes, I know - adding an interface for such simple program might be a bit much,
 * but I just wanted to set a good example.  Using interfaces (in my experience) makes it easier
 * to write tests later - I presume the product in this imaginary scenario would expand to other app stores,
 * not just the Play store.
 */
public interface IPacakgeInfo {
    
    void setProps(String url) throws IOException;
    String getTitle ();
    String getDescription();
    String getPublisher();
    String getPrice();
    double getAverageRating();
}
