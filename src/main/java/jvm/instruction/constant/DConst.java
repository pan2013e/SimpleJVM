package jvm.instruction.constant;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class DConst implements Instruction {

    private final double val;

    public DConst(double val) {
        this.val = val;
    }

    @Override
    public void eval(Frame frame) {
        frame.pushDouble(val);
        advance(frame);
    }

}
