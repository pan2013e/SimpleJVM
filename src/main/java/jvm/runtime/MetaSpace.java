package jvm.runtime;

import jvm.builtin.NativeMethod;

import java.util.HashMap;
import java.util.Map;

public final class MetaSpace {

    private static final String JAVA_HOME = "src/main/resources";
    private static final String JAVA_RT = JAVA_HOME + "/rt.jar";

    private static final Map<String, NativeMethod> NATIVE_METHOD_MAP = new HashMap<>();
    private static ClassLoader bootstrapLoader, systemLoader;
    private static VMStack main;

    public static void init() {
        bootstrapLoader = ClassLoader.createBootstrapClassLoader(JAVA_RT);
        systemLoader = null;
        main = null;
    }

    public static VMStack getMainEnv() {
        return main;
    }

    public static void setMainEnv(VMStack env) {
        main = env;
    }

    public static Class findClass(String className) {
        return systemLoader.findClass(className);
    }

    public static void setClassLoader(ClassLoader classLoader) {
        systemLoader = classLoader;
    }

    public static ClassLoader getClassLoader() {
        return getSystemClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return systemLoader;
    }

    public static ClassLoader getBootstrapClassLoader() {
        return bootstrapLoader;
    }

    public static NativeMethod findNativeMethod(String key) {
        return NATIVE_METHOD_MAP.get(key);
    }

    public static void putNativeMethod(String key, NativeMethod method) {
        NATIVE_METHOD_MAP.putIfAbsent(key, method);
    }


}
