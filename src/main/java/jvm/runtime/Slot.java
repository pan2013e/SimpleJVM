package jvm.runtime;

final class Slot {

    public final int val;      // primitive type
    public final Instance ref; // reference type

    private Slot(int val, Instance ref) {
        this.val = val;
        this.ref = ref;
    }

    public static Slot val(int val) {
        return new Slot(val, null);
    }

    public static Slot ref(Instance ref) {
        return new Slot(0, ref);
    }

}
