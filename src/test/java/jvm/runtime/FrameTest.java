package jvm.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FrameTest {

    @Test
    void localVarsTest() {
        final Frame frame = Frame.getTestFrame(10, 10);
        frame.setInt(0, 100);
        assertEquals(100, frame.getInt(0), "setInt");

        frame.setFloat(1, 100.0f);
        assertEquals(100.0f, frame.getFloat(1), "setFloat");

        frame.setLong(1, 100L);
        assertEquals(100L, frame.getLong(1), "setLong");

        frame.setLong(1, Integer.MAX_VALUE + 100L);
        assertEquals(Integer.MAX_VALUE + 100L, frame.getLong(1), "setLong");

        frame.setDouble(1, 100.0d);
        assertEquals(100.0d, frame.getDouble(1), "setDouble");

        final Instance obj = new Instance();
        frame.setRef(2, obj);
        assertEquals(obj, frame.getRef(2), "setRef");
    }

    @Test
    void operandStackTest() {
        final Frame frame = Frame.getTestFrame(10, 10);
        frame.pushInt(100);
        assertEquals(100, frame.popInt(), "pushInt");

        frame.pushFloat(100.0f);
        assertEquals(100.0f, frame.popFloat(), "pushFloat");

        frame.pushLong(100L);
        assertEquals(100L, frame.popLong(), "pushLong");

        frame.pushLong(Integer.MAX_VALUE + 100L);
        assertEquals(Integer.MAX_VALUE + 100L, frame.popLong(), "pushLong");

        frame.pushDouble(100.0d);
        assertEquals(100.0d, frame.popDouble(), "pushDouble");

        final Instance obj = new Instance();
        frame.pushRef(obj);
        assertEquals(obj, frame.popRef(), "pushRef");
    }

}
