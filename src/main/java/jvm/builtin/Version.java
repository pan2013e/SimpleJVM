package jvm.builtin;

import jvm.runtime.Frame;

@RegisterNativeMethod("MyJVM_version_()I")
public class Version implements NativeMethod {

    private static final int JDK8_MAJOR_VERSION = 52;

    public static NativeMethod get() {
        return frame -> frame.pushInt(JDK8_MAJOR_VERSION);
    }

    @Override
    public void eval(Frame frame) {
        get().eval(frame);
    }

}
