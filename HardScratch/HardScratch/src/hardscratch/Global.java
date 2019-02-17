package hardscratch;

import hardscratch.base.*;
import hardscratch.base.shapes.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Global {
    
    public static final String RESOURCES = "src/res/";
    
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final boolean FULLSCREEN = false;
    
    //public static final int WINDOW_WIDTH = 1920;
    //public static final int WINDOW_HEIGHT = 1080;
    //public static final boolean FULLSCREEN = true;
    
    
    public static final int FRAME_RATE = 60;
    
    public static boolean DEBUG_MODE = false;
    public static int DRAWING_RADIUS_LIMIT = 100;
    
    public static final float[][] SHAPE_SQUARE = new float[][] {
        new float[] { 0, 0,},
        new float[] { 0, 1,},
        new float[] { 1, 1,},
        new float[] { 1, 0,},
    };
    public static final float[][] SHAPE_TEXTURE = new float[][] {
        new float[] { 0, 1,},
        new float[] { 1, 1,},
        new float[] { 1, 0,},
        new float[] { 0, 0,},
    };
    
    public static Texture TEXTURE_TEST, TEXTURE_CAT;
    public static final String SOUND_TEST = RESOURCES+"sound/test.wav";
    public static final String SOUND_TEST2 = RESOURCES+"sound/pipe.wav";
    public static Font FONT_TEST;
    
    public static final String TASKBAR_ICON = RESOURCES+"img/icon.png";

    public static final int[] COLOR_AQUA = new int[]{0, 255, 255};
    public static final int[] COLOR_RED = new int[]{255, 0, 0};
    public static final int[] COLOR_PURPLE = new int[]{255, 0, 255};
    public static final int[] COLOR_WHITE = new int[]{255, 255, 255};
    
    public static float distance(int x1, int y1, int x2, int y2){
        return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    
    
    public static void load(){
        TEXTURE_TEST = new Texture("src/res/img/test.png");
        TEXTURE_CAT = new Texture("src/res/img/cat.jpg");
        FONT_TEST = new Font("src/res/fonts/test.png",32,32,16,16);
    }
    
    public static void unBind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
}
