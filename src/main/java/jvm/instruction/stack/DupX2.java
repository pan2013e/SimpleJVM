package jvm.instruction.stack;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.Slot;

public class DupX2 implements Instruction {
    @Override
    @SuppressWarnings("Duplicates")
    public void eval(Frame frame) {
        Slot s1 = frame.pop();
        Slot s2 = frame.pop();
        Slot s3 = frame.pop();
        frame.push(s1);
        frame.push(s3);
        frame.push(s2);
        frame.push(s1);
        advance(frame);
    }
}
