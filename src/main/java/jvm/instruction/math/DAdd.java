package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class DAdd implements Instruction {
    @Override
    public void eval(Frame frame) {
        double val2 = frame.popDouble();
        double val1 = frame.popDouble();
        frame.pushDouble(val1 + val2);
        advance(frame);
    }
}
