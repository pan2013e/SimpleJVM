package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetStatic implements Instruction {

    @NonNull private final String className;
    @NonNull private final String fieldName;
    @NonNull private final String fieldDescriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        // Mocking System.out.println for now
        // getstatic "Field java/lang/System.out:Ljava/io/PrintStream;"
        // iload
        // invokevirtual "Method java/io/PrintStream.println:(I)V"
        // TODO
        frame.pushRef(null);  // We don't need the real `System.out` for now
        advance(frame);
    }
}
