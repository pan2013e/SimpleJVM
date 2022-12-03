package jvm.instruction;

import jvm.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// load const -128 ~ 127
public class BiPush implements Instruction {

    @NonNull private final int val;

    @Override
    public int offset() {
        return 2;
    }

    @Override
    public void eval(Frame frame) {
        frame.operandStack.push(val);
        advance(frame);
    }

}
