package jvm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SystemTest {

    private static final PrintStream console = System.out;
    private ByteArrayOutputStream out = null;

    @BeforeEach
    void redirect() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterAll
    static void recover() {
        System.setOut(console);
    }

    @Test
    void return1Test() throws IOException {
        Driver.runInterpreter("src/test/resources/class/Test.class");
        assertEquals("1", out.toString().trim());
    }

    @Test
    void sum10Test() throws IOException {
        Driver.runInterpreter("src/test/resources/class/Sum10.class");
        assertEquals("55", out.toString().trim());
    }

    @Test
    void decrTest() throws IOException {
        Driver.runInterpreter("src/test/resources/class/Decr.class");
        assertEquals("0", out.toString().trim());
    }

}
