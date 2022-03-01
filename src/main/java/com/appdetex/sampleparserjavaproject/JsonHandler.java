package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;


public class JsonHandler {
    public static Map<String, WebScrapeItem> getWebScrapeConfig(String json) {
        Gson gson = new Gson();
        Type empMapType = new TypeToken<Map<String, WebScrapeItem>>() {}.getType();
        return gson.fromJson(json, empMapType);
    }

    public static String toJSON(Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }
}
