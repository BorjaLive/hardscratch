package hardscratch;

import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.subParts.Constructor;
import java.util.ArrayList;
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
    
    public static final float FUNCTION_QR_A = 4.857143f;
    public static final float FUNCTION_QR_B = 0.1428571f;
    public static final float FUNCTION_QR_C = -0.0003571429f;
    
    public static Texture TEXTURE_TEST, TEXTURE_CAT, TEXTURE_ARROW_1, TEXTURE_ARROW_2, TEXTURE_FINDERSLIDER, TEXTURE_HOUSE, TEXTURE_SAVE;
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
            COLOR_DECLARATOR,
            COLOR_INICIALIZER,
            COLOR_EXTRAVAR,
            COLOR_CONVERTER,
            COLOR_ASIGNATOR,
            COLOR_SETIF,
            COLOR_SETSWITCH,
            COLOR_SEQUENTIAL,
            COLOR_ASIGNATOR_SEQ,
            COLOR_IFTHEN,
            COLOR_SWITCHCASE,
            COLOR_WAITON,
            COLOR_WAITFOR,
            COLOR_FORNEXT,
            COLOR_SUMMONER_BACK,
            COLOR_SUMMONER_BORDER,
            COLOR_GUI_1,
            COLOR_GUI_2,
            COLOR_GUI_3,
            COLOR_GUI_4,
            COLOR_GUI_5,
            COLOR_GUI_6
            ;
    
    
    //Helping consts
    public static final int
            ROOM_DESIGN = 1,
            ROOM_IMPLEMENT = 2,
            ROOM_SIMULATE = 3,
            ROOM_MENU = 4,
            ROOM_CREATE = 5,
            ROOM_OPEN = 6,
            ROOM_SETTINGS = 7
            ;
    public static final int
            HOLE_VAR = 1,
            HOLE_VAR_INOUT = 2,
            HOLE_VAR_TYPE = 3,
            HOLE_CONSTRUCTOR = 4,   //Para todos los elementos del constructor
            CONSTRUCTOR_LITERAL_CONST = 5,
            CONSTRUCTOR_OPERATORS = 6,
            CONSTRUCTOR_OPERATORS_PLUS = 7,  //agrega el =
            CONSTRUCTOR_LITERAL_VARS = 8,
            HOLE_EDGE = 9
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
            TIP_CONSTRUCTOR_CLOSE = 26,
            TIP_VAR_INT = 27,
            TIP_VAR_BIT = 28,
            TIP_VAR_ARRAY = 29,
            TIP_EDGE_RISING = 30,
            TIP_EDGE_LOWERING = 31
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
            SUMMON_CONSTRUCTOR_CLOSE = 30,
            SUMMON_CONVERTER = 31,
            SUMMON_ASIGNATOR = 32,
            SUMMON_TIP_INT = 33,
            SUMMON_TIP_BIT = 34,
            SUMMON_TIP_ARRAY = 35,
            SUMMON_SETIF = 36,
            SUMMON_SETSWITCH = 37,
            SUMMON_SEQUENTIAL = 38,
            SUMMON_ASIGNATOR_SEQ = 39,
            SUMMON_IFTHEN = 40,
            SUMMON_SWITCHCASE = 41,
            SUMMON_WAITON = 42,
            SUMMON_WAITFOR = 43,
            SUMMON_FORNEXT = 44,
            SUMMON_TIP_RISSING = 45,
            SUMMON_TIP_LOWERING = 46
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
            PORT_EXTRAVAR = 3,
            PORT_SEQUENTIAL = 4
            ;
    public static final int
            EVENT_RESIZE =1,
            EVENT_SAVE = 2,
            EVENT_GO_HOMO = 3,
            EVENT_GO_DESIGN = 4,
            EVENT_GO_IMPLEMENT = 5,
            EVENT_GO_SIMULATE = 6,
            EVENT_GO_OPEN = 7,
            EVENT_GO_CREATE = 8,
            EVENT_GO_SETTINGS = 9,
            EVENT_EXIT = 10,
            EVENT_FINDER_TOGGLE = 11,
            EVENT_FINDER_MOVE = 12,
            EVENT_DRAW_BACKGROUND = 13,
            EVENT_DRAW_FLOD = 14,
            EVENT_TOOGLE_TOGGLE = 15,
            EVENT_DOCK = 16
            ;
    
    public static float distance(int x1, int y1, int x2, int y2){
        return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    
    
    public static void load(){
        TEXTURE_TEST = new Texture("src/res/img/test.png");
        TEXTURE_CAT = new Texture("src/res/img/cat.jpg");
        TEXTURE_ARROW_1 = new Texture("src/res/img/arrow1.png");
        TEXTURE_ARROW_2 = new Texture("src/res/img/arrow2.png");
        TEXTURE_FINDERSLIDER = new Texture("src/res/img/finderSlider.png");
        TEXTURE_HOUSE = new Texture("src/res/img/house.png");
        TEXTURE_SAVE = new Texture("src/res/img/save.png");
        FONT_TEST = new Font("src/res/fonts/test.png",32,32,16,16);
        FONT_MONOFONTO = new Font("src/res/fonts/monofonto.png",64,64,48,64+5);//Sumarle +5 al ultimo 64
        
        
        //Color load
        COLOR_TEXT_INPUT = new float[]              {  0/255f,  0/255f,  0/255f};
        COLOR_TEXT_INPUT_PLACEHOLDER = new float[]  { 35/255f, 35/255f, 35/255f};
        TEXT_INPUT_BACK = new float[]               { 96/255f,125/255f,139/255f};
        COLOR_BORDER_UNSELECTED = new float[]       {255/255f,255/255f,255/255f};
        COLOR_BORDER_SELECTED = new float[]         {255/255f,213/255f, 79/255f};
        COLOR_HOLE_BACK = new float[]               { 40/255f, 45/255f, 45/255f};
        COLOR_SUMMONER_BACK = new float[]           { 76/255f,152/255f,145/255f};
        COLOR_SUMMONER_BORDER = new float[]         {  1/255f, 49/255f, 44/255f};
        //Piezas
        COLOR_DECLARATOR = new float[]              {255/255f,108/255f,202/255f};
        COLOR_INICIALIZER = new float[]             {255/255f,127/255f,148/255f};
        COLOR_EXTRAVAR = new float[]                {225/255f, 80/255f, 80/255f};
        COLOR_CONVERTER = new float[]               {127/255f,255/255f,130/255f};
        COLOR_ASIGNATOR = new float[]               {119/255f,242/255f, 95/255f};
        COLOR_SETIF = new float[]                   {253/255f,255/255f, 69/255f};
        COLOR_SETSWITCH = new float[]               {249/255f,174/255f, 85/255f};
        COLOR_SEQUENTIAL = new float[]              { 80/255f, 71/255f,255/255f};
        COLOR_ASIGNATOR_SEQ = new float[]           { 50/255f,185/255f,255/255f};
        COLOR_IFTHEN = new float[]                  { 50/255f,248/255f,255/255f};
        COLOR_SWITCHCASE = new float[]              { 50/255f,255/255f,137/255f};
        COLOR_WAITFOR = new float[]                 {118/255f,114/255f,234/255f};
        COLOR_WAITON = new float[]                  {176/255f,139/255f,230/255f};
        COLOR_FORNEXT = new float[]                 {118/255f, 83/255f,182/255f};
        //Colores de GUI
        COLOR_GUI_1 = new float[]                   { 97/255f, 97/255f, 97/255f};
        COLOR_GUI_2 = new float[]                   {126/255f, 87/255f,194/255f};
        COLOR_GUI_3 = new float[]                   {  0/255f,  0/255f,  0/255f};
        COLOR_GUI_4 = new float[]                   {233/255f, 30/255f, 99/255f};
        COLOR_GUI_5 = new float[]                   {255/255f,255/255f,255/255f};
        COLOR_GUI_6 = new float[]                   {116/255f,179/255f,179/255f};
        
        //COLOR_DECLARATOR = new int[]{255,71,240};
    }
    
    public static void unBind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public static void moveA2R(ElementBase e, ElementBase base, int x, int y){
        //Mover de forma relativa un elemento de forma relativa
        e.move(base.getX()+x-e.getX(), base.getY()+y-e.getY());
    }
    public static void moveA2R(Port e, ElementBase base, int x, int y){
        e.move(base.getX()+x-e.getX(), base.getY()+y-e.getY());
    }
    
    public static void addSEQPorts(Element e, int x1, int y1, int x2, int y2, float[] color){
        e.addShape(new Shape_Square(0, 0, color, 1, 3, 60, -30), x1+30, y1);
        e.addShape(new Shape_Square(0, 0, color, 1, 3, 60, -30), x1+120, y1);
        e.addPort(x1, x1+210, y1-30, y1, PORT_MALE, PORT_SEQUENTIAL);
        e.addShape(new Shape_Square(0, 0, color, 1, 3, 30, 30), x2, y2);
        e.addShape(new Shape_Square(0, 0, color, 1, 3, 30, 30), x2+90, y2);
        e.addShape(new Shape_Square(0, 0, color, 1, 3, 30, 30), x2+180, y2);
        e.addPort(x2, x2+210, y2, y2+30, PORT_FEMALE, PORT_SEQUENTIAL);
    }
    
}
