package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class Pop implements Instruction {
    @Override
    public void eval(Frame frame) {
        frame.pop();
        advance(frame);
    }
}
