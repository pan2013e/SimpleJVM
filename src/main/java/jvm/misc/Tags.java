package jvm.misc;

@SuppressWarnings("unused")
public final class Tags {

    public static final class Constant {
        public static final int CONSTANT_Class              = 7;
        public static final int CONSTANT_Fieldref           = 9;
        public static final int CONSTANT_Methodref          = 10;
        public static final int CONSTANT_InterfaceMethodref = 11;
        public static final int CONSTANT_String             = 8;
        public static final int CONSTANT_Integer            = 3;
        public static final int CONSTANT_Float              = 4;
        public static final int CONSTANT_Long               = 5;
        public static final int CONSTANT_Double             = 6;
        public static final int CONSTANT_NameAndType        = 12;
        public static final int CONSTANT_Utf8               = 1;
        public static final int CONSTANT_MethodHandle       = 15;
        public static final int CONSTANT_MethodType         = 16;
        public static final int CONSTANT_InvokeDynamic      = 18;
    }

    public static final class ClassStat {
        public static final int CLASS_LOADED = 1;
        public static final int CLASS_LINKED = 2;
        public static final int CLASS_INITING= 3;
        public static final int CLASS_INITED = 4;
    }

    public static final class AccessFlag {
        public static final int ACC_PUBLIC       = 0x0001; // class, field, method
        public static final int ACC_PRIVATE      = 0x0002; //        field, method
        public static final int ACC_PROTECTED    = 0x0004; //        field, method
        public static final int ACC_STATIC       = 0x0008; //        field, method
        public static final int ACC_FINAL        = 0x0010; // class, field, method
        public static final int ACC_SUPER        = 0x0020; // class
        public static final int ACC_SYNCHRONIZED = 0x0020; //               method
        public static final int ACC_VOLATILE     = 0x0040; //        field
        public static final int ACC_BRIDGE       = 0x0040; //               method
        public static final int ACC_TRANSIENT    = 0x0080; //        field
        public static final int ACC_VARARGS      = 0x0080; //               method
        public static final int ACC_NATIVE       = 0x0100; //               method
        public static final int ACC_INTERFACE    = 0x0200; // class
        public static final int ACC_ABSTRACT     = 0x0400; // class,        method
        public static final int ACC_STRICT       = 0x0800; //               method
        public static final int ACC_SYNTHETIC    = 0x1000; // class, field, method
        public static final int ACC_ANNOTATION   = 0x2000; // class
        public static final int ACC_ENUM         = 0x4000; // class, field
    }

    public static final class FrameTag {
        public static final int FAKE_FRAME     = 1;
        public static final int FAKE_FRAME_END = 2;
    }

}
