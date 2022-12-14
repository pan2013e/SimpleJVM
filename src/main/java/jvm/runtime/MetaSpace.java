package jvm.runtime;

import jvm.builtin.NativeMethod;

import java.util.HashMap;
import java.util.Map;

public final class MetaSpace {

    private static final Map<String, Class> CLASS_MAP = new HashMap<>();
    private static final Map<String, NativeMethod> NATIVE_METHOD_MAP = new HashMap<>();
    private static ClassLoader loader;
    private static VMStack main;

    // for system tests only!
    public static void clear() {
        CLASS_MAP.clear();
        NATIVE_METHOD_MAP.clear();
        loader = null;
        main = null;
    }

    public static VMStack getMainEnv() {
        return main;
    }

    public static void setMainEnv(VMStack env) {
        main = env;
    }

    public static Class findClass(String className) {
        return CLASS_MAP.get(className);
    }

    public static void putClass(String className, Class clazz) {
        if(CLASS_MAP.containsKey(className)) {
            throw new IllegalStateException("class " + className + " has been loaded");
        }
        CLASS_MAP.put(className, clazz);
    }

    public static void setClassLoader(ClassLoader classLoader) {
        loader = classLoader;
    }

    public static ClassLoader getClassLoader() {
        return loader;
    }

    public static NativeMethod findNativeMethod(String key) {
        return NATIVE_METHOD_MAP.get(key);
    }

    public static void putNativeMethod(String key, NativeMethod method) {
        NATIVE_METHOD_MAP.putIfAbsent(key, method);
    }


}
