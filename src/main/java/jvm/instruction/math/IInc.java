package jvm.instruction.math;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IInc implements Instruction {

    @NonNull private final int index;
    @NonNull private final int constVal;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        int val = frame.getInt(index);
        val += constVal;
        frame.setInt(index, val);
        advance(frame);
    }

}
