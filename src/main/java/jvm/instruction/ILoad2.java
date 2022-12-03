package jvm.instruction;

import jvm.Frame;

public class ILoad2 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.operandStack.push(frame.localVars.get(2));
        advance(frame);
    }

}
