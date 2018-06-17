package com.appdetex.sampleparserjavaproject;

import com.squareup.moshi.Moshi;
import java.io.IOException;

/**
 * Main Java Class
 *
 * This class will use Jsoup to retrieve a provided URL
 * and parse out certain data, printing that data to
 * stdout in a JSON format.
 */
public class Main {
    private static final String FOUR_SPACES = "    ";

    private final PlayStoreClient playStoreClient;
    private final Moshi moshi;

    public Main(PlayStoreClient playStoreClient, Moshi moshi) {
        this.playStoreClient = playStoreClient;
        this.moshi = moshi;
    }

    public String fetchAppDetails(String url) throws IOException {
        PlayStoreApp playStoreApp = playStoreClient.get(url);

        return moshi.adapter(PlayStoreApp.class).indent(FOUR_SPACES).toJson(playStoreApp);
    }

    public static void main(String[] args ) throws IOException {
        if (args.length != 1) {
            throw new IllegalStateException("Usage: java -jar SampleParserJavaProject {Play Store URL}");
        }

        String url = args[0];

        Main app = new Main(new PlayStoreClient(new PlayStoreHtmlParser()), new Moshi.Builder().build());
        String appDetails = app.fetchAppDetails(url);

        System.out.println(appDetails);
    }
}
