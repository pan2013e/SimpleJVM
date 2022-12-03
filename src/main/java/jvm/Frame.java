package jvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Frame {
    public int pc;

    public final Map<Integer, Integer> localVars = new HashMap<>();

    public final Stack<Integer> operandStack = new Stack<>();
}
