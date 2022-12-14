package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Class;
import jvm.runtime.Field;
import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PutStatic implements Instruction {

    @NonNull private final String className;
    @NonNull private final String fieldName;
    @NonNull private final String fieldDescriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        final Class clazz = MetaSpace.getClassLoader().findClass(className);
        Field field = clazz.getStaticField(fieldName, fieldDescriptor);
        // value is popped from operand stack, and put into static field
        field.set(frame);
        advance(frame);
    }
}
