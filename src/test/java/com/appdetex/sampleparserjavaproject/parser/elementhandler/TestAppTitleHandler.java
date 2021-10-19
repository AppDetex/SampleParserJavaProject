package com.appdetex.sampleparserjavaproject.parser.elementhandler;

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
public class TestAppTitleHandler  {

    private Document loadDocument(final String docName) throws IOException {
        Path resourceDir = Paths.get("src","test","resources", "googleplaypages", docName);
        String absolutePath = resourceDir.toFile().getAbsolutePath();

        return Jsoup.parse(resourceDir.toFile(), "UTF-8", "http://example.com/");
    }


    @Test
    void test_seek_happyPath() throws IOException {
        //Given
        final Map<String, String> outputData = new HashMap<>();
        final Document doc = loadDocument("Minecraft.html");
        final AppTitleHandler handler = new AppTitleHandler();

        //When
        final Elements els = handler.seek(doc);
        //Then
        assertEquals(1, els.size());

        //When
        String data = handler.parse(els, outputData);
        //Then
        assertNotNull(data);
        assertEquals("Minecraft", data);
    }

    //etc

}
