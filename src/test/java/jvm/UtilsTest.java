package jvm;

import jvm.classfile.ClassFile;
import jvm.misc.Utils;
import jvm.runtime.ClassLoader;
import jvm.runtime.ClassLoaderTest;
import jvm.runtime.MetaSpace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @Test
    void getUtf8Test() {
        ClassLoader loader = ClassLoader.createSystemClassLoader(ClassLoaderTest.TEST_DIR);
        ClassFile classFile = loader.loadClassFileFromClassPath("Test");
        String utf8 = Utils.getUtf8(classFile.getConstantPool(), 4);
        assertEquals("java/lang/Object", utf8);
    }

    @Test
    void getClassNameTest() {
        MetaSpace.init();
        ClassLoader loader = MetaSpace.getBootstrapClassLoader();
        ClassFile classFile = loader.loadClassFileFromClassPath("java/lang/String");
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

    @Test
    void getArgSlotSizeTest() {
        assertEquals(0, Utils.getArgSlotSize("()V"));
        assertEquals(4, Utils.getArgSlotSize("(II[CI)V"));
        assertEquals(4, Utils.getArgSlotSize("(IILjava/lang/String;I)V"));
        assertEquals(4, Utils.getArgSlotSize("(II[Ljava/lang/String;I)V"));
        assertEquals(4, Utils.getArgSlotSize("([[[II[Ljava/lang/String;I)V"));
        assertEquals(10, Utils.getArgSlotSize("([[[I[J[DI[Ljava/lang/String;IJD)V"), "([[[I[J[DI[Ljava/lang/String;IJD)V args slots is 10");
    }

}
