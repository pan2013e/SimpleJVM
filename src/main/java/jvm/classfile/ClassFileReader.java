package jvm.classfile;

import static jvm.classfile.Types.*;
import static jvm.Tags.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class ClassFileReader {

    @NonNull private final DataInputStream dis;

    public ClassFileReader(@NonNull final String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        dis = new DataInputStream(fis);
    }

    public @NonNull ClassFile read() {
        try {
            int magic = readU4();
            int minorVersion = readU2();
            int majorVersion = readU2();

            int cpSize = readU2(); // constant_pool_count
            CpInfo[] cpInfos = readConstantPool(cpSize); // constant_pool

            int accessFlags = readU2();
            int thisClass = readU2();
            int superClass = readU2();

            int interfacesCount = readU2();
            U2[] interfaces = readInterfaces(interfacesCount);

            int fieldsCount = readU2();
            FieldInfo[] fields = readFields(fieldsCount);

            int methodsCount = readU2();
            MethodInfo[] methods = readMethods(methodsCount);

            int attributeCount = readU2();
            AttributeInfo[] attributeInfos = readAttributeInfos(attributeCount);

            return new ClassFile(new U4(magic), new U2(minorVersion), new U2(majorVersion),
                    new U2(cpSize), cpInfos, new U2(accessFlags), new U2(thisClass),
                    new U2(superClass), new U2(interfacesCount), interfaces,
                    new U2(fieldsCount), fields, new U2(methodsCount), methods,
                    new U2(attributeCount), attributeInfos);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read class file");
        }
    }

    private byte[] readBytes(int size) throws IOException {
        byte[] data = new byte[size];
        dis.read(data);
        return data;
    }

    private int readU4() throws IOException {
        return dis.readInt();
    }

    private int readU2() throws IOException {
        return dis.readUnsignedShort();
    }

    private CpInfo[] readConstantPool(int cpSize) throws IOException {
        CpInfo[] cpInfos = new CpInfo[cpSize];
        for(int i = 1;i < cpSize;i++) {
            int tag = dis.readUnsignedByte();
            byte[] info;
            int step = 0;
            switch (tag) {
                case Constant.CONSTANT_Fieldref:
                case Constant.CONSTANT_Methodref:
                case Constant.CONSTANT_InterfaceMethodref:
                case Constant.CONSTANT_InvokeDynamic:
                case Constant.CONSTANT_NameAndType:
                case Constant.CONSTANT_Integer:
                case Constant.CONSTANT_Float:
                    info = readBytes(4);
                    break;
                case Constant.CONSTANT_MethodHandle:
                    info = readBytes(3);
                    break;
                case Constant.CONSTANT_Class:
                case Constant.CONSTANT_String:
                case Constant.CONSTANT_MethodType:
                    info = readBytes(2);
                    break;
                case Constant.CONSTANT_Utf8:
                    int ulen = dis.readUnsignedShort();
                    info = readBytes(ulen);
                    break;
                case Constant.CONSTANT_Double:
                case Constant.CONSTANT_Long:
                    info = readBytes(8);
                    step = 1;
                    break;
                default:
                    throw new ClassFileException("unknown tag " + tag);
            }
            cpInfos[i] = new CpInfo(new U1(tag), info);
            i += step;
        }
        return cpInfos;
    }

    private U2[] readInterfaces(int interfacesCount) throws IOException {
        U2[] interfaces = new U2[interfacesCount];
        for(int i = 0;i < interfacesCount;i++) {
            interfaces[i] = new U2(readU2());
        }
        return interfaces;
    }

    private FieldInfo[] readFields(int fieldsCount) throws IOException {
        FieldInfo[] fields = new FieldInfo[fieldsCount];
        for (int i = 0; i < fieldsCount; i++) {
            int fieldAccessFlags = readU2();
            int fieldNameIndex = readU2();
            int fieldDescriptor = readU2();
            int fieldAttributesCount = readU2();
            AttributeInfo[] fieldAttributeInfo = readAttributeInfos(fieldAttributesCount);

            fields[i] = new FieldInfo(
                    new U2(fieldAccessFlags),
                    new U2(fieldNameIndex),
                    new U2(fieldDescriptor),
                    new U2(fieldAttributesCount),
                    fieldAttributeInfo);
        }
        return fields;
    }

    private MethodInfo[] readMethods(int methodsCount) throws IOException {
        MethodInfo[] methods = new MethodInfo[methodsCount];
        for (int i = 0; i < methodsCount; i++) {
            int methodAccessFlags = readU2();
            int methodNameIndex = readU2();
            int methodDescriptor = readU2();
            int methodAttributesCount = readU2();
            AttributeInfo[] methodAttributeInfo = readAttributeInfos(methodAttributesCount);

            methods[i] = new MethodInfo(
                    new U2(methodAccessFlags),
                    new U2(methodNameIndex),
                    new U2(methodDescriptor),
                    new U2(methodAttributesCount),
                    methodAttributeInfo);
        }
        return methods;
    }

    private AttributeInfo[] readAttributeInfos(int attributeCount) throws IOException {
        AttributeInfo[] attributeInfos = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            int attributeNameIndex = readU2();
            int attributeLength = readU4();
            byte[] info = readBytes(attributeLength);

            attributeInfos[i] = new AttributeInfo(new U2(attributeNameIndex), new U4(attributeLength), info);
        }
        return attributeInfos;
    }

}
