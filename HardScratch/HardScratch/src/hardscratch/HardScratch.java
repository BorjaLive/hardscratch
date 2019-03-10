package hardscratch;

import org.lwjgl.*;
import hardscratch.base.*;

public class HardScratch {

    public void run(){
        System.out.println("LWJGL " + Version.getVersion());
        //Global.sayLinesOfCode();

        Display mainWindow = new Display();
        mainWindow.init();

        while(mainWindow.isRunning()) {
            mainWindow.update();
        }

        mainWindow.terminate();
    }
    
    public static void main(String[] args) {
        new HardScratch().run();
    }
    
}
