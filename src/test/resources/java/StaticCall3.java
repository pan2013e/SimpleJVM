public class StaticCall3 {
    public static void main(String[] args) {
        test(1);
        test(1, 2);
    }

    public static void test(int i) {
        System.out.println(i);
    }
    public static void test(int i, int j) {
        System.out.println(i);
        System.out.println(j);
    }
}