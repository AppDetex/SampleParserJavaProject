package com.appdetex.sampleparserjavaproject.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class StoreLocationConfig {
    @Value("${google-play.location}")
    private String googlePlayLocation;

    @Value("${apple-store.location}")
    private String appleLocation;
}
