package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.Field;
import jvm.runtime.Frame;
import jvm.runtime.Instance;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetField implements Instruction {

    @NonNull private final String className;
    @NonNull private final String fieldName;
    @NonNull private final String fieldDescriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        final Instance self = frame.popRef();
        final Field field = self.getField(fieldName, fieldDescriptor);
        assert field != null;
        field.get(frame);
        advance(frame);
    }
}
