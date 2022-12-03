package jvm.instruction;

import jvm.Frame;

public class IStore0 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.localVars.put(0, frame.operandStack.pop());
        advance(frame);
    }

}
