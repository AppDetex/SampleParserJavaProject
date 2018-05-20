package com.appdetex.sampleparserjavaproject.config;

import com.appdetex.sampleparserjavaproject.parsing.stores.SelectKeys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// NOTE - This should all be in a database

@Data
@Configuration
public class AppleStoreSelectKeys implements SelectKeys {
    @Value("${apple-store.select-keys.title}")
    private String titleKey;

    @Value("${apple-store.select-keys.description}")
    private String descriptionKey;

    @Value("${apple-store.select-keys.publisher}")
    private String publisherKey;

    @Value("${apple-store.select-keys.price}")
    private String priceKey;

    @Value("${apple-store.select-keys.rating}")
    private String ratingKey;
}
