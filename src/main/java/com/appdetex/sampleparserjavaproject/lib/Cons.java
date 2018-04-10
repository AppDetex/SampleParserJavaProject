package com.appdetex.sampleparserjavaproject.lib;
import java.io.PrintStream;

public class Cons {

    private PrintStream console;

    public Cons(PrintStream console) {
        this.console = console;
    }

    public void write(String message) {
        this.console.println(message);
    }
}
