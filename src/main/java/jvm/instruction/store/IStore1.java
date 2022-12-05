package jvm.instruction.store;

public class IStore1 extends IStore {
    public IStore1() {
        super(1);
    }

    @Override
    public int offset() {
        return 1;
    }
}
