package jvm.classfile;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static jvm.classfile.Types.*;

@AllArgsConstructor
@Getter
public class ClassFile {

    final U4 magic;
    final U2 minorVersion;
    final U2 majorVersion;
    final U2 constantPoolCount; // 常量池大小
    final CpInfo[] constantPool; // 常量池
    final U2 accessFlags; // 访问标志
    final U2 thisClass; // 当前类名索引
    final U2 superClass; // 父类类名索引
    final U2 interfacesCount; // 接口数
    final U2[] interfaces; // 接口
    final U2 fieldsCount; // 字段数
    final FieldInfo[] fields; // 字段
    final U2 methodsCount; // 方法数
    final MethodInfo[] methods; // 方法
    final U2 attributesCount; // 属性数
    final AttributeInfo[] attributes; // 属性

}
