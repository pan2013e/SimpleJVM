package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.Slot;

public class Dup implements Instruction {
    @Override
    public void eval(Frame frame) {
        final Slot val = frame.pop();
        frame.push(val);
        frame.push(val);
        advance(frame);
    }
}
