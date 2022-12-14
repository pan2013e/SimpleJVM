package jvm.instruction.store;

public class AStore1 extends AStore {
    @Override
    public int offset() {
        return 1;
    }

    public AStore1() {
        super(1);
    }
}
