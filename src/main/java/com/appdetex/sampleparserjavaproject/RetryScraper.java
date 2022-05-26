package com.appdetex.sampleparserjavaproject;

import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.Status;
import com.evanlennick.retry4j.config.RetryConfig;
import com.evanlennick.retry4j.config.RetryConfigBuilder;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class RetryScraper {
    private static final int NUM_TRIES = Integer.getInteger("maxNumberOfTries", 10);
    private static final int DELAY_BETWEEN_TRIES = Integer.getInteger("delayBetweenTriesInMilliseconds", 250);

    private RetryConfig config;
    private Scraper scraper;

    public RetryScraper(String url, Map<String, String> model) {
        scraper = new Scraper(url, model);
        config = new RetryConfigBuilder()
                .retryOnSpecificExceptions(IOException.class, IllegalStateException.class)
                .withMaxNumberOfTries(NUM_TRIES)
                .withDelayBetweenTries(DELAY_BETWEEN_TRIES, ChronoUnit.MILLIS)
                .withExponentialBackoff()
                .build();
    }

    public String process() {
        Status<String> status = new CallExecutorBuilder<>().config(config).build().execute(scraper);
        return status.getResult();
    }

}
