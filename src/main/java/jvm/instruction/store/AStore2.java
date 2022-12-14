package jvm.instruction.store;

public class AStore2 extends AStore {
    @Override
    public int offset() {
        return 1;
    }

    public AStore2() {
        super(2);
    }
}
