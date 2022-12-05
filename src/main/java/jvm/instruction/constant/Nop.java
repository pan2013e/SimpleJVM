package jvm.instruction.constant;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

@SuppressWarnings("unused")
public class Nop implements Instruction {

    @Override
    public void eval(Frame frame) {
    }

}
