package com.appdetex.sampleparserjavaproject.lib;
import java.io.PrintStream;

/**
 * This will unable to handle complex screen drawings
 * and may need to be retooled if the project grows
 */
public class Cons {

    private PrintStream console;

    public Cons(PrintStream console) {
        this.console = console;
    }

    public boolean write(String message) {
        System.out.println(message);
        return true;
    }
}
