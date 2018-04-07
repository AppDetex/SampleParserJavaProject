package com.appdetex.sampleparserjavaproject.lib;

import com.google.gson.Gson;

import java.util.Map;

public class Json {

    private Gson provider; // Refactor to IJsonProvider

    public Json(Gson provider) {
        this.provider = provider;
    }

    public String toJson(Map<String, Object> data) {
        return this.provider.toJson(data);
    }
}
