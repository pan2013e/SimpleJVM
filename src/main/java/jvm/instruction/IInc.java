package jvm.instruction;

import jvm.Frame;
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
        int val = frame.localVars.get(index);
        val += constVal;
        frame.localVars.put(index, val);
        advance(frame);
    }

}
