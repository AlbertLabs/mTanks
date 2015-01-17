package com.albertlabs.mtanks;

class TankLoader{
    
   /* TankLoader(..., string url){
        super(...)
            
            // Prepare source somehow.
            String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";

        //load code
        
        //parse code
            
        //compile code
        
        // Save source in .java file.
        File root = new File("/java"); // On Windows running on C:\, this is C:\java.
        File sourceFile = new File(root, "test/Test.java");
        sourceFile.getParentFile().mkdirs();
        new FileWriter(sourceFile).append(source).close();

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        // Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
        Object instance = cls.newInstance(); // Should print "world".
        System.out.println(instance); // Should print "test.Test@hashcode".
    }
    
    public Tank loadTank(){

    }
    */
    
}