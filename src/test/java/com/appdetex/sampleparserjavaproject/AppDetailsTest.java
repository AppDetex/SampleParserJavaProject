package com.appdetex.sampleparserjavaproject;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


import static org.junit.Assert.*;

/**
 * Runs a list of urls through the scrapper and validates the AppDetails.
 */
@RunWith(Parameterized.class)
public class AppDetailsTest {

    private URL input;
    private AppDetails expected;

    public AppDetailsTest(URL input, AppDetails expected) {
        this.input = input;
        this.expected = expected;
    }

    /**
     * Supplies test data.
     */
    @Parameters
    public static Collection<Object[]> data() throws MalformedURLException {
        return Arrays.asList(new Object[][] {
                 {new URL("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe"),
                 new AppDetails("Minecraft",
                 "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. "
                 + "Play in creative mode with unlimited resources or mine deep into the world in survival mode, "
                 + "crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or "
                 + "with friends on mobile devices or Windows 10.",
                 "Mojang",
                 "$6.99",
                 new BigDecimal("4.50"))},
                 {new URL("https://play.google.com/store/apps/details?id=com.spotify.music"),
                 new AppDetails("Spotify - Music and Podcasts",
                 "Spotify is now free on mobile and tablet. Listen to the right music and podcasts, wherever you are. ",
                 "Spotify Ltd.",
                 "$0.00",
                 new BigDecimal("4.56"))},
                 {new URL("https://play.google.com/store/apps/details?id=com.bbb.jfix.boisebeerbuddies"),
                 new AppDetails("BoiseBeerBuddies",
                 "Brew News and Beer Events!",
                 "Chris Hillman",
                 "$0.00",
                 new BigDecimal("5.00"))},
                 {new URL("https://play.google.com/store/apps/details?id=com.beersmith.beersmith2full"),
                 new AppDetails("BeerSmith 3 Mobile Homebrewing",
                 "Need to create great homebrewing beer recipes on the go? BeerSmith 3, the world\u0027s top selling "
                 + "beer brewing software, now adds mead, wine and cider support. BeerSmith Mobile gives you all the "
                 + "tools to design, edit and brew your best beer from your phone or tablet. BeerSmith home brewing is "
                 + "our recipe cloud service and desktop BeerSmith program making it easy to create recipes from your "
                 + "desktop computer or phone and share them transparently. ",
                 "BeerSmith LLC",
                 "$7.99",
                 new BigDecimal("4.23"))},
                 {new URL("https://play.google.com/store/apps/details?id=com.novasa.bestbikingroads"),
                 new AppDetails("Best Biking Roads",
                 "Look up roads and routes in new areas for trip planning or weekend motorcycle rides.",
                 "Static Motion",
                 "$3.99",
                 new BigDecimal("3.8"))}
                });
    }

    @Test
    public void test() throws ScraperException {

        AppDetails actual = Scraper.parse(input);

        // Title, Publisher and Price a least likely to change
        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getPublisher(), expected.getPublisher());
        assertEquals(actual.getPrice(), expected.getPrice());
    }
}
