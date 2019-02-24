package hardscratch;

import hardscratch.base.*;
import hardscratch.base.shapes.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Global {
    
    public static final String RESOURCES = "src/res/";
    
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final boolean FULLSCREEN = true;
    
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
            HOLE_VAR_TYPE = 3,
            HOLE_CONSTRUCTOR = 4,   //Para todos los elementos del constructor
            CONSTRUCTOR_LITERAL_CONST = 5,
            CONSTRUCTOR_OPERATORS = 6,
            CONSTRUCTOR_OPERATORS_PLUS = 7,  //agrega el =
            CONSTRUCTOR_LITERAL_VARS = 8;
            ;
    public static final int
            TIP_VAR_IN = 1,
            TIP_VAR_OUT = 2,
            TIP_VAR_SIGNAL = 3,
            TIP_VAR_CONST = 4,
            TIP_CONSTRUCTOR_LITERAL_N = 5,  //Number
            TIP_CONSTRUCTOR_LITERAL_B = 6,  //Bit / char
            TIP_CONSTRUCTOR_LITERAL_A = 7,  //Array
            TIP_CONSTRUCTOR_VAR_I = 8,  //Integer
            TIP_CONSTRUCTOR_VAR_B = 9,  //Bit / char
            TIP_CONSTRUCTOR_VAR_A = 10, //Array
            TIP_CONSTRUCTOR_VAR_C = 11,  //Const
            TIP_CONSTRUCTOR_EQUALITY = 12,
            TIP_CONSTRUCTOR_CONCAT = 13,
            TIP_CONSTRUCTOR_ARITH_ADD = 14,
            TIP_CONSTRUCTOR_ARITH_SUB = 15,
            TIP_CONSTRUCTOR_ARITH_TIM = 16,
            TIP_CONSTRUCTOR_ARITH_TAK = 17,
            TIP_CONSTRUCTOR_LOGIC_AND = 18,
            TIP_CONSTRUCTOR_LOGIC_OR = 19,
            TIP_CONSTRUCTOR_LOGIC_XOR = 20,
            TIP_CONSTRUCTOR_LOGIC_NAND = 21,
            TIP_CONSTRUCTOR_LOGIC_NOR = 22,
            TIP_CONSTRUCTOR_LOGIC_XNOR = 23,
            TIP_CONSTRUCTOR_LOGIC_NOT = 24,
            TIP_CONSTRUCTOR_OPEN = 25,
            TIP_CONSTRUCTOR_CLOSE = 26
            ;
    public static final int
            SUMMON_DECLARATRON = 1,
            SUMMON_TIP_IN = 2,
            SUMMON_TIP_OUT = 3,
            SUMMON_TIP_SIGNAL = 4,
            SUMMON_TIP_CONST = 5,
            SUMMON_INICIALIZER = 6,
            SUMMON_EXTRAVAR = 7,
            SUMMON_CONSTRUCTOR_LITERAL_N = 8,
            SUMMON_CONSTRUCTOR_LITERAL_B = 9,
            SUMMON_CONSTRUCTOR_LITERAL_A = 10,
            SUMMON_CONSTRUCTOR_VAR_I = 11,
            SUMMON_CONSTRUCTOR_VAR_B = 12,
            SUMMON_CONSTRUCTOR_VAR_A = 13,
            SUMMON_CONSTRUCTOR_VAR_C = 15,
            SUMMON_CONSTRUCTOR_EQUALITY = 16,
            SUMMON_CONSTRUCTOR_CONCAT = 17,
            SUMMON_CONSTRUCTOR_ARITH_ADD = 18,
            SUMMON_CONSTRUCTOR_ARITH_SUB = 19,
            SUMMON_CONSTRUCTOR_ARITH_TIM = 20,
            SUMMON_CONSTRUCTOR_ARITH_TAK = 21,
            SUMMON_CONSTRUCTOR_LOGIC_AND = 22,
            SUMMON_CONSTRUCTOR_LOGIC_OR = 23,
            SUMMON_CONSTRUCTOR_LOGIC_XOR = 24,
            SUMMON_CONSTRUCTOR_LOGIC_NAND = 25,
            SUMMON_CONSTRUCTOR_LOGIC_NOR = 26,
            SUMMON_CONSTRUCTOR_LOGIC_XNOR = 27,
            SUMMON_CONSTRUCTOR_LOGIC_NOT = 28,
            SUMMON_CONSTRUCTOR_OPEN = 29,
            SUMMON_CONSTRUCTOR_CLOSE = 30
            ;
    public static final int
            CREATOR_I = 1,
            CREATOR_A = 2,
            CREATOR_E = 3,
            CREATOR_N = 4
            ;
    public static final boolean
            PORT_FEMALE = true,
            PORT_MALE = false
            ;
    public static final int
            PORT_INICIALIZER = 2,
            PORT_EXTRAVAR = 3
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
        COLOR_TEXT_INPUT_PLACEHOLDER = new float[]  { 35/255f, 35/255f, 35/255f};
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
