package jvm;

public class TestUtils {

    public static int getJavaClassMagic() {
        return 0xCAFEBABE;
    }

    public static int getHostVMVersion() {
        final int offset = 44;
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if (dot != -1) {
                version = version.substring(0, dot);
            }
        }
        return Integer.parseInt(version) + offset;
    }



}
