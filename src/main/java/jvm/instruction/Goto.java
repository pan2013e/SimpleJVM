package jvm.instruction;

import jvm.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Goto implements Instruction {

    @NonNull private final int offset;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        frame.pc += offset;
    }

}
