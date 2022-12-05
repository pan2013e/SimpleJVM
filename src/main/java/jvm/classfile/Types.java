package jvm.classfile;

import jvm.runtime.Method;
import jvm.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static jvm.runtime.Method.Code;
import static jvm.runtime.Method.ExceptionTableItem;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public final class Types {

    @AllArgsConstructor
    @Getter
    public static final class U1 {
        final int val;
    }

    @AllArgsConstructor
    @Getter
    public static final class U2 {
        final int val;
    }

    @AllArgsConstructor
    @Getter
    public static final class U4 {
        final int val;
    }

    @AllArgsConstructor
    @Getter
    public static final class CpInfo { // 常量池信息
        final U1 tag;
        final byte[] info;
    }

    @AllArgsConstructor
    @Getter
    public static final class FieldInfo {
        final U2 accessFlags;
        final U2 nameIndex;  // 字段名索引
        final U2 descriptorIndex;  // 字段描述符索引
        final U2 attributesCount;
        final AttributeInfo[] attributes;
    }

    @AllArgsConstructor
    @Getter
    public static final class MethodInfo {
        final U2 accessFlags;
        final U2 nameIndex;
        final U2 descriptorIndex;
        final U2 attributesCount;
        final AttributeInfo[] attributes;

        public Code getCode(ClassFile classFile) {
            Code code = null;
            final CpInfo[] constantPool = classFile.constantPool;
            for (AttributeInfo attribute : attributes) {
                final String attrName = Utils.getUtf8(constantPool, attribute.attributeNameIndex.val);
                if("Code".equals(attrName)) {
                    final byte[] bytes = attribute.info;
                    try (
                        final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes))
                    ) {
                        final int maxStack = dis.readUnsignedShort();
                        final int maxLocals = dis.readUnsignedShort();
                        final int codeLength = dis.readInt();
                        final byte[] codeBytes = new byte[codeLength];
                        dis.read(codeBytes);
                        final int exceptionTableLength = dis.readUnsignedShort();
                        final ExceptionTableItem[] exceptionTable = new ExceptionTableItem[exceptionTableLength];
                        for (int i = 0; i < exceptionTableLength; i++) {
                            final int startPc = dis.readUnsignedShort();
                            final int endPc = dis.readUnsignedShort();
                            final int handlerPc = dis.readUnsignedShort();
                            final int catchType = dis.readUnsignedShort();
                            exceptionTable[i] = new ExceptionTableItem(startPc, endPc, handlerPc, catchType);
                        }
                        final int attributesCount = dis.readUnsignedShort();
                        final AttributeInfo[] attributeInfos = new AttributeInfo[attributesCount];
                        for (int i = 0; i < attributesCount; i++) {
                            final int attributeNameIndex = dis.readUnsignedShort();
                            final int attributeLength = dis.readInt();
                            final byte[] info = new byte[attributeLength];
                            dis.read(info);
                            attributeInfos[i] = new AttributeInfo(new U2(attributeNameIndex),
                                    new U4(attributeLength), info);
                        }
                        code = new Code(maxStack, maxLocals, codeBytes, exceptionTable, attributeInfos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return code;
        }

        public Method toMethod(ClassFile classFile) {
            final CpInfo[] constantPool = classFile.constantPool;
            final int methodAccessFlags = accessFlags.val;
            final String methodName = Utils.getUtf8(constantPool, nameIndex.val);
            final String methodDescriptor = Utils.getUtf8(constantPool, descriptorIndex.val);
            final Code code = getCode(classFile);
            return new Method(methodAccessFlags, methodName, methodDescriptor, code);
        }

    }

    @AllArgsConstructor
    @Getter
    public static final class AttributeInfo {
        final U2 attributeNameIndex;
        final U4 attributeLength;
        final byte[] info;
    }

}
