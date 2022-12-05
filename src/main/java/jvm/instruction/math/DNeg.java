package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class DNeg implements Instruction {
    @Override
    public void eval(Frame frame) {
        double val = frame.popDouble();
        frame.pushDouble(-val);
        advance(frame);
    }
}
