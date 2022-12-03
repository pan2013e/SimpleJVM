package jvm;

import jvm.classfile.ClassFile;
import lombok.Getter;

@Getter
public class Class {

    final ClassFile classFile;
    final String name;
    final Class superClass;
    final Method[] methods;

    int stat;

    public Class(String name, ClassFile classFile, Class superClass, Method[] methods) {
        this.classFile = classFile;
        this.name = name;
        this.superClass = superClass;
        this.methods = methods;

        if(methods != null) {
            for(Method m : methods) {
                m.clazz = this;
            }
        }
    }

}
