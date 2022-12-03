package jvm;

import jvm.classfile.ClassFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassLoaderTest {

    public static final String USER_HOME = System.getProperty("user.home");

    public static final String JDK_NAME = "azul-1.8.0_352";

    public static final String JAVA_HOME =
            USER_HOME + "/Library/Java/JavaVirtualMachines/" + JDK_NAME + "/Contents/Home";

    public static final String RT_JAR = JAVA_HOME + "/jre/lib/rt.jar";

    @Test
    void loadClassFromJarTest() {
        final ClassLoader classLoader = new ClassLoader(RT_JAR);
        final ClassFile classFile = classLoader.loadClassFromJar(RT_JAR, java.lang.Object.class);
        assertNotNull(classFile, "java/lang/Object should not be null");
    }

    @Test
    void loadClassFromDirTest() {
        final ClassLoader classLoader = new ClassLoader("src/test/resources/class");
        final ClassFile classFile = classLoader.loadClassFromDir("src/test/resources/class", "Test");
        assertNotNull(classFile, "Test should not be null");
    }

    @Test
    void loadClassFromClassPathTest() {
        final String classPath = "src/test/resources/class:" + RT_JAR;
        final ClassLoader classLoader = new ClassLoader(classPath);
        final ClassFile classFile1 = classLoader.loadClassFromClassPath("Test");
        assertNotNull(classFile1, "Test should not be null");
        final ClassFile classFile2 = classLoader.loadClassFromClassPath(java.lang.Object.class);
        assertNotNull(classFile2, "java/lang/Object should not be null");
    }

    @Test
    void loadJavaLangObjectTest() {
        final ClassLoader loader = new ClassLoader(RT_JAR);
        final Class clazz = loader.findClass("java.lang.Object");
        assertNull(clazz.superClass, "java.lang.Object should not have a super class");
        assertEquals(14, clazz.methods.length, "java.lang.Object should have 14 methods");
    }

    @Test
    void loadTestClassTest() {
        final String classPath = "src/test/resources/class:" + RT_JAR;
        final ClassLoader loader = new ClassLoader(classPath);
        final Class clazz = loader.findClass("Test");
        assertNotNull(clazz.superClass, "Test should have a super class");
        assertEquals(2, clazz.methods.length, "Test should have 2 methods");
    }

}
