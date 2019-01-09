package com.appdetex.candyscrape.config;

import com.appdetex.candyscrape.scraper.model.GooglePlayAppModel;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * Configuration mapping domains to their respective scraper models.
 */
@Configuration
public class TargetModelConfiguration {

    @Bean
    public Map<String, Class> targetMappings() {
        return ImmutableMap.of(
            "play.google.com/store/apps/details", GooglePlayAppModel.class
        );
    }
}
