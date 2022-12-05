package jvm.instruction.constant;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

class IConst implements Instruction {

    private final int val;

    public IConst(int val) {
        this.val = val;
    }

    @Override
    public void eval(Frame frame) {
        frame.pushInt(val);
        advance(frame);
    }

}
