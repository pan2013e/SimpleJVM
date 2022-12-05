package jvm.instruction.load;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ILoad implements Instruction {

    @NonNull private final int index;

    @Override
    public int offset() {
        return 2;
    }

    @Override
    public void eval(Frame frame) {
        frame.pushInt(frame.getInt(index));
        advance(frame);
    }

}
