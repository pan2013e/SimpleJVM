package jvm.runtime;

import jvm.instruction.Instruction;

public class Frame {

    public int pc;

    private final Method method;
    private final Slot[] localVars;
    private final Slot[] operandStack;
    private int operandStackTop;

    public Frame(Method m) {
        localVars = new Slot[m.getMaxLocals()];
        operandStack = new Slot[m.getMaxStack()];
        operandStackTop = 0;
        pc = 0;
        method = m;
    }

    // for tests only
    private Frame(int maxLocals, int maxStack) {
        localVars = new Slot[maxLocals];
        operandStack = new Slot[maxStack];
        operandStackTop = 0;
        pc = 0;
        method = null;
    }

    // for unit tests only
    public static Frame getTestFrame(int maxLocals, int maxStack) {
        return new Frame(maxLocals, maxStack);
    }

    /*
     *  localVars
     */
    public void set(int idx, Slot val) {
        localVars[idx] = val;
    }

    public void setInt(int idx, int val) {
        set(idx, Slot.val(val));
    }

    public void setFloat(int idx, float val) {
        setInt(idx, Float.floatToIntBits(val));
    }

    public void setLong(int idx, long val) {
        int high = (int) (val >> 32);
        int low = (int) (val & 0x00000000ffffffffL);
        setInt(idx, high);
        setInt(idx + 1, low);
    }

    public void setDouble(int idx, double val) {
        setLong(idx, Double.doubleToLongBits(val));
    }

    public void setRef(int idx, Instance ref) {
        set(idx, Slot.ref(ref));
    }

    private Slot get(int idx) {
        return localVars[idx];
    }

    public int getInt(int idx) {
        return get(idx).val;
    }

    public float getFloat(int idx) {
        return Float.intBitsToFloat(getInt(idx));
    }

    public long getLong(int idx) {
        int high = getInt(idx);
        int low = getInt(idx + 1);
        return ((high & 0x00000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public double getDouble(int idx) {
        return Double.longBitsToDouble(getLong(idx));
    }

    public Instance getRef(int idx) {
        return get(idx).ref;
    }

    /*
     * operandStack
     */
    private void push(Slot val) {
        operandStack[operandStackTop++] = val;
    }

    public void pushInt(int val) {
        push(Slot.val(val));
    }

    public void pushFloat(float val) {
        pushInt(Float.floatToIntBits(val));
    }

    public void pushLong(long val) {
        int high = (int) (val >> 32);
        int low = (int) (val & 0x00000000ffffffffL);
        pushInt(high);
        pushInt(low);
    }

    public void pushDouble(double val) {
        pushLong(Double.doubleToLongBits(val));
    }

    public void pushRef(Instance ref) {
        push(Slot.ref(ref));
    }

    public Slot pop() {
        return operandStack[--operandStackTop];
    }

    public int popInt() {
        return pop().val;
    }

    public float popFloat() {
        return Float.intBitsToFloat(popInt());
    }

    public long popLong() {
        int low = popInt();
        int high = popInt();
        return ((high & 0x00000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }

    public Instance popRef() {
        return pop().ref;
    }

    public Instruction getInstruction() {
        return method.getInstructions().get(pc);
    }
}
