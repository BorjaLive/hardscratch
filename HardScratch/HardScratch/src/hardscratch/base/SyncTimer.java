package hardscratch.base;

public class SyncTimer {
     
    private static double timeThen;
    private static int targetFPS;
 
    public static void initer(int fps){
        SyncTimer.timeThen = System.nanoTime();
        SyncTimer.targetFPS = fps;
    }
     
    public static void sync() throws Exception {
        double resolution = 1000000000.0D;
        double timeNow =  System.nanoTime();
         
        double gapTo = resolution / targetFPS + timeThen;

        while (gapTo < timeNow) 
            gapTo = resolution / targetFPS + gapTo;
        
        while (gapTo > timeNow) {
            Thread.sleep(1);
            timeNow = System.nanoTime();
        }

        timeThen = gapTo; 
    }
}
