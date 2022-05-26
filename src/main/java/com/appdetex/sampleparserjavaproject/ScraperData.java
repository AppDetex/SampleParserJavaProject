package com.appdetex.sampleparserjavaproject;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@JsonPOJOBuilder(withPrefix = "")
@Data
@Builder
public class ScraperData {
    private final String title;
    private final String publisher;
    private final String rating;
    private final String price;
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ScraperDataBuilder {
    }
}
