package jvm.instruction;

import jvm.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IfICmpGt implements Instruction {

    @NonNull private final int offset;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        int v2 = frame.operandStack.pop();
        int v1 = frame.operandStack.pop();
        if (v1 > v2) {
            frame.pc += offset;
        } else {
            advance(frame);
        }
    }
}
