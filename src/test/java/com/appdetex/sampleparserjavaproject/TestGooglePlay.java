package com.appdetex.sampleparserjavaproject;

import com.appdetex.sampleparserjavaproject.coordinator.DataMiningCoordinator;
import com.appdetex.sampleparserjavaproject.dto.GooglePlayStorePageSummaryDto;
import com.appdetex.sampleparserjavaproject.formatter.FormattingException;
import com.appdetex.sampleparserjavaproject.formatter.JsonFormatter;
import com.appdetex.sampleparserjavaproject.parser.Parsers;
import com.appdetex.sampleparserjavaproject.parser.elementhandler.AppTitleHandler;
import com.appdetex.sampleparserjavaproject.scraper.GenericScraper;
import com.appdetex.sampleparserjavaproject.scraper.ScrapingException;
import com.appdetex.sampleparserjavaproject.util.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class TestMainApp {


    @Test
    void test_seek_happyPath() throws IOException, ScrapingException, FormattingException {

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

    //etc

}
