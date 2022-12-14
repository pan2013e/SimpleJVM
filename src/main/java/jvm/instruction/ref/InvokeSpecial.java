package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Class;
import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;
import jvm.runtime.Method;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvokeSpecial implements Instruction {

    @NonNull private final String className;
    @NonNull private final String methodName;
    @NonNull private final String descriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        final Class clazz = MetaSpace.getClassLoader().findClass(className);
        final Method method = clazz.getSpecialMethod(methodName, descriptor);
        clazz.clinit();
        method.invoke();
        advance(frame);
    }

}
