package jvm.instruction;

import jvm.Frame;

public class IConst1 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.operandStack.push(1);
        advance(frame);
    }

}
