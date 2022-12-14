package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.Slot;

public class Dup2X1 implements Instruction {
    @Override
    @SuppressWarnings("Duplicates")
    public void eval(Frame frame) {
        Slot v1 = frame.pop();
        Slot v2 = frame.pop();
        Slot v3 = frame.pop();
        frame.push(v2);
        frame.push(v1);
        frame.push(v3);
        frame.push(v2);
        frame.push(v1);
        advance(frame);
    }
}
