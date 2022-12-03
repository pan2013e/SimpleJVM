package jvm;

import jvm.classfile.ClassFile;
import jvm.classfile.ClassFileReader;
import jvm.instruction.Instruction;

import java.io.IOException;
import java.util.Map;

public class Driver {

    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String JDK_NAME = "azul-1.8.0_352";
    private static final String JAVA_HOME =
            USER_HOME + "/Library/Java/JavaVirtualMachines/" + JDK_NAME + "/Contents/Home";
    private static final String RT_JAR = JAVA_HOME + "/jre/lib/rt.jar";

    private static String classPath = Utils.classPathConcat(CURRENT_DIR, RT_JAR);

    private static String className = null;

    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java jvm.Driver [-cp <classpath>] <classname>");
            System.exit(1);
        }
        if (args[0].equals("-cp") || args[0].equals("-classpath")) {
            if (args.length < 3) {
                System.out.println("Usage: java jvm.Driver [-cp <classpath>] <classname>");
                System.exit(1);
            }
            classPath = Utils.classPathConcat(args[1], classPath);
            className = args[2];
        } else {
            className = args[0];
        }
    }

    public static void main(String[] args) {
        parseArgs(args);
        final ClassLoader loader = new ClassLoader(classPath);
        final Class main = loader.findClass(className);
        final Method mainMethod = main.getMethods()[1];
        final Map<Integer, Instruction> instructionMap = mainMethod.getInstructions();
        if(instructionMap.size() == 0) {
            System.out.println("No instructions found");
            System.exit(1);
        }
        Frame frame = new Frame();
        Interpreter.run(frame, instructionMap);
    }

    // for system tests only
    public static void runInterpreter(String file) throws IOException {
        ClassFileReader reader = new ClassFileReader(file);
        ClassFile classFile = reader.read();
        Map<Integer, Instruction> instructionMap = classFile.getMethods()[1]
                .getCode(classFile).getInstructions(classFile.getConstantPool());
        Frame frame = new Frame();
        Interpreter.run(frame, instructionMap);
    }
}
