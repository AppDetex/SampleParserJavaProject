package com.appdetex.sampleparserjavaproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class PlayStoreEntryTest {
    @Test
    public void TestPlayStoreEntryParsing() throws URISyntaxException, IOException {
        PlayStoreEntry obj = new PlayStoreEntry();
        String html = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/catan.html").toURI())));
        String output = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/output.json").toURI())));
        System.out.println("foo");
        Document doc = Jsoup.parse(html);
        obj.parseDocument(doc);
        PlayStoreEntry json = new Gson().fromJson(output, PlayStoreEntry.class);
        Assert.assertEquals(obj, json);
    }
            /*
    A a = new A();
    A aSpy = Mockito.spy(a);
Mockito.when(aSpy.method1()).thenReturn(5l);
*/
}