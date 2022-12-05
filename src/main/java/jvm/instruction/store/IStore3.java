package jvm.instruction.store;

public class IStore3 extends IStore {
    public IStore3() {
        super(3);
    }

    @Override
    public int offset() {
        return 1;
    }
}
