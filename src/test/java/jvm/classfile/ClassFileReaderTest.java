package jvm.classfile;

import jvm.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassFileReaderTest {

    @Test
    public void testRead() throws IOException {
        ClassFileReader reader = new ClassFileReader("src/test/resources/class/Test.class");
        final ClassFile file = reader.read();

        assertEquals(TestUtils.getJavaClassMagic(), file.magic.val, "magic");
        assertEquals(0, file.minorVersion.val, "minor_version");
        assertEquals(TestUtils.getHostVMVersion(), file.majorVersion.val, "major_version");
        assertEquals(15, file.constantPoolCount.val, "constant_pool_count");
        assertEquals(2, file.methodsCount.val, "methods_count");
        assertEquals(1, file.attributesCount.val, "attributes_count");
    }

}
