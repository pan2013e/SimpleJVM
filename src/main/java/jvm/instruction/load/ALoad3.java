package jvm.instruction.load;

public class ALoad3 extends ALoad {
    @Override
    public int offset() {
        return 1;
    }

    public ALoad3() {
        super(3);
    }
}
