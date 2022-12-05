package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class FNeg implements Instruction {
    @Override
    public void eval(Frame frame) {
        float val = frame.popFloat();
        frame.pushFloat(-val);
        advance(frame);
    }
}
