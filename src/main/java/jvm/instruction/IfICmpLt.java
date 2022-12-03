package jvm.instruction;

import jvm.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IfICmpLt implements Instruction {

    @NonNull private final int offset;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        int val2 = frame.operandStack.pop();
        int val1 = frame.operandStack.pop();
        if (val1 < val2) {
            frame.pc += offset;
        } else {
            advance(frame);
        }
    }

}
