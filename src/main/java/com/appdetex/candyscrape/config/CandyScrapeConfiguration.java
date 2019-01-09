package com.appdetex.candyscrape.config;

import com.appdetex.candyscrape.scraper.HttpDocumentRetriever;
import com.appdetex.candyscrape.scraper.IDocumentRetriever;
import com.appdetex.candyscrape.scraper.IScraper;
import com.appdetex.candyscrape.scraper.JsoupScraper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Main application configuration
 */
@Configuration
public class CandyScrapeConfiguration {

    @Bean
    public IDocumentRetriever documentRetriever() {
        return new HttpDocumentRetriever();
    }

    @Bean
    public IScraper googlePlayAppScraper() {
        return new JsoupScraper(documentRetriever());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return mapper;
    }
}
