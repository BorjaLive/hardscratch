package hardscratch;

import org.lwjgl.*;
import hardscratch.base.*;
import java.io.File;

public class HardScratch {

    public void run(){
        System.out.println("LWJGL " + Version.getVersion());
        System.out.println("HARDSCRATCH v1.0.5B Linux");
        Global.sayLinesOfCode();

        Display mainWindow = new Display();
        mainWindow.init();

        while(mainWindow.isRunning()) {
            mainWindow.update();
        }

        mainWindow.terminate();
        SoundPlayer.stop();
    }
    
    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("src/natives/").getAbsolutePath());
        
        new HardScratch().run();
    }
    
}
