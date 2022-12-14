package jvm.runtime;

public final class Slot {

    public int val;      // primitive type
    public Instance ref; // reference type

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
