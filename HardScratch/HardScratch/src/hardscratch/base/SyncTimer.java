package hardscratch.base;

import org.lwjgl.glfw.GLFW;

public class SyncTimer {
     
    private int mode;
    private double timeThen;
 
    public SyncTimer(){
        timeThen = System.nanoTime();
    }
     
    public void sync(double fps) throws Exception {
        double resolution = 1000000000.0D;
        double timeNow =  System.nanoTime();
         
        double gapTo = resolution / fps + timeThen;

        while (gapTo < timeNow) 
            gapTo = resolution / fps + gapTo;
        
        while (gapTo > timeNow) {
            Thread.sleep(1);
            timeNow = System.nanoTime();
        }

        timeThen = gapTo; 
    }
}
