package jvm;

import jvm.runtime.MetaSpace;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SystemTest {

    private static final PrintStream console = System.out;
    private ByteArrayOutputStream out = null;

    public static void runMainMethod(String className) {
        Driver.main(new String[]{"-cp", "src/test/resources/class", className});
    }

    public static void assertEquals(String expected, String actual, String message) {
        if(expected.endsWith("\n")) {
            expected = expected.substring(0, expected.length() - 1);
        }
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(String expected, String actual) {
        assertEquals(expected, actual, "");
    }

    @BeforeEach
    void redirect() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void resetMetaSpace() {
        MetaSpace.init();
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
        assertEquals("""
                0
                1
                2
                """, out.toString().trim());
    }

    @Test
    void staticCallTest2() {
        runMainMethod("StaticCall2");
        assertEquals("1", out.toString().trim());
    }

    @Test
    void staticCallTest3() {
        runMainMethod("StaticCall3");
        assertEquals("""
                1
                1
                2
                """, out.toString().trim());
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
        assertEquals("""
                3628800
                55
                """,
                out.toString().trim());
    }

    @Test
    void palindromeProductTest() {
        runMainMethod("PalindromeProduct");
        assertEquals("906609", out.toString().trim());
    }

    @Test
    void classInitTest1() {
        runMainMethod("ClassInit1");
        assertEquals("""
                1
                2
                """,
                out.toString().trim());
    }

    @Test
    void classInitTest2() {
        runMainMethod("ClassInit2");
        assertEquals("""
                1
                2
                3
                """,
                out.toString().trim());
    }

    @Test
    void classInitTest3() {
        runMainMethod("ClassInit3");
        assertEquals("""
                1
                2
                3
                4
                """,
                out.toString().trim());
    }

    @Test
    void staticFieldTest() {
        runMainMethod("StaticField");
        assertEquals("""
                false
                \0
                0
                0
                0
                0
                0.0
                0.0
                false
                1
                3
                4
                5
                0
                1.0
                0.0
                """,
                out.toString().trim());
    }

    @Test
    void objectInitTest1() {
        runMainMethod("ObjectInit1");
        assertEquals("""
                1
                2
                """,
                out.toString().trim());
    }

    @Test
    void objectInitTest2() {
        runMainMethod("ObjectInit2");
        assertEquals("""
                1
                2
                3
                4
                """,
                out.toString().trim());
    }

    @Test
    void objectInitTest3() {
        runMainMethod("ObjectInit3");
        assertEquals("""
                1
                2
                3
                4
                3
                2
                """,
                out.toString().trim());
    }

    @Test
    void instanceFieldTest1() {
        runMainMethod("ObjectField1");
        assertEquals("""
                1
                5
                """,
                out.toString().trim());
    }

    @Test
    void instanceFieldTest2() {
        runMainMethod("ObjectField2");
        assertEquals("""
                1
                5
                1
                1
                4
                3
                """,
                out.toString().trim());
    }

    @Test
    void objectOverrideTest() {
        runMainMethod("ObjectOverride");
        assertEquals("""
                1
                2
                3
                1
                0
                3
                1
                0
                0
                1
                0
                3
                1
                0
                0
                """,
                out.toString().trim());
    }

    @Test
    void interfaceOverrideTest() {
        runMainMethod("InterfaceOverride");
        assertEquals("""
                1
                2
                2
                3
                1
                2
                3
                """,
                out.toString().trim());
    }

    @Test
    void defaultMethodTest() {
        runMainMethod("DefaultMethod");
        assertEquals("""
                2
                2
                1
                1
                """,
                out.toString().trim());
    }

    @Test
    void interfaceInitTest() {
        runMainMethod("InterfaceInit");
        assertEquals("""
                2
                1
                0
                1
                3
                0
                """,
                out.toString().trim());
    }


}
