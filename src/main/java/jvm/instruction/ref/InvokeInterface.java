package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.misc.Utils;
import jvm.runtime.Class;
import jvm.runtime.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvokeInterface implements Instruction {

    @NonNull private final String className;
    @NonNull private final String methodName;
    @NonNull private final String methodDescriptor;

    @Override
    public int offset() {
        return 5;
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public void eval(Frame frame) {
        final Class clazz = MetaSpace.getClassLoader().findClass(className);
        final int slotSize = Utils.getArgSlotSize(methodDescriptor);
        final Instance self = frame.getThis(slotSize); // get `this` of the actual class instance
        // polymorphic dispatch
        Method method = self.getClazz().getVirtualMethod(methodName, methodDescriptor);
        if(method == null) { // try default method
            method = clazz.getDefaultMethod(methodName, methodDescriptor);
        }
        if(method == null) {
            throw new IllegalStateException(
                    String.format("Method not found: %s.%s:%s", className, methodName, methodDescriptor));
        }
        method.invoke();
        advance(frame);
    }
}
