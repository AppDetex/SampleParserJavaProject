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
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class TestGooglePlay {

    //Object mapper for helping check return value
    final ObjectMapper objMapper = new ObjectMapper();

    //Build our data miner
    final DataMiningCoordinator dmc = DataMiningCoordinator.builder()
            .scraper(new GenericScraper())
            .parser(Parsers.GooglePlayStoreParser())
            .converter(GooglePlayStorePageSummaryDto::mapData)
            .formatter(new JsonFormatter())
            .build();

    @Test
    void test_minecraft() throws IOException, ScrapingException, FormattingException {
        // Parse
        final String url = " https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US";
        final String objStr = dmc.execute(url, HttpMethod.GET);

        // Build an object from the JSON
        GooglePlayStorePageSummaryDto dto = objMapper.readValue(objStr, GooglePlayStorePageSummaryDto.class);

        // Validate
        assertEquals("Minecraft", dto.title);
        assertEquals("Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.\n", dto.firstParagraph);
        assertEquals("Mojang", dto.publisherName);
        assertEquals("$7.49", dto.price);
        assertEquals(4.6f, dto.rating);
    }


    @Test
    void test_monopoly() throws IOException, ScrapingException, FormattingException {
        //Parse
        final String url = "https://play.google.com/store/apps/details?id=com.marmalade.monopoly&hl=en-US";
        final String objStr = dmc.execute(url, HttpMethod.GET);

        // Build an object from the JSON
        GooglePlayStorePageSummaryDto dto = objMapper.readValue(objStr, GooglePlayStorePageSummaryDto.class);

        assertEquals("MONOPOLY - Classic Board Game", dto.title);
        assertEquals("We at Marmalade Game Studio are proud to bring this classic board game to mobile! Buy, sell and scheme your way to riches wherever you are. Play face-to-face with your friends and family with integrated multiplayer video chat! It’s the Hasbro MONOPOLY board game with no ads online and offline!\n", dto.firstParagraph);
        assertEquals("Marmalade Game Studio", dto.publisherName);
        assertEquals("$3.99", dto.price);
        assertEquals(4.3f, dto.rating);
    }


    @Test
    void test_superMarioRun() throws IOException, ScrapingException, FormattingException {
        //Parse
        final String url = "https://play.google.com/store/apps/details?id=com.nintendo.zara&hl=en-US";
        final String objStr = dmc.execute(url, HttpMethod.GET);

        // Build an object from the JSON
        GooglePlayStorePageSummaryDto dto = objMapper.readValue(objStr, GooglePlayStorePageSummaryDto.class);

        assertEquals("Super Mario Run", dto.title);
        assertEquals("A new kind of Mario game that you can play with one hand.\n", dto.firstParagraph);
        assertEquals("Nintendo Co., Ltd.", dto.publisherName);
        assertEquals("0", dto.price);
        assertEquals(3.8f, dto.rating);
    }


    @Test
    void test_musicEditorPro() throws IOException, ScrapingException, FormattingException {
        //Parse
        final String url = "https://play.google.com/store/apps/details?id=com.binghuo.audioeditor.mp3editor.musiceditor.pro&hl=en-US";
        final String objStr = dmc.execute(url, HttpMethod.GET);

        // Build an object from the JSON
        GooglePlayStorePageSummaryDto dto = objMapper.readValue(objStr, GooglePlayStorePageSummaryDto.class);

        assertEquals("Music Editor Pro", dto.title);
        assertEquals("Music Editor\n", dto.firstParagraph);
        assertEquals("Pony Mobile", dto.publisherName);
        assertEquals("$0.99", dto.price);
        assertEquals(4.7f, dto.rating);
    }

}
