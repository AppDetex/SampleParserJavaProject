package com.appdetex.sampleparserjavaproject.lib.json;
import java.util.Map;

public class Json {

    private JsonStrategyGson jsonProvider;

    public Json(JsonStrategyGson jsonProvider) {
        this.jsonProvider = jsonProvider;
    }

    public String toJsonPretty(Map<String, Object> data) {
        return this.jsonProvider.toJsonPretty(data);
    }
}
