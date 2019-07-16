import com.appdetex.sampleparserjavaproject.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@RunWith(JUnit4.class)
public class RegExTest {

    Main main;

    @Before
    public void before() {
        main = new Main();
    }

    @Test
    public void testItempropOne() {
        String testString = "<meta itemprop=\"key\" content=\"value\" />";
        Map<String,String> testMap = main.parseStringToHashMap(testString);
        assertEquals(1, testMap.size());
        assertEquals("value", testMap.get("key"));
    }

    @Test
    public void testItempropThree() {
        String testString =
                //no match
                "itemprop=\"offers\" itemscope itemtype=\"https://schema.org/Offer\"><meta "+
                //matches
                "itemprop=\"url\" content=\"https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&amp;rdid=com.mojang.minecraftpe&amp;feature=md&amp;offerId\"><meta " +
                "itemprop=\"price\" content=\"$6.99\"></span></span>$6.99 Buy</button></span></div><c-data id=\"i17\" jsdata=\" GixM9d;_;18 v3Bbmc;unsupported;16 Lq9R7c;unsupported;19 QhZwQc;unsupported;20\"></c-data></c-wiz><c-data id=\"i16\" jsdata=\" sCuaec;_;7 OKeYaf;_;10 pH8t9b;_;8\"></c-data></c-wiz></div></div></div></div></div></div></div><c-data id=\"i7\" jsdata=\" OKeYaf;_;10\"></c-data></c-wiz><c-wiz jsrenderer=\"OvGHec\" jsshadow jsdata=\"deferred-i18\" data-p=\"%.@.[&quot;com.mojang.minecraftpe&quot;,7]]\" data-node-index=\"3;0\" jsmodel=\"hc6Ubd\"><c-wiz jsrenderer=\"mmmrlc\" jsshadow jsdata=\"deferred-i19\" data-p=\"%.@.[&quot;com.mojang.minecraftpe&quot;,7]]\" data-node-index=\"1;0\" jsmodel=\"hc6Ubd GVgNYb\"><div class=\"Rx5dXb\" jscontroller=\"HtFpZ\" jsaction=\"rcuQ6b:npT2md;tPOirb:iksoyf;\" data-slideable-portion-heuristic-width=\"12502\"><div class=\"awJjId\" jsname=\"PjUZJf\" jsaction=\"mousedown:q1TRae; mouseup: V43Ssf; mouseleave:V43Ssf\"><div class=\"gIyxRc\" jsname=\"ZlDilf\"></div></div><div jsname=\"CmYpTb\" class=\"JiLaSd u3EI9e\"><div jsname=\"pCbVjb\" class=\"SgoUSc\"><div class=\"MSLVtf Q4vdJd\" jsname=\"WR0adb\"><img src=\"https://i.ytimg.com/vi/gcf9FM4TbN4/hqdefault.jpg\" class=\"T75of DYfLw\" aria-hidden=\"true\"><div class=\"TdqJUe\"><button aria-label=\"Play Minecraft\" class=\"MMZjL lgooh  \" jscontroller=\"HnDLGf\" jsaction=\"click:axChxd\" jsname=\"pWHZ7d\" data-should-show-kav=\"true\" data-trailer-url=\"https://www.youtube.com/embed/gcf9FM4TbN4?ps=play&amp;vq=large&amp;rel=0&amp;autohide=1&amp;showinfo=0\" data-item-id=\"com.mojang.minecraftpe\" data-item-type=\"7\"></button></div></div><button class=\"Q4vdJd\" aria-label=\"Open screenshot 0\" jscontroller=\"DeWHJf\" jsaction=\"click:O1htCb\" jsname=\"WR0adb\" data-screenshot-item-index=\"0\"><img src=\"https://lh3.googleusercontent.com/28b1vxJQe916wOaSVB4CmcnDujk8M2SNaCwqtQ4cUS0wYKYn9kCYeqxX0uyI2X-nQv0=w720-h310\" srcset=\"https://lh3.googleusercontent.com/28b1vxJQe916wOaSVB4CmcnDujk8M2SNaCwqtQ4cUS0wYKYn9kCYeqxX0uyI2X-nQv0=w1440-h620 2x\" class=\"T75of DYfLw\" aria-hidden=\"true\" width=\"551\" height=\"310\" alt=\"Screenshot Image\" ";
        Map<String,String> testMap = main.parseStringToHashMap(testString);
        assertEquals(2, testMap.size());
        assertEquals("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&amp;rdid=com.mojang.minecraftpe&amp;feature=md&amp;offerId", testMap.get("url"));
        assertEquals("$6.99", testMap.get("price"));
    }

    @Test
    public void minecraftIntegrationTest() throws IOException {
        //base test
        Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US").get();
        HashMap<String,String> testMap = main.parseStringToHashMap(document.html());
        testMap = main.standardizeMapKeys(testMap);
        testMap = main.tryJsoup(document,testMap);

        //enforce that the regex and HTML still align.
        assertEquals(testMap.toString(),5, testMap.size());
        testMap.forEach((key,value) -> assertNotEquals("null value found for: "+key,null, value));
        testMap.forEach((key,value) -> assertNotEquals("empty string found for: "+ key,"", value));
    }


    @Test
    public void plagueIncIntegrationTest() throws IOException {
        //the title of this game has a period it in
        Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=com.miniclip.plagueinc&hl=en-US").get();
        HashMap<String,String> testMap = main.parseStringToHashMap(document.html());
        testMap = main.standardizeMapKeys(testMap);
        testMap = main.tryJsoup(document,testMap);

        //enforce that the regex and HTML still align.
        assertEquals(testMap.toString(),5, testMap.size());
        testMap.forEach((key,value) -> assertNotEquals("null value found for: "+key,null, value));
        testMap.forEach((key,value) -> assertNotEquals("empty string found for: "+ key,"", value));
    }



    @Test
    public void slideyBlockPuzzleIntegrationTest() throws IOException {
        //the title of this game has at (TM) symbol
        Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=com.mavis.slidey&hl=en-US").get();
        HashMap<String,String> testMap = main.parseStringToHashMap(document.html());
        testMap = main.standardizeMapKeys(testMap);
        testMap = main.tryJsoup(document,testMap);

        //enforce that the regex and HTML still align.
        assertEquals("Slidey®: Block Puzzle", testMap.get("title"));
        assertEquals(testMap.toString(),5, testMap.size());
        testMap.forEach((key,value) -> assertNotEquals("null value found for: "+key,null, value));
        testMap.forEach((key,value) -> assertNotEquals("empty string found for: "+ key,"", value));
    }
}
