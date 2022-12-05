package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class LUShR implements Instruction {
    @Override
    public void eval(Frame frame) {
        long val = frame.popLong();
        frame.pushLong(val >>> 1);
        advance(frame);
    }
}
