package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.misc.Utils;
import jvm.runtime.Class;
import jvm.runtime.*;
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
    @SuppressWarnings("DuplicatedCode")
    public void eval(Frame frame) {
        // Mocking System.out.println for now
        // getstatic "Field java/lang/System.out:Ljava/io/PrintStream;"
        // iload # frame.pushInt
        // invokevirtual "Method java/io/PrintStream.println:(I)V"
        // TODO
        if(className.equals("java/io/PrintStream") && methodName.equals("println")) {
            final String argDes = methodDescriptor
                    .substring(methodDescriptor.indexOf('(') + 1, methodDescriptor.indexOf(')'));
            switch (argDes) {
                case "Z" -> System.out.println(frame.popInt() != 0);
                case "B" -> System.out.println(((byte) frame.popInt()));
                case "C" -> System.out.println(((char) frame.popInt()));
                case "S" -> System.out.println(((short) frame.popInt()));
                case "I" -> System.out.println(frame.popInt());
                case "J" -> System.out.println(frame.popLong());
                case "F" -> System.out.println(frame.popFloat());
                case "D" -> System.out.println(frame.popDouble());
                default -> throw new IllegalStateException("Unsupported method descriptor: " + methodDescriptor);
            }
            frame.popRef(); // pop the mocking null ref we pushed in `GetStatic`
            advance(frame);
            return;
        }
        final Class clazz = MetaSpace.getClassLoader().findClass(className);
        final int slotSize = Utils.getArgSlotSize(methodDescriptor);
        final Instance self = frame.getThis(slotSize); // get `this` of the actual class instance
        // polymorphic dispatch
        Method method = self.getClazz().getVirtualMethod(methodName, methodDescriptor);
        if(method == null) {
            method = clazz.getDefaultMethod(methodName, methodDescriptor);
        }
        if(method == null) {
            throw new IllegalStateException("Method not found: " + methodName + " " + methodDescriptor);
        }
        method.invoke();
        advance(frame);
    }
}
