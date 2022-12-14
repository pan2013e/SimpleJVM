package jvm.runtime;

import jvm.classfile.ClassFile;
import jvm.classfile.ClassFileReader;
import jvm.misc.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static jvm.classfile.Types.*;
import static jvm.misc.Tags.ClassStat;

@RequiredArgsConstructor
public class ClassLoader {

    /* classPath = PATH1:PATH2:... */
    @NonNull private final String classPath;

    public Class defineClass(@NonNull ClassFile classFile) {
        // get current class name
        final CpInfo[] constantPool = classFile.getConstantPool();
        final String className = Utils.getClassName(constantPool, classFile.getThisClass().getVal());
        assert Utils.isSlashClassName(className);
        // check super class
        final int superIdx = classFile.getSuperClass().getVal();
        if(superIdx == 0) { // no super class
            // then current class must be java.lang.Object
            if(!("java/lang/Object").equals(className)) {
                throw new IllegalStateException("must have a super class");
            }
        }
        // load super class recursively (until java/lang/Object)
        Class superClass = null;
        if(superIdx != 0) {
            final String superClassName = Utils.getClassName(constantPool, superIdx);
            superClass = findClass(superClassName);
        }
        // now create current class
        // create interfaces
        final Class[] interfaces = new Class[classFile.getInterfacesCount().getVal()];
        for(int i = 0; i < interfaces.length; i++) {
            final int interfaceIdx = classFile.getInterfaces()[i].getVal();
            final String interfaceName = Utils.getClassName(constantPool, interfaceIdx);
            interfaces[i] = findClass(interfaceName);
        }
        boolean hasDefaultMethod = false;
        // create methods
        Method[] methods = new Method[classFile.getMethodsCount().getVal()];
        for(int i = 0; i < methods.length; i++) {
            final MethodInfo methodInfo = classFile.getMethods()[i];
            methods[i] = methodInfo.toMethod(classFile);
            if(!hasDefaultMethod && Utils.isInterface(classFile.getAccessFlags().getVal())
                    && !Utils.isStatic(methods[i].accessFlags)
                    && !Utils.isAbstract(methods[i].accessFlags)) {
                hasDefaultMethod = true;
            }
        }
        // create fields
        Field[] fields = new Field[classFile.getFieldsCount().getVal()];
        for(int i = 0; i < fields.length; i++) {
            final FieldInfo fieldInfo = classFile.getFields()[i];
            fields[i] = fieldInfo.toField(classFile);
        }
        final Class clazz = new Class(className, classFile,
                superClass, fields, methods, interfaces, hasDefaultMethod);
        clazz.stat = ClassStat.CLASS_LOADED;
        MetaSpace.putClass(className, clazz);
        return clazz;
    }

    public void linkClass(@NonNull Class clazz) {
        // link super class first & recursively
        if(clazz.superClass != null && clazz.superClass.stat < ClassStat.CLASS_LINKED) {
            linkClass(clazz.superClass);
        }
        // verification, skipped
        // preparation, initialize static fields
        for(Field f : clazz.fields) {
            if(Utils.isStatic(f.accessFlags)) {
                f.init();
            }
        }
        // resolution, skipped
        clazz.stat = ClassStat.CLASS_LINKED;
    }

    public Class findClass(@NonNull String className) {
        String name = Utils.convertClassNameToSlash(className);
        Class cachedClass = MetaSpace.findClass(name);
        if(cachedClass != null) {
            return cachedClass;
        }
        ClassFile classFile = loadClassFromClassPath(name);
        Class clazz = defineClass(classFile);
        linkClass(clazz);
        return clazz;
    }

    // Support loading java.lang.* only
    public ClassFile loadClassFromJar(@NonNull String jarPath, @NonNull java.lang.Class<?> clazz) {
        final String name = Utils.convertClassNameToSlash(clazz);
        return loadClassFromJar(jarPath, name);
    }

    public ClassFile loadClassFromJar(@NonNull String jarPath, @NonNull String className) {
        ZipFile jarFile;
        try {
            jarFile = new ZipFile(jarPath);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot open jar file: " + jarPath);
        }
        ZipEntry entry = jarFile.getEntry(className + ".class");
        if (entry == null) {
            throw new IllegalStateException("Cannot find class file: " + className);
        }
        try (
            final DataInputStream dis = new DataInputStream(jarFile.getInputStream(entry))
        ) {
            ClassFileReader reader = new ClassFileReader(dis);
            return reader.read();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read class file: " + className);
        }
    }

    public ClassFile loadClassFromDir(@NonNull String dir, @NonNull String className) {
        final Path path = Path.of(dir, Utils.convertClassNameToSlash(className) + ".class");
        final File file = path.toFile();
        if (!file.exists()) {
            throw new IllegalStateException("Cannot find class file: " + className);
        }
        try (
            final DataInputStream dis = new DataInputStream(file.toURI().toURL().openStream())
        ) {
            ClassFileReader reader = new ClassFileReader(dis);
            return reader.read();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read class file: " + className);
        }
    }

    public ClassFile loadClassFromClassPath(@NonNull java.lang.Class<?> clazz) {
        return loadClassFromClassPath(clazz.getName());
    }

    public ClassFile loadClassFromClassPath(@NonNull String className) {
        final String[] paths = classPath.split(":");
        final String name = Utils.convertClassNameToSlash(className);
        ClassFile classFile = null;
        for (String path : paths) {
            if (path.endsWith(".jar")) {
                try {
                    classFile = loadClassFromJar(path, name);
                    break;
                } catch (IllegalStateException ignore) {}
            } else {
                try {
                    classFile = loadClassFromDir(path, name);
                    break;
                } catch (IllegalStateException ignore) {}
            }
        }
        if(classFile == null) {
            throw new IllegalStateException("Cannot find class file: " + className);
        }
        return classFile;
    }

}