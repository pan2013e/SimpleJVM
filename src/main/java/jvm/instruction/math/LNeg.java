package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class LNeg implements Instruction {
    @Override
    public void eval(Frame frame) {
        long val = frame.popLong();
        frame.pushLong(-val);
        advance(frame);
    }
}
