package jvm.instruction.load;

public class ALoad1 extends ALoad {
    @Override
    public int offset() {
        return 1;
    }

    public ALoad1() {
        super(1);
    }
}
