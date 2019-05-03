package com.appdetex.sampleparserjavaproject;

public interface Parser<T> {

    /**
     * Returns a pojo representing data parsed from the input html content.
     *
     * @param html
     * @return parsed data
     */
    T parse(String html);
}
