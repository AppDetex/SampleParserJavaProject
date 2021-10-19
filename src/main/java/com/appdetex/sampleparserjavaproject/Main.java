package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.coordinator.DataMiningCoordinator;
import com.appdetex.sampleparserjavaproject.dto.GooglePlayStorePageSummaryDto;
import com.appdetex.sampleparserjavaproject.formatter.FormattingException;
import com.appdetex.sampleparserjavaproject.formatter.JsonFormatter;
import com.appdetex.sampleparserjavaproject.parser.Parsers;
import com.appdetex.sampleparserjavaproject.scraper.GenericScraper;
import com.appdetex.sampleparserjavaproject.scraper.ScrapingException;
import com.appdetex.sampleparserjavaproject.util.HttpMethod;
import lombok.extern.slf4j.Slf4j;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
/*
small scraping tool for parsing mobile app information from the Google Play app store's webpage.
a Java program which accepts a URL for a Google Play app page as a command line parameter, and returns
a JSON-formatted string containing relevant data for the app.
Required data:
- app title,
- first paragraph of the description,
- publisher name,
- price,
- and rating (average review score).

 For example, for the app "Minecraft" the scraper will be provided the URL
 https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
 and will output a string looking something like:

{
    "title": "Minecraft",
    "description": "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
    "publisher": "Mojang",
    "price": "$7.49",
    "rating": 4.6
}
 */
@Slf4j
public class Main {

    public static void main( String[] args ) throws ScrapingException, FormattingException {

        //Build our data miner
        DataMiningCoordinator dmc = DataMiningCoordinator.builder()
            .scraper(new GenericScraper())
            .parser(Parsers.GooglePlayStoreParser())
            .converter(GooglePlayStorePageSummaryDto::mapData)
            .formatter(new JsonFormatter())
            .build();

        String url = " https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US";
        final String output = dmc.execute(url, HttpMethod.GET);

        log.info(output);
    }
}
