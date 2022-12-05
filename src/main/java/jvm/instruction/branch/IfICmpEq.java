package jvm.instruction.branch;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IfICmpEq implements Instruction {

    @NonNull
    private final int offset;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        int v2 = frame.popInt();
        int v1 = frame.popInt();
        if (v1 == v2) {
            frame.pc += offset;
        } else {
            advance(frame);
        }
    }

}
