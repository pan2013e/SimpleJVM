package jvm.instruction.constant;

@SuppressWarnings("unused")
// load const -128 ~ 127
public class BiPush extends IConst {

    public BiPush(int value) {
        super(value);
    }

    @Override
    public int offset() {
        return 2;
    }

}
