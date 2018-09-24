package com.appdetex.sampleparserjavaproject;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @ParameterizedTest
    @ValueSource(strings = {"free", "paid", "unreleased"})
    void testGetResult(String name) throws URISyntaxException, IOException {
        Path jsonPath = Paths.get(MainTest.class.getResource(
            String.format("/%s.json", name)
        ).toURI());
        JSONObject expected = new JSONObject(new String(Files.readAllBytes(jsonPath)));

        File input = new File(MainTest.class.getResource(
            String.format("/%s.html", name)
        ).toURI());
        Document doc = Jsoup.parse(input, "UTF-8");
        JSONObject actual = Main.getResult(doc);

        String[] keys = {"title", "description", "publisher", "price", "rating"};
        for (String key : keys) {
            Assertions.assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    void testMain() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        String[] args = {"https://play.google.com/store/apps/details?id=com.facebook.katana"};
        Main.main(args);
        Assertions.assertEquals(
            "{\n" +
            "    \"price\": \"0\",\n" +
            "    \"rating\": 4.1,\n" +
            "    \"description\": \"Keeping up with friends is faster and easier than ever. Share updates and photos, engage with friends and Pages, and stay connected to communities important to you. \",\n" +
            "    \"publisher\": \"Facebook\",\n" +
            "    \"title\": \"Facebook\"\n" +
            "}",
            outContent.toString()
        );

        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}