package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Frame;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvokeVirtual implements Instruction {

    @NonNull private final String className;
    @NonNull private final String methodName;
    @NonNull private final String methodDescriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        // Mocking System.out.println for now
        // getstatic "Field java/lang/System.out:Ljava/io/PrintStream;"
        // iload # frame.pushInt
        // invokevirtual "Method java/io/PrintStream.println:(I)V"
        // TODO
        final int val = frame.popInt(); // pop the value we want to print
        frame.popRef(); // pop the mocking null ref we pushed in `GetStatic`
        System.out.println(val); // mock the real `System.out.println`
        advance(frame);
    }
}
