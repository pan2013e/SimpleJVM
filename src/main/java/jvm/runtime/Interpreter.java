package jvm.runtime;

import jvm.instruction.Instruction;

import static jvm.misc.Tags.FrameTag;

public class Interpreter {

    public static void run() {
        VMStack env = MetaSpace.getMainEnv();
        while (!env.isEmpty()) {
            Frame frame = env.peek();
            Instruction inst = frame.getInstruction();
            inst.eval(frame);
        }
    }

    @SuppressWarnings("Duplicates")
    public static void execute(Method method) {
        Frame newFrame = new Frame(method);
        final Frame oldFrame = MetaSpace.getMainEnv().peek();
        final int slots = method.getArgSlotSize();
        for (int i = slots - 1; i >= 0; i--) {
            newFrame.set(i, oldFrame.pop());
        }
        execute(newFrame);
    }

    public static void execute(Frame frame) {
        MetaSpace.getMainEnv().push(frame);
        frame.stat = FrameTag.FAKE_FRAME;
        while(frame.stat == FrameTag.FAKE_FRAME) {
            Frame f = MetaSpace.getMainEnv().peek();
            Instruction inst = f.getInstruction();
            inst.eval(f);
        }
    }
}
