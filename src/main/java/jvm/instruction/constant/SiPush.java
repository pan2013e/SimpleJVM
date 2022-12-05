package jvm.instruction.constant;

@SuppressWarnings("unused")
// -32768 ~ 32767
public class SiPush extends IConst {

    public SiPush(int value) {
        super(value);
    }

    @Override
    public int offset() {
        return 3;
    }

}
