package jvm.instruction.constant;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

class LConst implements Instruction {

    private final long val;

    public LConst(long val) {
        this.val = val;
    }

    @Override
    public void eval(Frame frame) {
        frame.pushLong(val);
        advance(frame);
    }

}
