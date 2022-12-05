package jvm.instruction.branch;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IfICmpLe implements Instruction {
    @NonNull
    private final int offset;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        int val2 = frame.popInt();
        int val1 = frame.popInt();
        if (val1 <= val2) {
            frame.pc += offset;
        } else {
            advance(frame);
        }
    }
}
