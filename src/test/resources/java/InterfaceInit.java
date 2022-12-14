class InterfaceInitObj {
    int val = 0;
    { System.out.println(1); }
}

interface InterfaceInit111 {
    InterfaceInitObj x = new InterfaceInitObj();
    void v1();
}

interface InterfaceInit222 extends InterfaceInit111 {
    InterfaceInitObj y = new InterfaceInitObj();
    default void v2() { System.out.println(4); }
}

class InterfaceInitImpl111 implements InterfaceInit111 {
    public void v1() { System.out.println(2); }
}

class InterfaceInitImpl222 implements InterfaceInit222 {
    public void v1() { System.out.println(3); }
}

class InterfaceInit {

    public static void main(String[] args) {
        InterfaceInit111 i1 = new InterfaceInitImpl111();
        i1.v1();
        System.out.println(i1.x.val);

        InterfaceInit222 i2 = new InterfaceInitImpl222();
        i2.v1();
        System.out.println(i2.y.val);
    }
}