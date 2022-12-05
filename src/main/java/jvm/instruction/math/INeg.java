package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class INeg implements Instruction {

    @Override
    public void eval(Frame frame) {
        int val = frame.popInt();
        frame.pushInt(-val);
        advance(frame);
    }

}
