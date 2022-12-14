package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.Slot;

public class Swap implements Instruction {
    @Override
    public void eval(Frame frame) {
        Slot v1 = frame.pop();
        Slot v2 = frame.pop();
        frame.push(v1);
        frame.push(v2);
        advance(frame);
    }
}
