package com.appdetex.sampleparserjavaproject.lib;
import org.junit.Test;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ConsTest {

    Cons console = new Cons(System.out);

    @Test
    public void write_Output_Pass() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        this.console.write(" ");
        assertEquals("", outContent.toString());
    }
}



