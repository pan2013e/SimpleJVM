package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Class;
import jvm.runtime.Frame;
import jvm.runtime.Instance;
import jvm.runtime.MetaSpace;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class New implements Instruction {

    @NonNull private final String className;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        Class clazz = MetaSpace.getClassLoader().findClass(className);
        clazz.clinit();
        Instance instance = clazz.newInstance();
        frame.pushRef(instance);
        advance(frame);
    }
}
