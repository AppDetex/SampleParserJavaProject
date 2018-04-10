package com.appdetex.sampleparserjavaproject.lib.scrape;
import com.appdetex.sampleparserjavaproject.lib.json.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scrape {

    private Map<String, Object> data = new HashMap<String, Object>();
    private String url;
    private Json json;
    private ScrapeStrategyJsoup queryProvider;

    public Scrape(Json json, ScrapeStrategyJsoup queryProvider) {
        this.json = json;
        this.queryProvider = queryProvider;
    }

    public Scrape queryAttributes(List attributes) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ScrapeParameterException {
        this.resetData();

        Map<String, Object> tmpData = new HashMap<String, Object>();

        for (Object item : attributes) {
            String attr = this.queryProvider.getAttribute(item.toString());
            tmpData.put(item.toString(), attr);
        }

        this.setData(tmpData);

        return this;
    }

    public void init(String url) throws IOException {
        this.setUrl(url);
        this.queryProvider.setUrl(this.url);
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setData(Map<String, Object> data) {
        this.data = data;
    }

    private void resetData() {
        this.data.clear();
    }

    public String toJsonPretty() {
        return this.json.toJsonPretty(this.data);
    }
}