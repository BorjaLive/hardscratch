package hardscratch;

import hardscratch.base.*;
import hardscratch.base.shapes.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Global {
    
    public static final String RESOURCES = "src/res/";
    
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 640;
    public static final boolean FULLSCREEN = false;
    
    public static final int LAYOUT_TOP = 70;
    public static int LAYOUT_LEFT = 400;
    
    public static final float MOUSE_SCROLL_SEPED = 10;
    
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
    public static Font FONT_TEST, FONT_MONOFONTO;
    
    public static final String TASKBAR_ICON = RESOURCES+"img/icon_light.png";

    public static final float[] COLOR_AQUA = new float[]    {0, 1, 1};
    public static final float[] COLOR_RED = new float[]     {1, 0, 0};
    public static final float[] COLOR_PURPLE = new float[]  {1, 0, 1};
    public static final float[] COLOR_WHITE = new float[]   {1, 1, 1};
    
    //Paleta de color
    public static float[]
            COLOR_TEXT_INPUT,
            COLOR_TEXT_INPUT_PLACEHOLDER,
            TEXT_INPUT_BACK,
            COLOR_BORDER_UNSELECTED,
            COLOR_BORDER_SELECTED,
            COLOR_HOLE_BACK,
            COLOR_DECLARATOR;
    
    
    //Helping consts
    public static final int
            HOLE_VAR = 1,
            HOLE_VAR_INOUT = 2,
            HOLE_VAR_TYPE = 3
            ;
    public static final int
            TIP_VAR_IN = 1,
            TIP_VAR_OUT = 2,
            TIP_VAR_SIGNAL = 3,
            TIP_VAR_CONST = 4
            ;
    public static final int
            SUMMON_DECLARATRON = 1,
            SUMMON_TIP_IN = 2,
            SUMMON_TIP_OUT = 3,
            SUMMON_TIP_SIGNAL = 4,
            SUMMON_TIP_CONST = 5
            ;
    
    public static float distance(int x1, int y1, int x2, int y2){
        return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    
    
    public static void load(){
        TEXTURE_TEST = new Texture("src/res/img/test.png");
        TEXTURE_CAT = new Texture("src/res/img/cat.jpg");
        FONT_TEST = new Font("src/res/fonts/test.png",32,32,16,16);
        FONT_MONOFONTO = new Font("src/res/fonts/monofonto.png",64,64,48,64+5);//Sumarle +5 al ultimo 64
        
        
        //Color load
        COLOR_TEXT_INPUT = new float[]              {  0/255f,  0/255f,  0/255f};
        COLOR_TEXT_INPUT_PLACEHOLDER = new float[]  { 25/255f, 25/255f, 25/255f};
        TEXT_INPUT_BACK = new float[]               { 96/255f,125/255f,139/255f};
        COLOR_BORDER_UNSELECTED = new float[]       {255/255f,255/255f,255/255f};
        COLOR_BORDER_SELECTED = new float[]         {255/255f,213/255f, 79/255f};
        COLOR_HOLE_BACK = new float[]               { 40/255f, 45/255f, 45/255f};
        COLOR_DECLARATOR = new float[]              {255/255f, 71/255f,240/255f};
        
        //COLOR_DECLARATOR = new int[]{255,71,240};
    }
    
    public static void unBind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
}
