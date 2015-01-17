package com.albertlabs.mtanks;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;

class TankLoader{
      
   /*
    public Tank loadTank(){

    }
    */


    /** where shall the compiled class be saved to (should exist already) */
    private static String classOutputFolder = "temp";

    private static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject>
    {
        public void report(Diagnostic<? extends JavaFileObject> diagnostic)
        {

            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("code->" + diagnostic.getCode());
            System.out.println("Message->"
                    + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Source->" + diagnostic.getSource());
            System.out.println(" ");
        }
    }

    /** java File Object represents an in-memory java source file <br>
     * so there is no need to put the source file on hard disk  **/
    private static class InMemoryJavaFileObject extends SimpleJavaFileObject
    {
        private String contents = null;

        public InMemoryJavaFileObject(String className, String contents) throws Exception
        {
            super(URI.create("string:///" + className.replace('.', '/')
                    + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
            this.contents = contents;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException
        {
            return contents;
        }
    }

    /** Get a simple Java File Object ,<br>
     * It is just for demo, content of the source code is dynamic in real use case */
    private static JavaFileObject getJavaFileObject(String contents)
    {
        JavaFileObject so = null;
        try
        {
            so = new InMemoryJavaFileObject("math.Calculator", contents);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return so;
    }

    /** compile your files by JavaCompiler */
    private static void compile(Iterable<? extends JavaFileObject> files)
    {
        //get system compiler:
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // for compilation diagnostic message processing on compilation WARNING/ERROR
        MyDiagnosticListener c = new MyDiagnosticListener();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(c,
                Locale.ENGLISH,
                null);
        //specify classes output folder
        Iterable options = Arrays.asList("-d", classOutputFolder);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
                c, options, null,
                files);
        Boolean result = task.call();
        if (result == true)
        {
            System.out.println("Succeeded");
        }
    }

    /** run class from the compiled byte code file by URLClassloader */
    private static Tank runIt()
    {
        // Create a File object on the root of the directory
        // containing the class file
        File file = new File(classOutputFolder);

        try
        {
            // Convert File to a URL
            URL url = file.toURL(); // file:/classes/demo
            URL[] urls = new URL[] { url };

            // Create a new class loader with the directory
            ClassLoader loader = new URLClassLoader(urls);

            // Load in the class; Class.childclass should be located in
            // the directory file:/class/demo/
            Class thisClass = loader.loadClass("math.Calculator");

            Object instance = thisClass.newInstance();
            return (Tank) instance;
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception
    {
        //1.Construct an in-memory java source file from your dynamic code
        StringBuilder contents = new StringBuilder(
                "package com.albertlabs.mtanks;"+
                        "public class NewTank extends Tank{ " +
                        "public NewTank(){" +
                        "super(100f, 100f, 30f, 30f, 0f);" +
                        "}" +
                        "}");

        JavaFileObject file = getJavaFileObject(contents.toString());
        Iterable<? extends JavaFileObject> files = Arrays.asList(file);

        //2.Compile your files by JavaCompiler
        compile(files);

        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        Tank tank = runIt();
    }
}