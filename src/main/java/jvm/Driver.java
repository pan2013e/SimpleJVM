package jvm;

import jvm.builtin.NativeMethod;
import jvm.builtin.RegisterNativeMethod;
import jvm.runtime.*;
import jvm.runtime.ClassLoader;
import lombok.NonNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Driver {

    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String RT_JAR = "src/main/resources/rt.jar";

    private static String classPath = Utils.classPathConcat(CURRENT_DIR, RT_JAR);

    private static String className = null;

    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java jvm.Driver [-cp <classpath>] <classname>");
            System.exit(1);
        }
        if (args[0].equals("-cp") || args[0].equals("-classpath")) {
            if (args.length < 3) {
                System.out.println("Usage: java jvm.Driver [-cp <classpath>] <classname>");
                System.exit(1);
            }
            classPath = Utils.classPathConcat(args[1], classPath);
            className = args[2];
        } else {
            className = args[0];
        }
    }

    private static void initNativeMethods() throws IOException {
        PackageScanner scanner = new PackageScanner(NativeMethod.class, RegisterNativeMethod.class);
        scanner.getClasses()
                .forEach(cls -> {
                    try {
                        NativeMethod nativeMethod =
                                (NativeMethod) cls.getDeclaredMethod("get").invoke(null);
                        MetaSpace.putNativeMethod(
                                cls.getAnnotation(RegisterNativeMethod.class).value(),
                                nativeMethod);
                    } catch (NoSuchMethodException | IllegalAccessException |
                            InvocationTargetException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                });
    }

    public static void VMInit(@NonNull final String classPath,
                              @NonNull final String className) {
        MetaSpace.setClassLoader(new ClassLoader(classPath));
        final Method main = MetaSpace.getClassLoader()
                                .findClass(className)
                                .getStaticMethod("main", "([Ljava/lang/String;)V");
        if(main == null) {
            System.out.println("main method not found");
            System.exit(1);
        }
        try {
            initNativeMethods();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        MetaSpace.setMainEnv(new VMStack(128));
        MetaSpace.getMainEnv().push(new Frame(main));
    }

    public static void main(String[] args) {
        parseArgs(args);
        VMInit(classPath, className);
        Interpreter.run();
    }

}
