package jvm.runtime;

import jvm.misc.Utils;
import lombok.Getter;

@Getter
public final class Field {

    int accessFlags;
    String name;
    String descriptor;
    Class clazz;
    UnionSlot val;

    public Field(int accessFlags, String name, String descriptor) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.clazz = null;
        this.val = null;
    }

    public void init() {
        switch (descriptor) {
            case "Z", "B", "C", "S", "I" -> val = UnionSlot.of(0);
            case "J" -> val = UnionSlot.of(0L);
            case "F" -> val = UnionSlot.of(0f);
            case "D" -> val = UnionSlot.of(0d);
            default -> val = UnionSlot.of(((Instance) null));
        }
    }

    public void get(Frame frame) {
        switch (descriptor) {
            case "Z", "B", "C", "S", "I" -> frame.pushInt(val.getInt());
            case "J" -> frame.pushLong(val.getLong());
            case "F" -> frame.pushFloat(val.getFloat());
            case "D" -> frame.pushDouble(val.getDouble());
            default -> frame.pushRef(val.getRef());
        }
    }

    public void set(Frame frame) {
        switch (descriptor) {
            case "Z", "B", "C", "S", "I" -> val.setInt(frame.popInt());
            case "J" -> val.setLong(frame.popLong());
            case "F" -> val.setFloat(frame.popFloat());
            case "D" -> val.setDouble(frame.popDouble());
            default -> val.setRef(frame.popRef());
        }
    }

    public void set(UnionSlot neo) {
        val.set(neo);
    }

    public boolean isStatic() {
        return Utils.isStatic(accessFlags);
    }

}
