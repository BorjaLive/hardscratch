package hardscratch;

import hardscratch.backend.CONF;
import hardscratch.backend.Resolution;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.piezes.WaitCourtain;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Global {
    
    public static final String RESOURCES = "src/res/";
    
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static boolean FULLSCREEN;
    
    public static final int LAYOUT_TOP = 70;
    public static int LAYOUT_LEFT = 400;
    
    public static final float MOUSE_SCROLL_SEPED = 10;
    public static final boolean QUICK_MOVE = false;
    
    public static String SAVE_NAME;
    
    
    public static final int FRAME_RATE = 60;
    
    public static boolean DEBUG_MODE = false;
    public static int DRAWING_RADIUS_LIMIT = 500;   //Algun dia habra que quitar esto
    public static String VERSION_NAME = "v1.0 OpenWound";
    
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
    
    public static final float FUNCTION_QR_A = 0.5f;
    public static final float FUNCTION_QR_B = 1.8f;
    public static final float FUNCTION_QR_C = -1.8f;
    public static final float FUNCTION_SM_A = 0f;
    public static final float FUNCTION_SM_B = 1.8f;
    public static final float FUNCTION_SM_C = -0.8f;
    
    public static Texture  TEXTURE_ARROW_1, TEXTURE_ARROW_2, TEXTURE_FINDERSLIDER, TEXTURE_HOUSE, TEXTURE_SAVE, TEXTURE_BORELICIOUS, TEXTURE_CLOSE_CROSS, TEXTURE_LOGO, TEXTURE_SETTINGS, TEXTURE_TRASHCAN, TEXTURE_IMPORT, TEXTURE_EXPORT;
    public static Texture[] TEXTURE_BCD;
    public static String SOUND_SWITCH_UP = RESOURCES+"sound/switchUp.wav";
    public static String SOUND_SWITCH_DOWN = RESOURCES+"sound/switchDown.wav";
    public static String SOUND_BUTTON_UP = RESOURCES+"sound/ButtonUp.wav";
    public static String SOUND_BUTTON_DOWN = RESOURCES+"sound/ButtonDown.wav";
    public static String SOUND_KEY_PRESS = RESOURCES+"sound/KeyPress.wav";
    public static Font FONT_MONOFONTO, FONT_LCD;
    
    public static final String TASKBAR_ICON = RESOURCES+"img/icon_light.png";
    
    public static final String VMESS_EXE = RESOURCES+"vmess/vmess.exe";
    public static final String LAUNCHER_EXE = "HardScratch.exe";

    public static final float[] COLOR_AQUA = new float[]    {0, 1, 1};
    public static final float[] COLOR_RED = new float[]     {1, 0, 0};
    public static final float[] COLOR_PURPLE = new float[]  {1, 0, 1};
    public static final float[] COLOR_WHITE = new float[]   {1, 1, 1};
    
    //Resoluciones
    public static Resolution[] RESOLUTION_LIST = new Resolution[15];
    
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
            COLOR_GUI_6,
            COLOR_VAR_IN,
            COLOR_VAR_OUT,
            COLOR_VAR_SIGNAL,
            COLOR_VAR_CONST,
            COLOR_IMPLEMENTER,
            COLOR_CIRCUIT_GREEN,
            COLOR_CIRCUIT_DKGREEN,
            COLOR_CIRCUIT_BROWN,
            COLOR_CIRCUIT_DKBROWN,
            COLOR_CIRCUIT_DKGRAY,
            COLOR_CIRCUIT_LIGHT,
            COLOR_CIRCUIT_DKLIGHT,
            COLOR_CIRCUIT_LCD_OFF,
            COLOR_CIRCUIT_LCD_ON,
            COLOR_CIRCUIT_LCD_LETTER,
            COLOR_OK,
            COLOR_NOOK
            ;
    
    
    //Helping consts
    public static final int
            ROOM_DESIGN = 1,
            ROOM_IMPLEMENT = 2,
            ROOM_SIMULATE = 3,
            ROOM_MENU = 4,
            ROOM_CREATE = 5,
            ROOM_OPEN = 6,
            ROOM_SETTINGS = 7,
            ROOM_EASTER = 8
            ;
    public static final int
            HOLE_VAR_INOUT = 2,
            HOLE_VAR_TYPE = 3,
            HOLE_CONSTRUCTOR = 4,   //Para todos los elementos del constructor
            CONSTRUCTOR_LITERAL_CONST = 5,
            CONSTRUCTOR_OPERATORS = 6,
            CONSTRUCTOR_OPERATORS_PLUS = 7,  //agrega el =
            CONSTRUCTOR_LITERAL_VARS = 8,
            HOLE_EDGE = 9,
            HOLE_VAR = 10,
            HOLE_VAR_IN = 11,
            HOLE_VAR_OUT = 12
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
            TIP_EDGE_LOWERING = 31,
            TIP_VAR = 32,
            TIP_VAR_SUBARRAY = 33,//CLOCK y subindice en BUMLAY
            TIP_VAR_CLOCK = 34,
            TIP_VAR_CONV_ARRAY_TO_INT = 35,
            TIP_VAR_CONV_INT_TO_ARRAY = 36
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
            SUMMON_TIP_LOWERING = 46,
            SUMMON_VAR = 47,
            SUMMON_VAR_SUBARRAY = 48,
            SUMMON_VAR_CLOCK = 49,
            SUMMON_VAR_CONV_ARRAY_TO_INT = 50,
            SUMMON_VAR_CONV_INT_TO_ARRAY = 51
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
            EVENT_DOCK = 16,
            EVENT_CREATE_SPARTAN = 17,
            EVENT_TURN_ON = 18,
            EVENT_TURN_OFF = 19,
            EVENT_SIMULATION_STEP = 20,
            EVENT_GO_HELP = 21,
            EVENT_SCROLL = 22,
            EVENT_DELETE = 23,
            EVENT_TOGGLE = 24,
            EVENT_EXPORT = 25,
            EVENT_IMPORT = 26,
            EVENT_RELOAD = 27
            ;
    public static final int
            IMPLEMENTER_LED = 1,
            IMPLEMENTER_SWITCH = 2,
            IMPLEMENTER_BUTTON = 3,
            IMPLEMENTER_BCD = 4,
            IMPLEMENTER_CLOCK = 5
            ;
    public static final int
            SIM_POWER_ON = 1,
            SIM_POWER_OFF = 2,
            SIM_BUTTON_ON = 3,
            SIM_BUTTON_OFF = 4,
            SIM_LED_ON = 5,
            SIM_LED_OFF = 6
            ;
    
    
    public static final int
            ERROR_BOKEY = 0,
            ERROR_CANNOT_INICIALIZE_INOUT = 1,
            ERROR_CONST_MUST_BE_INICIALIZED = 2,
            ERROR_OUT_VAR_CANNOT_BE_READEN = 3,
            ERROR_CONST_VAR_CANNOT_BE_ASIGNED = 4,
            ERROR_CONVERSION_NOT_ALLOWED = 5,
            ERROR_IN_VAR_CANNOT_BE_ASIGNED = 6,
            ERROR_SETIF_IS_USELESS = 7,
            ERROR_SETSWITCH_IS_USELESS = 8,
            ERROR_ELSE_VALUE_NEEDED = 9,
            ERROR_SWITCH_NEEDS_DEFAULT_CASE = 10,
            ERROR_VARIABLE_DOES_NOT_EXIST = 11,
            ERROR_ILEGAL_USE_OF_OPERATOR_ADD = 12,
            ERROR_ILEGAL_USE_OF_OPERATOR_SUB = 13,
            ERROR_ILEGAL_USE_OF_OPERATOR_TIM = 14,
            ERROR_ILEGAL_USE_OF_OPERATOR_TAK = 15,
            ERROR_ILEGAL_USE_OF_OPERATOR_CONCATENATE = 16,
            ERROR_BAD_INICIALIZATION = 17,
            ERROR_CANT_CHANGE_LENGTH_OF_BITARRAY = 18,
            ERROR_PROYECT_IS_EMPTY = 19,
            ERROR_PROBLEM_LOADING_PROYECT = 20,
            ERROR_INCOMPATIBLE_TYPES= 21,
            ERROR_BAD_EXPRESSION = 22,
            ERROR_WAIT_NOT_ALLOWED = 23,
            ERROR_BAD_CONDITION = 24
            ;
    public static final String[] ERROR_NAME = new String[]{"BOKEY", "Input And Output variables\ncan not be initialized.",
    "Constants must\nbe initialized.","Output var\ncannot be read.","Constants cannot\nbe assigned.","Illegal conversion.",
    "Input vars cannot\nbe assigned.","SetIf structure\nis empty.","SetSwitch structure\nis empty.","Set Switch requires\nelse instruction.",
    "Switch needs\ndefault case.","Variable does\nnot exist.","Illegal use of addition operator.","Illegal use of subtraction operator.",
    "Illegal use of\nproduct operator.","Illegal use of\ndivision operator.","Illegal use of\nconcatenate operator.",
    "Illegal inicialization.","Length of bit array\ncannot be modified.","Cant compile an\nempty proyect",
    "Unespected problem\nloading proyect.", "Incompatible\ntypes", "Illegal\nexpression", "Wait statement\nnot allowed",
    "Illegal\ncondition"};
    
    public static float distance(int x1, int y1, int x2, int y2){
        return (float) Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    
    
    public static void preload(){
        RESOLUTION_LIST[0] = new Resolution(1024,640);
        RESOLUTION_LIST[1] = new Resolution(1280,720);
        RESOLUTION_LIST[2] = new Resolution(1024,768);
        RESOLUTION_LIST[3] = new Resolution(1366,768);
        RESOLUTION_LIST[4] = new Resolution(1280,800);
        RESOLUTION_LIST[5] = new Resolution(1280,960);
        RESOLUTION_LIST[6] = new Resolution(1400,1050);
        RESOLUTION_LIST[7] = new Resolution(1680,1050);
        RESOLUTION_LIST[8] = new Resolution(1440,1080);
        RESOLUTION_LIST[9] = new Resolution(1920,1080);
        RESOLUTION_LIST[10] = new Resolution(1920,1440);
        RESOLUTION_LIST[11] = new Resolution(2340,1440);
        RESOLUTION_LIST[12] = new Resolution(2560,1440);
        RESOLUTION_LIST[13] = new Resolution(3840,2160);
        RESOLUTION_LIST[14] = new Resolution(7680,4320);
        
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
        
        COLOR_VAR_IN = new float[]                  {116/255f,179/255f,179/255f};//Modificar
        COLOR_VAR_OUT = new float[]                 {255/255f,179/255f,179/255f};
        COLOR_VAR_SIGNAL = new float[]              {116/255f,255/255f,179/255f};
        COLOR_VAR_CONST = new float[]               {116/255f,179/255f,255/255f};
        //Implement
        COLOR_IMPLEMENTER = new float[]             {183/255f,150/255f,114/255f};
        //Simulate
        COLOR_CIRCUIT_GREEN = new float[]           { 70/255f,242/255f, 90/255f};
        COLOR_CIRCUIT_DKGREEN = new float[]         {  6/255f,107/255f,  3/255f};
        COLOR_CIRCUIT_BROWN = new float[]           {205/255f,160/255f, 90/255f};
        COLOR_CIRCUIT_DKBROWN = new float[]         {160/255f,113/255f, 40/255f};
        COLOR_CIRCUIT_DKGRAY = new float[]          { 57/255f, 80/255f, 80/255f};
        COLOR_CIRCUIT_DKLIGHT = new float[]         {156/255f,156/255f, 20/255f};
        COLOR_CIRCUIT_LIGHT = new float[]           {240/255f,240/255f, 30/255f};
        COLOR_CIRCUIT_LCD_OFF = new float[]         { 17/255f, 24/255f,102/255f};
        COLOR_CIRCUIT_LCD_ON = new float[]          {  4/255f, 95/255f,186/255f};
        COLOR_CIRCUIT_LCD_LETTER = new float[]      {153/255f,227/255f,255/255f};
    }
    public static void load(){//Mete el logo y ponselo a MenuGUI y el resto
        
        TEXTURE_ARROW_1 = new Texture(RESOURCES+"img/arrow1.png");
        TEXTURE_ARROW_2 = new Texture(RESOURCES+"img/arrow2.png");
        TEXTURE_FINDERSLIDER = new Texture(RESOURCES+"img/finderSlider.png");
        TEXTURE_HOUSE = new Texture(RESOURCES+"img/house.png");
        TEXTURE_SAVE = new Texture(RESOURCES+"img/save.png");
        TEXTURE_CLOSE_CROSS = new Texture(RESOURCES+"img/cross.png");
        TEXTURE_SETTINGS = new Texture(RESOURCES+"img/Setings.png");
        TEXTURE_TRASHCAN = new Texture(RESOURCES+"img/trashcan.png");
        TEXTURE_IMPORT = new Texture(RESOURCES+"img/import.png");
        TEXTURE_EXPORT = new Texture(RESOURCES+"img/export.png");
        //FONT_TEST = new Font("src/res/fonts/test.png",32,32,16,16);
        FONT_MONOFONTO = new Font(RESOURCES+"fonts/monofonto.png",64,64,48,64+5);//Sumarle +5 al ultimo 64
        FONT_LCD = new Font(RESOURCES+"fonts/lcd.png",64,64,42,64+8);
        TEXTURE_BCD = new Texture[11];
        TEXTURE_BORELICIOUS = new Texture(RESOURCES+"img/borelicious.png");
        for(int i = 0; i < 11; i++)
            TEXTURE_BCD[i] = new Texture(RESOURCES+"img/bcd"+i+".jpg");
        
        if(CONF.get(CONF.THEME) == 1){
            //LIGHT THEME
            TEXTURE_LOGO = new Texture(RESOURCES+"img/Logo_Light.png");
            //Colores de GUI
            COLOR_GUI_1 = new float[]                   { 37/255f,175/255f,244/255f}; //FONDO: Azul Scratch
            COLOR_GUI_2 = new float[]                   {253/255f,184/255f, 37/255f}; //BORDE: Narangito Scratch
            COLOR_GUI_3 = new float[]                   {  0/255f,  0/255f,  0/255f}; //NEGRO
            COLOR_GUI_4 = new float[]                   { 96/255f,125/255f,139/255f}; //DETALLES: Azul oscuro
            COLOR_GUI_5 = new float[]                   {255/255f,255/255f,255/255f}; //BLANCO
            COLOR_GUI_6 = new float[]                   {139/255f,195/255f, 74/255f}; //FONDO: Verde salton apagado
            COLOR_OK = new float[]                      { 37/255f,155/255f, 36/255f};
            COLOR_NOOK = new float[]                    {253/255f,151/255f, 31/255f};
            
            //Problemas de contraste
            COLOR_ASIGNATOR_SEQ = new float[]           {250/255f,126/255f, 44/255f};
            COLOR_IFTHEN = new float[]                  { 58/255f, 73/255f,165/255f};
        }else if(CONF.get(CONF.THEME) == 2){
            //DARK THEME
            TEXTURE_LOGO = new Texture(RESOURCES+"img/Logo_Dark.png");
            //Colores de GUI
            COLOR_GUI_1 = new float[]                   { 97/255f, 97/255f, 97/255f}; //FONDO: El gris claroscuro
            COLOR_GUI_2 = new float[]                   {126/255f, 87/255f,194/255f}; //BORDE: MORADO FUXIA VIOLETA
            COLOR_GUI_3 = new float[]                   {  0/255f,  0/255f,  0/255f}; //NEGRO
            COLOR_GUI_4 = new float[]                   {233/255f, 30/255f, 99/255f}; //DETALLES: Rosita moradito
            COLOR_GUI_5 = new float[]                   {255/255f,255/255f,255/255f}; //BLANCO
            COLOR_GUI_6 = new float[]                   {116/255f,179/255f,179/255f}; //FONDO: Azul poco brillante
            COLOR_OK = new float[]                      { 37/255f,155/255f, 36/255f};
            COLOR_NOOK = new float[]                    {253/255f,151/255f, 31/255f};
        }
        
        WaitCourtain.load(RESOURCES+"img/loading/", 25, COLOR_GUI_1);
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
    public static void addGUIframe(Element e){
        e.addShape(new Shape_Square(0, 0, Global.COLOR_GUI_2, 1, 4, Global.WINDOW_WIDTH, 70), 0, 0);
        e.addImage(new Image(0, 0, 3, Global.TEXTURE_HOUSE, 1, Global.COLOR_GUI_3), Global.WINDOW_WIDTH-80, 0);
        e.addImage(new Image(0, 0, 3, Global.TEXTURE_SAVE, 1, Global.COLOR_GUI_3), Global.WINDOW_WIDTH-160, 0);
        e.addShape(new Shape_BorderedBox(0, 0, Global.COLOR_GUI_4, Global.COLOR_GUI_5, 1, 3, 200, 52, 5), 36, 10);
        e.addShape(new Shape_BorderedBox(0, 0, Global.COLOR_GUI_4, Global.COLOR_GUI_5, 1, 3, 200, 52, 5), 246, 10);
        e.addShape(new Shape_BorderedBox(0, 0, Global.COLOR_GUI_4, Global.COLOR_GUI_5, 1, 3, 200, 52, 5), 456, 10);
        e.addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "DESIGN", true), 136, 36);
        e.addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "IMPLEMENT", true), 346, 36);
        e.addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "SIMULATE", true), 556, 36);
        e.getLabel(0).setScretch(1.8f);
        e.getLabel(1).setScretch(1.8f);
        e.getLabel(2).setScretch(1.8f);
        
        e.addBoundingBox(Global.WINDOW_WIDTH-80, Global.WINDOW_WIDTH-10, 0, 70, Global.EVENT_GO_HOMO);
        e.addBoundingBox(Global.WINDOW_WIDTH-160, Global.WINDOW_WIDTH-90, 0, 70, Global.EVENT_SAVE);
        e.addBoundingBox(36, 236, 10, 62, Global.EVENT_GO_DESIGN);
        e.addBoundingBox(246, 446, 10, 62, Global.EVENT_GO_IMPLEMENT);
        e.addBoundingBox(456, 656, 10, 62, Global.EVENT_GO_SIMULATE);
    }
    public static void addGUImenu(Element e){
        int paddingBorder = (int) (WINDOW_HEIGHT*0.05);
        e.addShape(new Shape_Square(0, 0, COLOR_GUI_2, 1, 5, WINDOW_WIDTH, WINDOW_HEIGHT), 0, 0);
        e.addShape(new Shape_Square(0, 0, COLOR_GUI_1, 1, 5, WINDOW_WIDTH -2*paddingBorder, WINDOW_HEIGHT -2*paddingBorder), paddingBorder, paddingBorder);
           
        int widthLogo = (int) (WINDOW_WIDTH*0.9);
        e.addImage(new Image(0, 0, 4, TEXTURE_LOGO, widthLogo/1920f, COLOR_WHITE), (WINDOW_WIDTH-widthLogo)/2, (int) (WINDOW_HEIGHT*0.12));
    }
    
    public static void sayLinesOfCode(){
        final String folderPath = "src";
        try{
            long totalLineCount = 0;
            final List<File> folderList = new LinkedList<>();
            folderList.add(new File(folderPath));
            while (!folderList.isEmpty()) {
                final File folder = folderList.remove(0);
                if (folder.isDirectory() && folder.exists()) {
                    final File[] fileList = folder.listFiles();
                    for (final File file : fileList) {
                        if (file.isDirectory()) {
                            folderList.add(file);
                        } else if (file.getName().endsWith(".java")
                                || file.getName().endsWith(".sql")) {
                            long lineCount = 0;
                            final Scanner scanner = new Scanner(file);
                            while (scanner.hasNextLine()) {
                                scanner.nextLine();
                                lineCount++;
                            }
                            totalLineCount += lineCount;
                        }
                    }
                }
            }
            System.out.println("Lines of code: " + totalLineCount);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String concatenate(String list[]){
        return Global.concatenate(list, "");
    }
    public static String concatenate(ArrayList<String> list){
        return concatenate(list, "");
    }
    public static String concatenate(String list[],String divider){
        String text = "";
        
        for(String s:list)
            text += (text.isEmpty()?"":divider) + s;
        
        return text;
    }
    public static String concatenate(ArrayList<String> list,String divider){
        String[] data = new String[list.size()];
        for(int i = 0; i < list.size(); i++)
            data[i] = list.get(i);
        return Global.concatenate(data, divider);
    }
    
    public static String concatenate(int list[],String divider){
        String text = "";
        
        for(int s:list)
            text += (text.isEmpty()?"":divider) + s;
        
        return text;
    }
    
    
    public static void createProyect(String name){
        SAVE_NAME = name.replace(" ", "_");
        File proyectFolder = new File(System.getenv("APPDATA")+"/HardScratch/"+SAVE_NAME);
        if(proyectFolder.exists())
            deleteFolder(proyectFolder);
        proyectFolder.mkdirs();
        
        //Resto de cosas
    }
    public static void deleteProyect(String name){
        File proyectFolder = new File(System.getenv("APPDATA")+"/HardScratch/"+name.replaceAll(" ", "_"));
        if(proyectFolder.exists())
            deleteFolder(proyectFolder);
        if(SAVE_NAME != null && SAVE_NAME.endsWith(name))
            SAVE_NAME = "";
    }
    public static void deleteFolder(File folder) {///?Se nota que no lo he escrito yo?
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
    public static void openProyect(String name){
        SAVE_NAME = name.replace(" ", "_");
        File proyectFolder = new File(System.getenv("APPDATA")+"/HardScratch/"+SAVE_NAME);
        if(!proyectFolder.exists())
            createProyect(name);
    }
    
    public static String getProyectFolder(){
        return System.getenv("APPDATA")+"/HardScratch/"+SAVE_NAME;
    }
    
    public static float[] colorGlow(float[] color){
        if(color.length != 3) return null;
        return new float[]{color[0]*1.5f,color[1]*1.2f,color[2]*1.2f};
    }
    public static float[] colorDeGlow(float[] color){
        if(color.length != 3) return null;
        return new float[]{color[0]/1.5f,color[1]/1.2f,color[2]/1.2f};
    }
    
    public static void planedRestart(){
        try {
            Runtime.getRuntime().exec(Global.LAUNCHER_EXE);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
