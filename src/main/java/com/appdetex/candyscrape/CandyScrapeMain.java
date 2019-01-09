package com.appdetex.candyscrape;

import com.appdetex.candyscrape.scraper.IScraper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class CandyScrapeMain implements CommandLineRunner {

    @Qualifier("targetMappings")
    private final Map<String, Class> targetMappings;
    private final IScraper scraper;
    private final ObjectMapper outputMapper;

    @Value("${build.version}")
    String buildVersion;

    @Value("${application.name}")
    String appName;

    public static void main(String[] args) {
        SpringApplication.run(CandyScrapeMain.class, args);
    }

    @Override
    public void run(String... args) {
        Class scraperModel;
        URL target;
        String[] parsedArguments;
        HelpFormatter formatter = new HelpFormatter();
        DefaultParser clParse = new DefaultParser();
        Options opts = new Options();
        String usage = appName + "-" + buildVersion + " <URL>";

        try {
            CommandLine cmdLine = clParse.parse(opts, args);
            parsedArguments = cmdLine.getArgs();
        } catch (ParseException e) {
            formatter.printHelp(usage, opts);
            return;
        }

        if (parsedArguments.length != 1) {
            formatter.printHelp(usage, opts);
            return;
        }

        try {
            target = new URL(parsedArguments[0]);
            scraperModel = targetMappings.get(target.getHost() + target.getPath());
            Assert.notNull(scraperModel, "No model exists that allows the supplied URL to be parsed.");
            Assert.notNull(target, "The supplied URL is not valid.");

            Object scraped = scraper.scrape(target.toString(), scraperModel);
            Assert.notNull(scraped, "The supplied URL could not be scraped.");
            String mapped = outputMapper.writeValueAsString(scraped);

            System.out.println(mapped);
        } catch (MalformedURLException e) {
            log.error("Failed to parse URL {}", args[0], e);
        } catch (IOException e) {
            log.error("Failed encode output model for URL {}", args[0], e);
        }
    }
}