package jvm.instruction.jump;

import jvm.instruction.Instruction;
import jvm.misc.Utils;
import jvm.runtime.Frame;

public class IReturn implements Instruction {

    @Override
    public void eval(Frame frame) {
        Utils.doReturn1();
        advance(frame);
    }

}
