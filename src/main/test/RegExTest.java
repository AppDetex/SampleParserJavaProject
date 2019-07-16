import com.appdetex.sampleparserjavaproject.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegExTest {

    Main main;

    @BeforeEach
    private void beforeEach() {
        main = new Main();
    }

    @Test
    public void basicTest() {
        String testString = "<meta itemprop=\"key\" content=\"value\" />";
        Map<String,String> testMap = main.parseStringToMap(testString);
        assertEquals(1, testMap.size());
        assertEquals("value", testMap.get("key"));
    }

    @Test
    public void hugePayloadTest() {
        String testString =
                //no match
                "itemprop=\"offers\" itemscope itemtype=\"https://schema.org/Offer\"><meta "+
                //matches
                "itemprop=\"url\" content=\"https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&amp;rdid=com.mojang.minecraftpe&amp;feature=md&amp;offerId\"><meta " +
                "itemprop=\"price\" content=\"$6.99\"></span></span>$6.99 Buy</button></span></div><c-data id=\"i17\" jsdata=\" GixM9d;_;18 v3Bbmc;unsupported;16 Lq9R7c;unsupported;19 QhZwQc;unsupported;20\"></c-data></c-wiz><c-data id=\"i16\" jsdata=\" sCuaec;_;7 OKeYaf;_;10 pH8t9b;_;8\"></c-data></c-wiz></div></div></div></div></div></div></div><c-data id=\"i7\" jsdata=\" OKeYaf;_;10\"></c-data></c-wiz><c-wiz jsrenderer=\"OvGHec\" jsshadow jsdata=\"deferred-i18\" data-p=\"%.@.[&quot;com.mojang.minecraftpe&quot;,7]]\" data-node-index=\"3;0\" jsmodel=\"hc6Ubd\"><c-wiz jsrenderer=\"mmmrlc\" jsshadow jsdata=\"deferred-i19\" data-p=\"%.@.[&quot;com.mojang.minecraftpe&quot;,7]]\" data-node-index=\"1;0\" jsmodel=\"hc6Ubd GVgNYb\"><div class=\"Rx5dXb\" jscontroller=\"HtFpZ\" jsaction=\"rcuQ6b:npT2md;tPOirb:iksoyf;\" data-slideable-portion-heuristic-width=\"12502\"><div class=\"awJjId\" jsname=\"PjUZJf\" jsaction=\"mousedown:q1TRae; mouseup: V43Ssf; mouseleave:V43Ssf\"><div class=\"gIyxRc\" jsname=\"ZlDilf\"></div></div><div jsname=\"CmYpTb\" class=\"JiLaSd u3EI9e\"><div jsname=\"pCbVjb\" class=\"SgoUSc\"><div class=\"MSLVtf Q4vdJd\" jsname=\"WR0adb\"><img src=\"https://i.ytimg.com/vi/gcf9FM4TbN4/hqdefault.jpg\" class=\"T75of DYfLw\" aria-hidden=\"true\"><div class=\"TdqJUe\"><button aria-label=\"Play Minecraft\" class=\"MMZjL lgooh  \" jscontroller=\"HnDLGf\" jsaction=\"click:axChxd\" jsname=\"pWHZ7d\" data-should-show-kav=\"true\" data-trailer-url=\"https://www.youtube.com/embed/gcf9FM4TbN4?ps=play&amp;vq=large&amp;rel=0&amp;autohide=1&amp;showinfo=0\" data-item-id=\"com.mojang.minecraftpe\" data-item-type=\"7\"></button></div></div><button class=\"Q4vdJd\" aria-label=\"Open screenshot 0\" jscontroller=\"DeWHJf\" jsaction=\"click:O1htCb\" jsname=\"WR0adb\" data-screenshot-item-index=\"0\"><img src=\"https://lh3.googleusercontent.com/28b1vxJQe916wOaSVB4CmcnDujk8M2SNaCwqtQ4cUS0wYKYn9kCYeqxX0uyI2X-nQv0=w720-h310\" srcset=\"https://lh3.googleusercontent.com/28b1vxJQe916wOaSVB4CmcnDujk8M2SNaCwqtQ4cUS0wYKYn9kCYeqxX0uyI2X-nQv0=w1440-h620 2x\" class=\"T75of DYfLw\" aria-hidden=\"true\" width=\"551\" height=\"310\" alt=\"Screenshot Image\" " +
                "itemprop=\"description\" content=\"Explore! (infinite)*[] worlds and -_+=b,'uild everything;:' f?!@#$%^&*()_-+=:;',.?/1234567890rom\"><h2 aria-label=\"Description\"></h2><div jscontroller=\"IsfMIf\" jsaction=\"rcuQ6b:npT2md\" class=\"PHBdkd\" data-content-height=\"144\" jsshadow><div jsname=\"bN97Pc\" class=\"DWPxHb\" ";
        Map<String,String> testMap = main.parseStringToMap(testString);
        assertEquals(3, testMap.size());
        assertEquals("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&amp;rdid=com.mojang.minecraftpe&amp;feature=md&amp;offerId", testMap.get("url"));
        assertEquals("$6.99", testMap.get("price"));
        assertEquals("Explore! (infinite)*[] worlds and -_+=b,'uild everything;:' f?!@#$%^&*()_-+=:;',.?/1234567890rom", testMap.get("description"));

    }

    @Test
    public void googleIntegrationTest() {
        String response = main.get("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US");
        Map<String,String> testMap = main.parseStringToMap(response);
        testMap = main.standardizeMapKeys(testMap);

        //enforce that the regex and HTML still align.
        assertEquals(5, testMap.size());
    }
}
