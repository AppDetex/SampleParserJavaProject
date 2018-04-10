package com.appdetex.sampleparserjavaproject.lib.json;
import com.appdetex.sampleparserjavaproject.lib.Cons;
import com.appdetex.sampleparserjavaproject.lib.scrape.Scrape;
import com.appdetex.sampleparserjavaproject.lib.scrape.ScrapeStrategyJsoup;
import com.appdetex.sampleparserjavaproject.lib.xargs.Xargs;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertTrue;

public class JsonTest {

    Cons console = new Cons(System.out);
    Xargs xargs = new Xargs();
    JsonStrategyGson jsonStrategyGson = new JsonStrategyGson();
    Json json = new Json(jsonStrategyGson);
    ScrapeStrategyJsoup scrapeStrategy = new ScrapeStrategyJsoup();
    Scrape scrape = new Scrape(json, scrapeStrategy);

    @Test
    public void toJson_Short_Pass() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put( "a", "aa" );
        this.json.toJsonPretty(params);
    }

    @Test
    public void toJson_Medium_Pass() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put( "a", "aa" );
        params.put( "b", "bb" );
        params.put( "c", true );
        params.put( "d", "dd" );
        params.put( "e", "ee" );
        params.put( "f", "ff" );
        params.put( "g", "gg" );
        params.put( "h", "hh" );
        params.put( "i", "ii" );
        params.put( "j", "jj" );
        params.put( "k", "kk" );
        params.put( "l", "ll" );
        params.put( "m", "mm" );
        params.put( "n", "nn" );
        params.put( "o", "oo" );
        params.put( "p", "pp" );
        params.put( "q", "qq" );
        params.put( "r", "rr" );
        this.json.toJsonPretty(params);
    }

    @Test
    public void toJson_Null_PassEmptyJson() {
        Map<String, Object> params = new HashMap<String, Object>();
        assertTrue(!this.json.toJsonPretty(params).isEmpty());
    }

    @Test
    public void toJson_PoorlyFormatted_Fail() {
    }
}