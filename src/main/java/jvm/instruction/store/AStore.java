package jvm.instruction.store;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AStore implements Instruction {

    @NonNull private final int idx;

    @Override
    public int offset() {
        return 2;
    }

    @Override
    public void eval(Frame frame) {
        frame.setRef(idx, frame.popRef());
        advance(frame);
    }
}
