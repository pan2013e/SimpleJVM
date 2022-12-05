package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class LMul implements Instruction {
    @Override
    public void eval(Frame frame) {
        long val2 = frame.popLong();
        long val1 = frame.popLong();
        frame.pushLong(val1 * val2);
        advance(frame);
    }
}
