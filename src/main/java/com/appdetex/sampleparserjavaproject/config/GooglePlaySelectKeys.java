package com.appdetex.sampleparserjavaproject.config;

import com.appdetex.sampleparserjavaproject.parsing.stores.SelectKeys;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// NOTE - This should all be in a database

@Data
@Configuration
public class GooglePlaySelectKeys implements SelectKeys {
    @Value("${google-play.select-keys.title}")
    private String titleKey;

    @Value("${google-play.select-keys.description}")
    private String descriptionKey;

    @Value("${google-play.select-keys.publisher}")
    private String publisherKey;

    @Value("${google-play.select-keys.price}")
    private String priceKey;

    @Value("${google-play.select-keys.rating}")
    private String ratingKey;
}
