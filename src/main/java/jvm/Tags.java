package jvm;

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

}
