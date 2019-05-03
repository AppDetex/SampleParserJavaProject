package com.appdetex.sampleparserjavaproject.googlePlay;

import com.appdetex.sampleparserjavaproject.Scraper;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class AppScraper implements Scraper {
    private final CloseableHttpClient client;

    public AppScraper(CloseableHttpClient client) {
        this.client = client;
    }

    @Override
    public String getHtml(String url) {
        if (Strings.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("url must be non-empty");
        }

        try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {

            int responseCode = response.getStatusLine().getStatusCode();

            if (responseCode != 200) {
                throw new RuntimeException("response code invalid for processing. code=" + responseCode);
            }

            return CharStreams.toString(new InputStreamReader(response.getEntity().getContent(), Charsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
