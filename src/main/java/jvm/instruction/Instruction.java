package jvm.instruction;

import jvm.Frame;

public interface Instruction {

    default int offset() {
        return 1;
    }

    default void advance(Frame frame) { frame.pc += offset(); }

    void eval(Frame frame);

}
