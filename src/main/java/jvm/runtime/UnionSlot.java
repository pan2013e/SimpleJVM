package jvm.runtime;

public final class UnionSlot {

    private Slot high;
    private Slot low;

    private UnionSlot(Slot high, Slot low) {
        this.high = high;
        this.low = low;
    }

    public static UnionSlot of(Slot high, Slot low) {
        return new UnionSlot(high, low);
    }

    public static UnionSlot of(Slot high) {
        return new UnionSlot(high, null);
    }

    public static UnionSlot of(Instance instance) {
        return new UnionSlot(Slot.ref(instance), null);
    }

    public static UnionSlot of(int val) {
        return new UnionSlot(Slot.val(val), null);
    }

    public static UnionSlot of(float val) {
        return of(Float.floatToIntBits(val));
    }

    public static UnionSlot of(long val) {
        int high = (int) (val >> 32);
        int low = (int) (val & 0x00000000FFFFFFFFL);
        return new UnionSlot(Slot.val(high), Slot.val(low));
    }

    public static UnionSlot of(double val) {
        return of(Double.doubleToLongBits(val));
    }

    public void set(UnionSlot neo) {
        high = neo.high;
        low = neo.low;
    }

    public void setRef(Instance instance) {
        high.ref = instance;
    }

    public void setInt(int val) {
        high.val = val;
    }

    public void setFloat(float val) {
        setInt(Float.floatToIntBits(val));
    }

    public void setLong(long val) {
        int high = (int) (val >> 32);
        int low = (int) (val & 0x00000000FFFFFFFFL);
        this.high.val = high;
        this.low.val = low;
    }

    public void setDouble(double val) {
        setLong(Double.doubleToLongBits(val));
    }

    public Instance getRef() {
        return high.ref;
    }

    public int getInt() {
        return high.val;
    }

    public float getFloat() {
        return Float.intBitsToFloat(getInt());
    }

    public long getLong() {
        final long high = this.high.val;
        final long low = this.low.val;
        return ((high & 0x00000000FFFFFFFFL) << 32) | (low & 0x00000000FFFFFFFFL);
    }

    public double getDouble() {
        return Double.longBitsToDouble(getLong());
    }

}
