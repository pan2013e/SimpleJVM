package jvm.instruction;

import jvm.runtime.Frame;

public interface Instruction {

    default int offset() {
        return 1;
    }

    default void advance(Frame frame) { frame.pc += offset(); }

    void eval(Frame frame);

}
