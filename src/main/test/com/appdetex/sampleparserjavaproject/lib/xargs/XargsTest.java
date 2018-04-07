package com.appdetex.sampleparserjavaproject.lib.xargs;

import com.appdetex.sampleparserjavaproject.lib.Cons;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class XargsTest {

    private Cons console = new Cons(System.out);
    private Xargs xargs = new Xargs(console);

    @Test
    void init_HappyBootstrap_Pass() {

        Map<String, Object> xargsOptions = new HashMap<String, Object>();
        xargsOptions.put( "opt", "u" );
        xargsOptions.put( "longOpt", "url" );
        xargsOptions.put( "hasArg", true );
        xargsOptions.put( "description", "url of play store app" );

        try {
            xargs.init(xargsOptions, new String[] { "-u", "abc" });

        } catch (XargsInputException | ParseException e) {
            fail("Missing params");
        }
    }

    @Test
    void init_MissingUrlParameterShowHelp_Fail() {

        Map<String, Object> xargsOptions = new HashMap<String, Object>();
        xargsOptions.put( "opt", "u" );
        xargsOptions.put( "longOpt", "url" );
        xargsOptions.put( "hasArg", true );
        xargsOptions.put( "description", "url of play store app" );

        try {
            xargs.init(xargsOptions, new String[] { "" });

        } catch (XargsInputException | ParseException e) {
            assertFalse(false);
        }
    }

    @Test
    void getExpectedParam_Pass() {

        Map<String, Object> xargsOptions = new HashMap<String, Object>();
        xargsOptions.put( "opt", "u" );
        xargsOptions.put( "longOpt", "url" );
        xargsOptions.put( "hasArg", true );
        xargsOptions.put( "description", "url of play store app" );

        try {
            xargs.init(xargsOptions, new String[] { "-u", "abc" });
            assertTrue(xargs.get("u").equals("abc"));

        } catch (XargsInputException | ParseException e) {
            fail("Missing params");
        }
    }
}