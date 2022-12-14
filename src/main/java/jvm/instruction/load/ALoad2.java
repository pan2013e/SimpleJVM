package jvm.instruction.load;

public class ALoad2 extends ALoad {
    @Override
    public int offset() {
        return 1;
    }

    public ALoad2() {
        super(2);
    }
}
