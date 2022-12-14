package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.Slot;

public class Dup2 implements Instruction {
    @Override
    public void eval(Frame frame) {
        final Slot v2 = frame.pop();
        final Slot v1 = frame.pop();
        frame.push(v1);
        frame.push(v2);
        frame.push(v1);
        frame.push(v2);
        advance(frame);
    }
}
