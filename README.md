# TurnBasedRPG

Running:
  
  In the root directory is a file called TurnBasedRPG.jar
  
  Using cmd/terminal, run it using "java -jar TurnBasedRPG.jar" with quotations removed

Compiling:
  
  Load project as a new project in Eclipse
  
  /src/engine/Main.java is the runnable file
  
  Right click on the projects properties
  
  Under Java Build Path, click on the libraries tab
 
 Next add these external jar files:
    
    /lib/jars/jinput.jar
    
    /lib/jars/jogg-0.0.7.jar
    
    /lib/jars/jorbis-0.0.15.jar
    
    /lib/jars/lwjgl.jar
    
    /lib/jars/slick.jar
  
  Next expand lwjgl.jar and click on "Native library location"
  
  Edit this and set it to the correct native folder for your operating system
  
    e.g. /lib/natives/windows
  
  Finally, select /src/engine/Main.java and right click it to run as a java application
