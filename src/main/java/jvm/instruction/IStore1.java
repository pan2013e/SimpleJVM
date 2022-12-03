package jvm.instruction;

import jvm.Frame;

public class IStore1 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.localVars.put(1, frame.operandStack.pop());
        advance(frame);
    }

}
