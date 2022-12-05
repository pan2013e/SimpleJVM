package jvm.runtime;

import jvm.Utils;
import jvm.classfile.ClassFile;
import lombok.Getter;

import java.util.Objects;

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

    // find static method only in current class, not in super class
    public Method getSpecialStaticMethod(String name, String descriptor) {
        for(Method m : methods) {
            if(Utils.isStatic(m.accessFlags)
                    && Objects.equals(name, m.name)
                    && Objects.equals(descriptor, m.descriptor)) {
                return m;
            }
        }
        return null;
    }

    // find static method in current class and super class recursively
    public Method getStaticMethod(String name, String descriptor) {
        final Method m = getSpecialStaticMethod(name, descriptor);
        if(m != null) {
            return m;
        }
        if(superClass == null) {
            return null;
        }
        return superClass.getStaticMethod(name, descriptor);
    }

}
