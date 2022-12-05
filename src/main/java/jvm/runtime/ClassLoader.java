package jvm.runtime;

import jvm.Utils;
import jvm.classfile.ClassFile;
import jvm.classfile.ClassFileReader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static jvm.classfile.Types.*;
import static jvm.Tags.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
        Method[] methods = new Method[classFile.getMethodsCount().getVal()];
        for(int i = 0; i < methods.length; i++) {
            MethodInfo methodInfo = classFile.getMethods()[i];
            methods[i] = methodInfo.toMethod(classFile);
        }
        final Class clazz = new Class(className, classFile, superClass, methods);
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
        // preparation
        // TODO: init static fields
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