package com.appdetex.sampleparserjavaproject.lib.json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Map;

public class JsonStrategyGson {

    public String toJsonPretty(Map<String, Object> data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(gson.toJson(data));
        return gson.toJson(je);
    }
}
