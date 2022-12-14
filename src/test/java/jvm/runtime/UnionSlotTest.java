package jvm.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UnionSlotTest {

    @Test
    void initAndSetTest() {
        final UnionSlot rus = UnionSlot.of((Instance) null);
        assertNull(rus.getRef(), "ref should be null");
        final Instance instance = new Instance();
        rus.setRef(instance);
        assertEquals(instance, rus.getRef(), "ref should be equal");

        final UnionSlot ius = UnionSlot.of(0);
        assertEquals(0, ius.getInt(), "int should be 0");
        ius.setInt(1);
        assertEquals(1, ius.getInt(), "int should be 1");

        final UnionSlot fus = UnionSlot.of(0.0f);
        assertEquals(0.0f, fus.getFloat(), "float should be 0");
        fus.setFloat(1.0f);
        assertEquals(1.0f, fus.getFloat(), "float should be 1");

        final UnionSlot lus = UnionSlot.of(0L);
        assertEquals(0L, lus.getLong(), "long should be 0");
        lus.setLong(1L);
        assertEquals(1L, lus.getLong(), "long should be 1");

        final UnionSlot dus = UnionSlot.of(0.0);
        assertEquals(0.0, dus.getDouble(), "double should be 0");
        dus.setDouble(1.0);
        assertEquals(1.0, dus.getDouble(), "double should be 1");

        final UnionSlot neo = UnionSlot.of(0);
        neo.set(UnionSlot.of(1));
        assertEquals(1, neo.getInt(), "int should be 1");
    }
}
