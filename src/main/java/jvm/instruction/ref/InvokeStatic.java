package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Class;
import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;
import jvm.runtime.Method;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvokeStatic implements Instruction {

    @NonNull private final String className;
    @NonNull private final String methodName;
    @NonNull private final String methodDescriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        // No need to check polymorphism,
        // since static methods cannot be overridden
        final Class clazz = MetaSpace.getClassLoader().findClass(className);
        Method m = clazz.getStaticMethod(methodName, methodDescriptor);
        clazz.clinit();
        m.invoke();
        advance(frame);
    }

}
