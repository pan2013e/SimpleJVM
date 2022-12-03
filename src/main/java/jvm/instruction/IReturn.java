package jvm.instruction;

import jvm.Frame;

public class IReturn implements Instruction {

    @Override
    public void eval(Frame frame) {
        int top = frame.operandStack.pop();
        System.out.println(top);
        advance(frame);
    }

}
