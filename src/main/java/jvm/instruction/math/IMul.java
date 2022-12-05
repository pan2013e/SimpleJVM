package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class IMul implements Instruction {
    @Override
    public void eval(Frame frame) {
        int val2 = frame.popInt();
        int val1 = frame.popInt();
        frame.pushInt(val1 * val2);
        advance(frame);
    }
}
