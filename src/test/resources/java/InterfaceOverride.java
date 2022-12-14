interface InterfaceOverride1 {

    void v1();
}

interface InterfaceOverride2 extends InterfaceOverride1 {

    void v2();
}

class InterfaceOverrideImpl1 implements InterfaceOverride1 {

    @Override
    public void v1() {
        System.out.println(1);
    }
}

class InterfaceOverrideImpl2 implements InterfaceOverride2 {

    @Override
    public void v1() {
        System.out.println(2);
    }

    @Override
    public void v2() {
        System.out.println(3);
    }
}

class InterfaceOverride {

    public static void main(String[] args) {
        InterfaceOverride1 i1 = new InterfaceOverrideImpl1();
        i1.v1();
        InterfaceOverride1 i12 = new InterfaceOverrideImpl2();
        i12.v1();

        InterfaceOverride2 i2 = new InterfaceOverrideImpl2();
        i2.v1();
        i2.v2();

        InterfaceOverrideImpl1 impl1 = new InterfaceOverrideImpl1();
        impl1.v1();
        InterfaceOverrideImpl2 impl2 = new InterfaceOverrideImpl2();
        impl2.v1();
        impl2.v2();
    }
}