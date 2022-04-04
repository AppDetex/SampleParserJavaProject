import com.appdetex.sampleparserjavaproject.client.WebClient;
import com.appdetex.sampleparserjavaproject.domain.ScraperResponse;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class WebClientTest {
    @Test
    void getScraperResponse_HappyPath() throws IOException {
        String testUrl1 = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US";
        WebClient client = new WebClient(testUrl1);
        ScraperResponse response = client.getScraperResponse();

        // checking a couple of fields here to make sure response is correct
        assertEquals("Minecraft", response.title);
        assertTrue(response.description.length() > 0);

        String testUrl2 = " https://play.google.com/store/apps/details?id=com.ninefolders.hd3";
        WebClient client2 = new WebClient(testUrl2);
        ScraperResponse response2 = client2.getScraperResponse();

        // checking a couple of fields here to make sure response is correct
        assertEquals("Nine - Email & Calendar", response2.title);
        assertTrue(response2.price.length() > 0);
    }

    @Test
    void getScraperResponse_Malformed_Url() {
        String urlToFail = "wrong_url";
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new WebClient(urlToFail),
                "WebClient() is failed"
        );
        assertTrue(thrown.getMessage().contains("Malformed URL"));
    }
}
