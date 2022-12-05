package jvm.instruction.constant;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class FConst implements Instruction {

    private final float val;

    public FConst(float val) {
        this.val = val;
    }

    @Override
    public void eval(Frame frame) {
        frame.pushFloat(val);
        advance(frame);
    }

}
