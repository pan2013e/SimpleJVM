package jvm.runtime;

import jvm.builtin.NativeMethod;
import jvm.instruction.Instruction;
import jvm.instruction.Opcodes;
import jvm.misc.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static jvm.classfile.Types.AttributeInfo;
import static jvm.classfile.Types.CpInfo;

public class Method {

    @Getter
    final int accessFlags;
    final String name;
    final String descriptor;
    final Code code;

    @Getter
    Class clazz;

    Map<Integer, Instruction> instructions;

    public Method(int accessFlags, String name, String descriptor, Code code) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.code = code;
        this.instructions = null;
    }

    @AllArgsConstructor
    @Getter
    public static final class ExceptionTableItem {
        final int startPc;
        final int endPc;
        final int handlerPc;
        final int catchType;
    }

    @AllArgsConstructor
    @Getter
    public static final class Code {
        final int maxStack;
        final int maxLocals;
        final byte[] bytes;
        final ExceptionTableItem[] exceptionTable;
        final AttributeInfo[] attributes;

        public Map<Integer, Instruction> getInstructions(CpInfo[] cp) {
            Map<Integer, Instruction> instructionMap = new HashMap<>();
            try (
                    final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))
            ) {
                int pc = 0;
                while (dis.available() > 0) {
                    final int opcode = dis.read();
                    Instruction inst = Opcodes.getInstruction(opcode, dis, cp);
                    instructionMap.put(pc, inst);
                    pc += inst.offset();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return instructionMap;
        }
    }

    public Map<Integer, Instruction> getInstructions() {
        if(instructions != null) {
            return instructions;
        }
        final Map<Integer, Instruction> inst = code.getInstructions(clazz.getClassFile().getConstantPool());
        instructions = inst;
        return inst;
    }

    public int getMaxLocals() {
        return code.maxLocals;
    }

    public int getMaxStack() {
        return code.maxStack;
    }

    public int getArgSlotSize() {
        final int size = Utils.getArgSlotSize(descriptor);
        if(Utils.isStatic(accessFlags)) {
            return size;
        }
        return size + 1; // `this` pointer
    }

    public String getKey() {
        return clazz.name + "_" + name + "_" + descriptor;
    }

    public boolean isNative() {
        return Utils.isNative(accessFlags);
    }

    public boolean isStatic() {
        return Utils.isStatic(accessFlags);
    }

    public void invoke() {
        if(isNative()) {
            NativeMethod nm = MetaSpace.findNativeMethod(getKey());
            if(nm == null) {
                throw new RuntimeException("native method not found: " + getKey());
            }
            nm.eval(MetaSpace.getMainEnv().peek());
        } else {
            Frame newFrame = new Frame(this);
            final Frame old = MetaSpace.getMainEnv().peek();
            final int slots = getArgSlotSize();
            for(int i = slots - 1; i >= 0; i--) {
                newFrame.set(i, old.pop());
            }
            MetaSpace.getMainEnv().push(newFrame);
        }
    }

}
