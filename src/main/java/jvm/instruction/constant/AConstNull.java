package jvm.instruction.constant;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

@SuppressWarnings("unused")
public class AConstNull implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.pushRef(null);
        advance(frame);
    }

}
