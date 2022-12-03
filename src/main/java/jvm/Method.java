package jvm;

import jvm.instruction.Instruction;
import jvm.instruction.Opcodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static jvm.classfile.Types.*;

public class Method {

    final int accessFlags;
    final String name;
    final String descriptor;
    final Code code;

    Class clazz;

    public Method(int accessFlags, String name, String descriptor, Code code) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.code = code;
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
                    Instruction inst = Opcodes.getInstruction(opcode, dis);
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
        return code.getInstructions(clazz.classFile.getConstantPool());
    }

}
