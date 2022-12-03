package jvm;

import jvm.instruction.Instruction;

import java.util.Map;

public class Interpreter {

    public static void run(Frame frame, Map<Integer, Instruction> instructions) {
        do {
            Instruction inst = instructions.get(frame.pc);
            inst.eval(frame);
        } while(instructions.containsKey(frame.pc));
    }

}
