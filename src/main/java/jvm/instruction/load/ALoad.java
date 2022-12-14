package jvm.instruction.load;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ALoad implements Instruction {

    @NonNull private final int idx;

    @Override
    public int offset() {
        return 2;
    }

    @Override
    public void eval(Frame frame) {
        frame.pushRef(frame.getRef(idx));
        advance(frame);
    }
}
