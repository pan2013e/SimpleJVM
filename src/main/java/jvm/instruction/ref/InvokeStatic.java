package jvm.instruction.ref;

import jvm.Utils;
import jvm.builtin.NativeMethod;
import jvm.instruction.Instruction;
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
        Method m = MetaSpace.getClassLoader()
                    .findClass(className)
                    .getStaticMethod(methodName, methodDescriptor);
        if(Utils.isNative(m.getAccessFlags())) {
            final NativeMethod nm = MetaSpace.findNativeMethod(m.getKey());
            if(nm == null) {
                throw new IllegalStateException("native method " + m.getKey() + " not found");
            }
            nm.eval(frame);
        } else {
            Frame newFrame = new Frame(m);
            final Frame oldFrame = MetaSpace.getMainEnv().peek();
            final int slots = m.getArgSlotSize();
            for(int i = slots - 1;i >= 0;i--) {
                newFrame.set(i, oldFrame.pop());
            }
            MetaSpace.getMainEnv().push(newFrame);
        }
        advance(frame);
    }

}
