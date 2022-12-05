package jvm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SystemTest {

    private static final PrintStream console = System.out;
    private ByteArrayOutputStream out = null;

    public static void runMainMethod(String className) {
        Driver.main(new String[]{"-cp", "src/test/resources/class", className});
    }

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
    void sum10Test() {
        runMainMethod("Sum10");
        assertEquals("55", out.toString().trim());
    }

    @Test
    void decrTest() {
        runMainMethod("Decr");
        assertEquals("0", out.toString().trim());
    }

    @Test
    void printlnMockTest() {
        runMainMethod("TestM");
        assertEquals("1", out.toString().trim());
    }

    @Test
    void staticCallTest() {
        runMainMethod("StaticCall");
        assertEquals("0\n1\n2", out.toString().trim());
    }

    @Test
    void staticCallTest2() {
        runMainMethod("StaticCall2");
        assertEquals("1", out.toString().trim());
    }

    @Test
    void staticCallTest3() {
        runMainMethod("StaticCall3");
        assertEquals("1\n1\n2", out.toString().trim());
    }

    @Test
    void fibonacciTest() {
        runMainMethod("Fibonacci");
        assertEquals("55", out.toString().trim());
    }

    @Test
    void nativeMethodTest() {
        runMainMethod("MyJVM");
        assertEquals("52", out.toString().trim());
    }

    @Test
    void fibFactTest() {
        runMainMethod("FibFact");
        assertEquals("3628800\n55", out.toString().trim());
    }

    @Test
    void palindromeProductTest() {
        runMainMethod("PalindromeProduct");
        assertEquals("906609", out.toString().trim());
    }

}
