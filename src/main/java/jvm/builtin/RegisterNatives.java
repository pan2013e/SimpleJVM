package jvm.builtin;

import jvm.runtime.Frame;

@RegisterNativeMethod("java/lang/Object_registerNatives_()V")
public class RegisterNatives implements NativeMethod {

    public static NativeMethod get() {
        return frame -> {};
    }

    @Override
    public void eval(Frame frame) {
        get().eval(frame);
    }

}
