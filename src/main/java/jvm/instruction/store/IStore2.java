package jvm.instruction.store;

public class IStore2 extends IStore {
    public IStore2() {
        super(2);
    }

    @Override
    public int offset() {
        return 1;
    }
}
