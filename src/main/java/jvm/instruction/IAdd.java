package jvm.instruction;

import jvm.Frame;

public class IAdd implements Instruction {

    @Override
    public void eval(Frame frame) {
        int val2 = frame.operandStack.pop();
        int val1 = frame.operandStack.pop();
        frame.operandStack.push(val1 + val2);
        advance(frame);
    }

}
