package jvm.misc;

import jvm.runtime.Frame;
import jvm.runtime.MetaSpace;
import jvm.runtime.Slot;

import static jvm.classfile.Types.CpInfo;
import static jvm.misc.Tags.*;

@SuppressWarnings("DuplicatedCode")
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

    // get name from NameAndType
    public static String getNameFromNameAndType(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_NameAndType) {
            throw new IllegalStateException("not a name and type");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameIdx = ((info[0] & 0xFF) << 8) | (info[1] & 0xFF);
        return getUtf8(cp, nameIdx);
    }

    // get type from NameAndType
    public static String getDescriptorFromNameAndType(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_NameAndType) {
            throw new IllegalStateException("not a name and type");
        }
        final byte[] info = cpInfo.getInfo();
        final int descriptorIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getUtf8(cp, descriptorIdx);
    }

    public static String getClassNameFromFieldRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Fieldref) {
            throw new IllegalStateException("not a field ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int classIdx = ((info[0] & 0xFF) << 8) | (info[1] & 0xFF);
        return getClassName(cp, classIdx);
    }

    // get field name from FieldRef
    public static String getNameFromFieldRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Fieldref) {
            throw new IllegalStateException("not a field ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameAndTypeIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getNameFromNameAndType(cp, nameAndTypeIdx);
    }

    // get field descriptor from FieldRef
    public static String getDescriptorFromFieldRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Fieldref) {
            throw new IllegalStateException("not a field ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameAndTypeIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getDescriptorFromNameAndType(cp, nameAndTypeIdx);
    }

    public static String getClassNameFromMethodRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Methodref) {
            throw new IllegalStateException("not a method ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int classIdx = ((info[0] & 0xFF) << 8) | (info[1] & 0xFF);
        return getClassName(cp, classIdx);
    }

    // get method name from MethodRef
    public static String getNameFromMethodRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Methodref) {
            throw new IllegalStateException("not a method ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameAndTypeIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getNameFromNameAndType(cp, nameAndTypeIdx);
    }

    // get method descriptor from MethodRef
    public static String getDescriptorFromMethodRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_Methodref) {
            throw new IllegalStateException("not a method ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameAndTypeIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getDescriptorFromNameAndType(cp, nameAndTypeIdx);
    }

    public static String getClassNameFromInterfaceMethodRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_InterfaceMethodref) {
            throw new IllegalStateException("not a interface method ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int classIdx = ((info[0] & 0xFF) << 8) | (info[1] & 0xFF);
        return getClassName(cp, classIdx);
    }

    // get method name from InterfaceMethodRef
    public static String getNameFromInterfaceMethodRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_InterfaceMethodref) {
            throw new IllegalStateException("not a interface method ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameAndTypeIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getNameFromNameAndType(cp, nameAndTypeIdx);
    }

    // get method descriptor from InterfaceMethodRef
    public static String getDescriptorFromInterfaceMethodRef(CpInfo[] cp, int idx) {
        final CpInfo cpInfo = cp[idx];
        if(cpInfo.getTag().getVal() != Constant.CONSTANT_InterfaceMethodref) {
            throw new IllegalStateException("not a interface method ref");
        }
        final byte[] info = cpInfo.getInfo();
        final int nameAndTypeIdx = ((info[2] & 0xFF) << 8) | (info[3] & 0xFF);
        return getDescriptorFromNameAndType(cp, nameAndTypeIdx);
    }

    public static int getArgSlotSize(String descriptor) {
        int slots = 0;
        assert descriptor.matches("\\(.*\\).");
        for(int i = 1;i < descriptor.length();i++) {
            char c = descriptor.charAt(i);
            if(c == ')') {  // end of args
                break;
            }
            if(c == 'J' || c == 'D') { // long or double
                slots += 2;
            } else if(c == 'L') {  // object
                while(descriptor.charAt(i) != ';') { // skip object name
                    i++;
                }
                slots++;
            } else if(c == '[') {  // array
                while(descriptor.charAt(i) == '[') {
                    i++;
                }
                if(descriptor.charAt(i) == 'L') {
                    while(descriptor.charAt(i) != ';') {
                        i++;
                    }
                }
                slots++;
            } else { // int, short, byte, char, boolean
                slots++;
            }
        }
        return slots;
    }

    public static boolean isStatic(int accessFlags) {
        return (accessFlags & AccessFlag.ACC_STATIC) != 0;
    }

    public static boolean isAbstract(int accessFlags) {
        return (accessFlags & AccessFlag.ACC_ABSTRACT) != 0;
    }

    public static boolean isNative(int accessFlags) {
        return (accessFlags & AccessFlag.ACC_NATIVE) != 0;
    }

    public static boolean isInterface(int accessFlags) {
        return (accessFlags & AccessFlag.ACC_INTERFACE) != 0;
    }

    public static boolean isDotClassName(String className) {
        return className.contains(".");
    }

    public static boolean isSlashClassName(String className) {
        return !isDotClassName(className);
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

    public static void doReturn(int slotSize) {
        final Frame oldFrame = MetaSpace.getMainEnv().pop();
        if(oldFrame.stat == FrameTag.FAKE_FRAME) {
            oldFrame.stat = FrameTag.FAKE_FRAME_END;
        }
        if(slotSize == 0) {
            return;
        }
        final Frame current = MetaSpace.getMainEnv().peek();
        if(slotSize == 1) {
            current.push(oldFrame.pop());
        } else if(slotSize == 2) {
            final Slot v2 = oldFrame.pop();
            final Slot v1 = oldFrame.pop();
            current.push(v1);
            current.push(v2);
        } else {
            throw new IllegalStateException("slot size must be 0, 1 or 2");
        }
    }

    public static void doReturn0() {
        doReturn(0);
    }

    public static void doReturn1() {
        doReturn(1);
    }

    public static void doReturn2() {
        doReturn(2);
    }

}
