package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class Pop2 implements Instruction {
    @Override
    public void eval(Frame frame) {
        frame.pop();
        frame.pop();
        advance(frame);
    }
}
