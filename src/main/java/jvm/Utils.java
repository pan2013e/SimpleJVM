package jvm;

import static jvm.classfile.Types.*;
import static jvm.Tags.*;

public class Utils {

    public static String getUtf8(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Utf8) {
            throw new IllegalStateException("not a utf8");
        }
        return new String(cpInfo.getInfo());
    }

    public static String getClassName(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Class) {
            throw new IllegalStateException("not a class");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameIdx = ((info[0] & 0xFF) << 8) | (info[1] & 0xFF);
        return getUtf8(cp, nameIdx);
    }

    public static boolean isDotClassName(String className) {
        return className.contains(".");
    }

    public static boolean isSlashClassName(String className) {
        return className.contains("/");
    }

    public static String convertClassNameToSlash(String className) {
        if (isDotClassName(className)) {
            return className.replace(".", "/");
        } else {
            return className;
        }
    }

    public static String convertClassNameToSlash(java.lang.Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }

    public static String classPathConcat(String path1, String... paths) {
        StringBuilder pathBuilder = new StringBuilder(path1);
        for(String path : paths) {
            pathBuilder.append(":").append(path);
        }
        return pathBuilder.toString();
    }

}
