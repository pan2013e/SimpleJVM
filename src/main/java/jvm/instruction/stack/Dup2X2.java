package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.Slot;

public class Dup2X2 implements Instruction {
    @Override
    public void eval(Frame frame) {
        Slot v1 = frame.pop();
        Slot v2 = frame.pop();
        Slot v3 = frame.pop();
        Slot v4 = frame.pop();
        frame.push(v2);
        frame.push(v1);
        frame.push(v4);
        frame.push(v3);
        frame.push(v2);
        frame.push(v1);
        advance(frame);
    }
}
