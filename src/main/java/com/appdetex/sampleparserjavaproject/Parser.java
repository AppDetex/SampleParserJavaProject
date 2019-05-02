package com.appdetex.sampleparserjavaproject;

public interface Parser<T> {

    T parse(String html);
}
