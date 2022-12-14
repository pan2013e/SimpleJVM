package jvm.runtime;

import jvm.classfile.ClassFile;
import jvm.misc.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static jvm.misc.Tags.ClassStat;

@Getter
public class Class {

    final ClassFile classFile;
    final String name;
    final Class superClass;
    final Field[] fields;
    final Method[] methods;
    final Class[] interfaces;
    boolean hasDefaultMethod;

    @Setter
    int stat;

    public Class(String name, ClassFile classFile,
                 Class superClass, Field[] fields, Method[] methods,
                 Class[] interfaces, boolean hasDefaultMethod) {
        this.classFile = classFile;
        this.name = name;
        this.superClass = superClass;
        this.fields = fields;
        this.methods = methods;
        this.interfaces = interfaces;
        this.hasDefaultMethod = hasDefaultMethod;

        if(fields != null) {
            for(Field f : fields) {
                f.clazz = this;
            }
        }
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

    public Field getStaticField(String fieldName, String fieldDescriptor) {
        for(Field f : fields) {
            if(Utils.isStatic(f.accessFlags)
                    && Objects.equals(fieldName, f.name)
                    && Objects.equals(fieldDescriptor, f.descriptor)) {
                return f;
            }
        }
        if(superClass == null) {
            return null;
        }
        return superClass.getStaticField(fieldName, fieldDescriptor);
    }

    public Method getSpecialMethod(String name, String descriptor) {
        for(Method m : methods) {
            if(!Utils.isStatic(m.accessFlags)
                    && Objects.equals(name, m.name)
                    && Objects.equals(descriptor, m.descriptor)) {
                return m;
            }
        }
        return null;
    }

    public Method getVirtualMethod(String name, String descriptor) {
        final Method m = getSpecialMethod(name, descriptor);
        if(m != null) {
            return m;
        }
        if(superClass == null) {
            return null;
        }
        return superClass.getVirtualMethod(name, descriptor);
    }

    public Method getDefaultMethod(String name, String descriptor) {
        Method method;
        if(isInterface() && hasDefaultMethod) {
            method = getSpecialMethod(name, descriptor);
            if(method != null) {
                return method;
            }
        }
        for(Class c : interfaces) {
            method = c.getDefaultMethod(name, descriptor);
            if(method != null) {
                return method;
            }
        }
        return null;
    }

    public Instance newInstance() {
        // copy fields to instance
        final Field[] newFields = new Field[fields.length];
        for(int i = 0; i < newFields.length; i++) {
            final Field field = fields[i];
            Field newField;
            if(field.isStatic()) {
                newField = field; // static field is shared
            } else {
                newField = new Field(field.accessFlags,
                        field.name, field.descriptor);
                newField.init(); // non-static field is initialized
            }
            newFields[i] = newField;
        }
        final Instance instance = new Instance(this, newFields);
        if(superClass != null) {
            instance.superInstance = superClass.newInstance();
        }
        return instance;
    }

    public void clinit() {
        if(stat >= ClassStat.CLASS_INITING) {
            return;
        }
        if(superClass != null) {
            superClass.clinit();
        }
        // initialize interface with default methods
        for(Class c: interfaces) {
            // recursively initialize super interfaces
            for(Class sc: c.interfaces) {
                if(sc.hasDefaultMethod) {
                    sc.clinit();
                }
            }
            if(c.hasDefaultMethod) {
                c.clinit();
            }
        }
        // initialize self
        final Method clinitMethod = getSpecialStaticMethod("<clinit>", "()V");
        if(clinitMethod != null) {
            stat = ClassStat.CLASS_INITING;
            Frame frame = new Frame(clinitMethod);
            Interpreter.execute(frame);
        }
        setStat(ClassStat.CLASS_INITED);
    }

    public boolean isInterface() {
        return Utils.isInterface(classFile.getAccessFlags().getVal());
    }

}
