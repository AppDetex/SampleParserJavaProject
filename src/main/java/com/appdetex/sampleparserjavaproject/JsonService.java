package com.appdetex.sampleparserjavaproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class JsonService<T> {
    private final Scraper scraper;
    private final Parser<T> parser;
    private final ObjectMapper objectMapper = new ObjectMapper();

    JsonService(Scraper scraper, Parser<T> parser) {
        this.scraper = scraper;
        this.parser = parser;
    }

    String getJson(String url) {
        return dataToJson(parser.parse(scraper.getHtml(url)));
    }

    private String dataToJson(T data) {
        try
        {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
