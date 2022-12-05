package jvm.instruction.load;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class ILoad0 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.pushInt(frame.getInt(0));
        advance(frame);
    }

    @Override
    public int offset() {
        return 1;
    }

}
