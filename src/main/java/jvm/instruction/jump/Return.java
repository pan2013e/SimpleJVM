package jvm.instruction.jump;

import jvm.instruction.Instruction;
import jvm.misc.Utils;
import jvm.runtime.Frame;

public class Return implements Instruction {

    @Override
    public void eval(Frame frame) {
        // Mocking System.out.println for now
        // getstatic "Field java/lang/System.out:Ljava/io/PrintStream;"
        // iload
        // invokevirtual "Method java/io/PrintStream.println:(I)V"
        // TODO
        Utils.doReturn0();
        advance(frame);
    }

}
