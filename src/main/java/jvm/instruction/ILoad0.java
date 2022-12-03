package jvm.instruction;

import jvm.Frame;

public class ILoad0 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.operandStack.push(frame.localVars.get(0));
        advance(frame);
    }

}
