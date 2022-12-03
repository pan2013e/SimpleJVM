package jvm;

import jvm.classfile.ClassFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    void getUtf8Test() {
        ClassLoader loader = new ClassLoader("src/test/resources/class");
        ClassFile classFile = loader.loadClassFromClassPath("Test");
        String utf8 = Utils.getUtf8(classFile.getConstantPool(), 4);
        assertEquals("java/lang/Object", utf8);
    }

    @Test
    void getClassNameTest() {
        ClassLoader loader = new ClassLoader(ClassLoaderTest.RT_JAR);
        ClassFile classFile = loader.loadClassFromClassPath("java/lang/String");
        String className = Utils.getClassName(classFile.getConstantPool(), classFile.getThisClass().getVal());
        assertEquals("java/lang/String", className);
        String superClassName = Utils.getClassName(classFile.getConstantPool(), classFile.getSuperClass().getVal());
        assertEquals("java/lang/Object", superClassName);
    }

    @Test
    void classNameConversionTest() {
        java.lang.Class<?> clazz = java.lang.String.class;
        assertTrue(Utils.isDotClassName("java.lang.String"));
        assertTrue(Utils.isSlashClassName("java/lang/String"));
        assertEquals("java/lang/String", Utils.convertClassNameToSlash(clazz));
        assertEquals("java/lang/String", Utils.convertClassNameToSlash("java.lang.String"));
        assertEquals("java/lang/String", Utils.convertClassNameToSlash("java/lang/String"));
    }

}