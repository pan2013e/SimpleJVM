package jvm.runtime;

import jvm.misc.Utils;
import lombok.Getter;

public final class Instance {

    @Getter
    Class clazz;
    Field[] fields;

    Instance superInstance;

    public Instance(Class clazz, Field[] fields) {
        this.clazz = clazz;
        this.fields = fields;
    }

    public Instance() {}

    public Field getField(String name, String descriptor) {
        for (Field field : fields) {
            if (!Utils.isStatic(field.accessFlags)
                    && field.name.equals(name)
                    && field.descriptor.equals(descriptor)) {
                return field;
            }
        }
        if(superInstance != null) {
            return superInstance.getField(name, descriptor);
        }
        return null;
    }

}
