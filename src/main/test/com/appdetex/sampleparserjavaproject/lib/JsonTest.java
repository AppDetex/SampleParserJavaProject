package com.appdetex.sampleparserjavaproject.lib;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import com.appdetex.sampleparserjavaproject.lib.xargs.Xargs;

class JsonTest {

    private Cons console = new Cons(System.out);
    Xargs xargs = new Xargs(console);
    private Json json = new Json(new Gson());
    Scrape scrape = new Scrape(json);

    @Test
    void toJson_Short_Pass() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put( "a", "aa" );
        this.json.toJson(params);

        // No asserts needed @fixme use annotations for exception checking
    }

    @Test
    void toJson_Medium_Pass() {
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
        this.json.toJson(params);

        // No asserts needed @fixme use annotations for exception checking
    }

    @Test
    void toJson_Null_PassEmptyJson() {
        Map<String, Object> params = new HashMap<String, Object>();
        assertTrue(!this.json.toJson(params).isEmpty());
    }

    @Test
    void toJson_PoorlyFormatted_Fail() {
    }
}