package jvm.runtime;

import jvm.classfile.ClassFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ClassLoaderTest {

    public static final String RT_JAR = "src/main/resources/rt.jar";
    public static final String TEST_DIR = "src/test/resources/class";

    public static ClassLoader classLoader;

    @BeforeEach
    void initMetaSpace() {
        MetaSpace.init();
        classLoader = ClassLoader.createSystemClassLoader(TEST_DIR);
        MetaSpace.setClassLoader(classLoader);
    }

    @Test
    void loadClassFromJarTest() {
        final ClassFile classFile = classLoader.loadClassFileFromJar(RT_JAR, "java/lang/Object");
        assertNotNull(classFile, "java/lang/Object should not be null");
    }

    @Test
    void loadClassFromDirTest() {
        final ClassFile classFile = classLoader.loadClassFileFromDir("src/test/resources/class", "Test");
        assertNotNull(classFile, "Test should not be null");
    }

    @Test
    void loadClassFromClassPathTest() {
        final ClassFile classFile1 = classLoader.loadClassFileFromClassPath("Test");
        assertNotNull(classFile1, "Test should not be null");
        final ClassFile classFile2 = classLoader.loadClassFileFromClassPath("java/lang/Object");
        assertNotNull(classFile2, "java/lang/Object should not be null");
    }

    @Test
    void loadJavaLangObjectTest() {
        final Class clazz = classLoader.findClass("java.lang.Object");
        assertNull(clazz.superClass, "java.lang.Object should not have a super class");
        assertEquals(14, clazz.methods.length, "java.lang.Object should have 14 methods");
    }

    @Test
    void loadTestClassTest() {
        final Class clazz = classLoader.findClass("Test");
        assertNotNull(clazz.superClass, "Test should have a super class");
        assertEquals(2, clazz.methods.length, "Test should have 2 methods");
    }

    @Test
    void classCacheTest() {
        final Class clazz1 = classLoader.findClass("Test");
        final Class clazz2 = classLoader.findClass("Test");
        assertEquals(clazz1, clazz2, "Test should be cached");
    }

    @Test
    void hierarchyTest() {
         Class clazz = MetaSpace.findClass("java/lang/Object");
         assertFalse(Arrays.stream(clazz.methods).anyMatch(m -> m.name.equals("fake")));
    }

}
