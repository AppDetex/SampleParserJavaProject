package com.appdetex.sampleparserjavaproject.lib;

import com.google.gson.Gson;
import static org.junit.jupiter.api.Assertions.*;
import com.appdetex.sampleparserjavaproject.lib.xargs.Xargs;

class ConsTest {

    private Cons console = new Cons(System.out);
    Xargs xargs = new Xargs(console);
    private Json json = new Json(new Gson());
    Scrape scrape = new Scrape(json);

    @org.junit.jupiter.api.Test
    void write_ShortString_Pass() {
        assertTrue(this.console.write("A"));
    }

    @org.junit.jupiter.api.Test
    void write_NoString_Pass() {
        assertTrue(this.console.write(""));
    }

    @org.junit.jupiter.api.Test
    void write_LongString_Pass() {
        assertTrue(this.console.write("AaaaaaaBbbbbbbCcccccc"));
    }
}