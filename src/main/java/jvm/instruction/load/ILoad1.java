package jvm.instruction.load;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;

public class ILoad1 implements Instruction {

    @Override
    public void eval(Frame frame) {
        frame.pushInt(frame.getInt(1));
        advance(frame);
    }

    @Override
    public int offset() {
        return 1;
    }

}
