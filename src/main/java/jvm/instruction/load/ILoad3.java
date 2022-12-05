package jvm.instruction.load;

public class ILoad3 extends ILoad {
    public ILoad3() {
        super(3);
    }

    @Override
    public int offset() {
        return 1;
    }
}
