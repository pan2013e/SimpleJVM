package jvm.instruction.jump;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;

public class IReturn implements Instruction {

    @Override
    public void eval(Frame frame) {
        int ret = frame.popInt();
        MetaSpace.getMainEnv().pop();
        MetaSpace.getMainEnv().peek().pushInt(ret);
        advance(frame);
    }

}
