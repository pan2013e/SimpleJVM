interface DefaultMethodInterface11 {
    default void v1() { System.out.println(1); }
}

class DefaultMethodInterfaceImpl11 implements DefaultMethodInterface11 { }

class DefaultMethodInterfaceImpl12 implements DefaultMethodInterface11 {
    public void v1() { System.out.println(2); }
}

class DefaultMethod {
    public static void main(String[] args) {
        DefaultMethodInterface11 i12 = new DefaultMethodInterfaceImpl12();
        i12.v1();

        DefaultMethodInterfaceImpl12 impl12 = new DefaultMethodInterfaceImpl12();
        impl12.v1();

        DefaultMethodInterface11 i11 = new DefaultMethodInterfaceImpl11();
        i11.v1();

        DefaultMethodInterfaceImpl11 impl11 = new DefaultMethodInterfaceImpl11();
        impl11.v1();
    }
}