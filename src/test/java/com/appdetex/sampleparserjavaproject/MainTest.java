package com.appdetex.sampleparserjavaproject;

import com.squareup.moshi.Moshi;
import java.io.IOException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

public class MainTest {
    @Test
    public void canSerializeAppToJSON() throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        PlayStoreClient playStoreClient = mock(PlayStoreClient.class);
        when(playStoreClient.get("fake-url")).thenReturn(
                new PlayStoreApp(
                        "Candy Line",
                        "is Chocolate-style eliminate of crush candy game, Thick chocolate style with beautiful background music, as well as simple operation, mobile syrup, gorgeous crush effects, bring you Infinite joy!",
                        "AnjunSang",
                        "$4.99",
                        4.3));

        Main app = new Main(playStoreClient, moshi);
        String appDetails = app.fetchAppDetails("fake-url");

        assertThat(appDetails, sameJSONAs("{" +
                "    \"title\": \"Candy Line\"," +
                "    \"description\": \"is Chocolate-style eliminate of crush candy game, Thick chocolate style with beautiful background music, as well as simple operation, mobile syrup, gorgeous crush effects, bring you Infinite joy!\"," +
                "    \"publisher\": \"AnjunSang\"," +
                "    \"price\": \"$4.99\"," +
                "    \"rating\": 4.3" +
                "}"));
    }
}