package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class FDiv implements Instruction {
    @Override
    public void eval(Frame frame) {
        float val2 = frame.popFloat();
        float val1 = frame.popFloat();
        frame.pushFloat(val1 / val2);
        advance(frame);
    }
}
