package jvm.instruction.branch;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IfEq implements Instruction {
    @NonNull
    private final int offset;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        int val = frame.popInt();
        if(val == 0) {
            frame.pc += offset;
        } else {
            advance(frame);
        }
    }
}
