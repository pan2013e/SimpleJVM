package jvm.instruction.store;

public class AStore3 extends AStore {
    @Override
    public int offset() {
        return 1;
    }

    public AStore3() {
        super(3);
    }
}
