package jvm.builtin;

import jvm.runtime.Frame;

@FunctionalInterface
public interface NativeMethod {

    void eval(Frame frame);

}
