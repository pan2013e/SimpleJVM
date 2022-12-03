package jvm.instruction;

import jvm.Frame;

public class ILoad1 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.operandStack.push(frame.localVars.get(1));
        advance(frame);
    }

}
