package jvm.instruction;

import jvm.Frame;

public class IStore2 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.localVars.put(2, frame.operandStack.pop());
        advance(frame);
    }

}
