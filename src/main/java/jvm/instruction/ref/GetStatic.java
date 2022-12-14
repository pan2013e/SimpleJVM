package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Class;
import jvm.runtime.Field;
import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;
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
        if(className.equals("java/lang/System") && fieldName.equals("out")) {
            // Mocking System.out.println for now
            // getstatic "Field java/lang/System.out:Ljava/io/PrintStream;"
            // iload
            // invokevirtual "Method java/io/PrintStream.println:(I)V"
            frame.pushRef(null);  // We don't need the real `System.out` for now
        } else {
            final Class clazz = MetaSpace.getClassLoader().findClass(className);
            Field field = clazz.getStaticField(fieldName, fieldDescriptor);
            // if the class was not loaded before, init static fields first
            field.getClazz().clinit();
            field.get(frame);  // static field is pushed to operand stack
        }
        advance(frame);
    }
}
