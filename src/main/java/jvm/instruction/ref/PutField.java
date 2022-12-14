package jvm.instruction.ref;

import jvm.instruction.Instruction;
import jvm.runtime.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PutField implements Instruction {

    @NonNull private final String className;
    @NonNull private final String fieldName;
    @NonNull private final String fieldDescriptor;

    @Override
    public int offset() {
        return 3;
    }

    @Override
    public void eval(Frame frame) {
        UnionSlot us;
        switch (fieldDescriptor) {
            case "J", "D" -> {
                final Slot low = frame.pop();
                final Slot high = frame.pop();
                us = UnionSlot.of(high, low);
            }
            default -> us = UnionSlot.of(frame.pop());
        }
        final Instance self = frame.popRef();
        Field field = self.getField(fieldName, fieldDescriptor);
        assert field != null;
        field.set(us);
        advance(frame);
    }
}
