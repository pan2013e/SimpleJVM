package jvm.runtime;

public final class VMStack {

    int top;
    Frame[] frames;

    public VMStack(int maxStack) {
        frames = new Frame[maxStack];
        top = 0;
    }

    public void push(Frame frame) {
        if(top == frames.length) {
            throw new RuntimeException("StackOverflowError");
        }
        frames[top++] = frame;
    }

    public Frame pop() {
        if(top == 0) {
            throw new RuntimeException("StackUnderflowError");
        }
        Frame frame = frames[--top];
        frames[top] = null;
        return frame;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public Frame peek() {
        if(top == 0) {
            throw new RuntimeException("StackUnderflowError");
        }
        return frames[top - 1];
    }


}
