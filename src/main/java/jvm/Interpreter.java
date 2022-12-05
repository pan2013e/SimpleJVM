package jvm;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;
import jvm.runtime.VMStack;

public class Interpreter {

    public static void run() {
        VMStack env = MetaSpace.getMainEnv();
        while (!env.isEmpty()) {
            Frame frame = env.peek();
            Instruction inst = frame.getInstruction();
            inst.eval(frame);
        }
    }

}
