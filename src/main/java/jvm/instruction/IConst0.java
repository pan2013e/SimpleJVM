package jvm.instruction;

import jvm.Frame;

public class IConst0 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.operandStack.push(0);
        advance(frame);
    }

}
