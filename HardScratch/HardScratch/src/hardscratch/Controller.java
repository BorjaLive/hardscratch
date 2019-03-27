package hardscratch;

import hardscratch.elements.piezes.Hole;
import hardscratch.elements.piezes.Tip;
import hardscratch.elements.piezes.GUIs.*;
import hardscratch.elements.piezes.*;
import hardscratch.backend.*;
import hardscratch.base.*;
import hardscratch.elements.piezes.Design.Declarator;
import hardscratch.elements.piezes.Implementation.Implementer;
import hardscratch.elements.piezes.Simulation.*;
import hardscratch.inputs.*;
import static hardscratch.Global.*;
import java.util.ArrayList;
import java.util.Collections;
import static org.lwjgl.glfw.GLFW.*;

public class Controller {
    
    private static long window;
    private static ArrayList<Element> elements;
    private static int focus, Rfocus, lastFocus, currentRoom;
    private static boolean focus_volatile, Rfocus_volatile;
    private static long idCounter = 0;
    private static int deleteAll;
    
    private static int errorI, errorState;
    private static String openOnNext;
    private static int openOnNextCounter;
    private static int buckleStage, buckleLine, buckleMax;
    
    private static int finder;
    private static ArrayList<Summoner>
            BasicElements,  //Iniciadores y partes principales
            InOutElements,  //In, Out, Const, Signal para variables
            VarTypeElements,    //Integer, bit, bit array
            cLiteralElements,   //Dafts tipo I
            cLiteralVarElements,    //Variables y literales
            cVarElements,
            cOperatorsElements,     //Linkers tipo a
            cOperatorsPlusElements, //Linker tipo e
            cVarOutsElements,
            cVarInsElements,
            EdgeType
            ;  
    private static ArrayList<Summoner> selectedFinder;
    
    //AutoMuvement
    private static ElementBase movingElement;
    private static int[][] movingPos;
    private static int movingCounter;
    private static int slidingFinder, finderPos, slidingCurrent;
    private static int[] slidingPos;
    private static boolean easterTrue;
    
    private static int layerOnly = -1;
    
    //Variables y logia
    private static ArrayList<Variable> vars;
    
    public static void initer(long window){
        elements = new ArrayList<>();
        vars = new ArrayList<>();
        Controller.window = window;
        focus = -1; Rfocus = -1; lastFocus= -1;
        deleteAll = 0;
        
        load();
        Mouse.Init(window);
        glfwSetKeyCallback(window, new Keyboard());
        
        //Movement
        movingElement = null;
        
        
        //Bucle load
        buckleStage = -1;
        buckleLine = -1;
        easterTrue = false;
        
        //Calcular posiciones del finder
        slidingPos = new int[30];
        for(int i = 0; i < 30; i++)
            slidingPos[i] = (int) (400*functionSM((float)i/30));
        slidingPos[29] = 400;
        
        //Create summonersPack
        finderInit();
        errorTerminate(); //Por inicializar las cosas un poco
        
        changeRoom(ROOM_MENU);
        
        
        glfwShowWindow(window);
    }
    
    private static void finderInit(){
        BasicElements = new ArrayList<>();
        finderADD(BasicElements, 1f, SUMMON_DECLARATRON);
        finderADD(BasicElements, 1f, SUMMON_INICIALIZER);
        finderADD(BasicElements, 1f, SUMMON_EXTRAVAR);
        finderADD(BasicElements, 1f, SUMMON_CONVERTER);
        finderADD(BasicElements, 1f, SUMMON_ASIGNATOR);
        finderADD(BasicElements, 1f, SUMMON_SETIF);
        finderADD(BasicElements, 1f, SUMMON_SETSWITCH);
        finderADD(BasicElements, 1f, SUMMON_SEQUENTIAL);
        finderADD(BasicElements, 1f, SUMMON_ASIGNATOR_SEQ);
        finderADD(BasicElements, 1f, SUMMON_IFTHEN);
        finderADD(BasicElements, 1f, SUMMON_SWITCHCASE);
        finderADD(BasicElements, 1f, SUMMON_WAITFOR);
        finderADD(BasicElements, 1f, SUMMON_WAITON);
        finderADD(BasicElements, 1f, SUMMON_FORNEXT);
        
        InOutElements = new ArrayList<>();
        finderADD(InOutElements, 1f, SUMMON_TIP_IN);
        finderADD(InOutElements, 1f, SUMMON_TIP_OUT);
        finderADD(InOutElements, 1f, SUMMON_TIP_SIGNAL);
        finderADD(InOutElements, 1f, SUMMON_TIP_CONST);
        
        VarTypeElements = new ArrayList<>();
        finderADD(VarTypeElements, 1f, SUMMON_TIP_INT);
        finderADD(VarTypeElements, 1f, SUMMON_TIP_BIT);
        finderADD(VarTypeElements, 1f, SUMMON_TIP_ARRAY);
        
        cLiteralElements = new ArrayList<>();
        finderADD(cLiteralElements, 1f, SUMMON_CONSTRUCTOR_OPEN);
        finderADD(cLiteralElements, 1f, SUMMON_CONSTRUCTOR_CLOSE);
        finderADD(cLiteralElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_NOT);
        finderADD(cLiteralElements, 1f, SUMMON_CONSTRUCTOR_LITERAL_N);
        finderADD(cLiteralElements, 1f, SUMMON_CONSTRUCTOR_LITERAL_B);
        finderADD(cLiteralElements, 1f, SUMMON_CONSTRUCTOR_LITERAL_A);
        
        cLiteralVarElements = new ArrayList<>();
        finderADD(cLiteralVarElements, cLiteralElements);
        //Depende de las variables
        
        cVarElements = new ArrayList<>();
        
        cOperatorsElements = new ArrayList<>();
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_OPEN);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_CLOSE);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_ARITH_ADD);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_ARITH_SUB);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_ARITH_TIM);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_ARITH_TAK);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_CONCAT);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_AND);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_OR);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_XOR);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_NAND);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_NOR);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_XNOR);
        finderADD(cOperatorsElements, 1f, SUMMON_CONSTRUCTOR_LOGIC_NOT);
        
        cOperatorsPlusElements = new ArrayList<>();
        finderADD(cOperatorsPlusElements, 1f, SUMMON_CONSTRUCTOR_EQUALITY);
        finderADD(cOperatorsPlusElements, cOperatorsElements);
        
        EdgeType = new ArrayList<>();
        finderADD(EdgeType, 1f, SUMMON_TIP_RISSING);
        finderADD(EdgeType, 1f, SUMMON_TIP_LOWERING);
        
        cVarOutsElements = new ArrayList<>();
        cVarInsElements = new ArrayList<>();
        
        selectedFinder = new ArrayList<>();
    }
    
    private static void finderADD(ArrayList<Summoner> list, float scale, int type){
        int height = 95;
        for(Summoner s:list)
            height += s.getHeight()+15;
        list.add(new Summoner(0, height, 3, scale, type));
        list.get(list.size()-1).move((400-list.get(list.size()-1).getWidth())/2, 0);
    }
    private static void finderADD(ArrayList<Summoner> list, ArrayList<Summoner> news){
        for(Summoner s:news)
            finderADD(list, s.getScale(), s.getItem());
    }
    private static void finderADD(ArrayList<Summoner> list, float scale, Variable var){
        finderADD(list, scale, SUMMON_VAR);
        list.get(list.size()-1).setVar(var);
        if(var.type == TIP_VAR_ARRAY){
            finderADD(list, scale, SUMMON_VAR_SUBARRAY);
            list.get(list.size()-1).setVar(var);
        }else if(var.type == TIP_VAR_BIT && (var.name.equals("CK") ||
                                                    var.name.equals("CLK") ||
                                                    var.name.equals("CLOCK"))){
            finderADD(list, scale, SUMMON_VAR_CLOCK);
            list.get(list.size()-1).setVar(var);
        }
    }
    private static void finderADD(ArrayList<Summoner> list, float scale, ArrayList<Variable> vars){
        for(Variable var:vars)
            finderADD(list,scale,var);
    }
    
    public static void loop(){
        Mouse.read();
        
        
        
        //Limpiar el tablero
        if(currentRoom == ROOM_DESIGN){
            if(Keyboard.getDown(GLFW_KEY_DELETE)){
                deleteAll++;
                if(deleteAll == 60){
                    if(finderPos == slidingPos[0]){ 
                        for(Summoner s:selectedFinder)
                            s.move(slidingPos[slidingPos.length-1]-finderPos, 0);
                        finderPos = slidingPos[slidingPos.length-1];
                    }
                    elements.clear();
                    elements.add(new DesignGUI());
                }
            }else if(deleteAll > 0)
                deleteAll = 0;
        }
        //Rutina open Next
        if(openOnNext != null){
            WaitCourtain.drawFrame();
            if(openOnNextCounter == 5){
                openProyect(openOnNext);
                changeRoom(ROOM_DESIGN);
                openOnNext = null;
                openOnNextCounter = 0;
            }else
                openOnNextCounter++;
        }
        
        //Rutina de BUCKLE load
        if(buckleStage != 0){
            //System.out.println("LOADING "+buckleStage+"  "+buckleLine);
            
            try {
                BUCKLE.partLoadDesign(buckleStage, buckleLine);
            } catch (Exception e) {
                System.err.println("BUCKLE ERROR IN STAGE "+buckleStage+"  "+buckleLine);
                e.printStackTrace();
                
                buckleStage = 0;
                elements.clear();
                elements.add(new DesignGUI());
                setError(ERROR_PROBLEM_LOADING_PROYECT, -1);
                return;
            }
            
            
            if(buckleStage == 3 || buckleStage == 5){
                buckleStage++;
            }else{
                buckleLine++;
                if(buckleLine == buckleMax){
                    buckleLine = 0;
                    buckleStage++;
                }
            }
            if(buckleStage == 6){
                buckleStage = 0;
                ElementsHolesCorrectTipPos();
                //System.out.println("BUCLE FINISH");
            }
            
            WaitCourtain.drawFrame();
            return;
        }
        
        //EASTER
        if(currentRoom == ROOM_MENU && Keyboard.getClick(GLFW_KEY_F2))
            changeRoom(ROOM_EASTER);
        if(easterTrue) elements.get(0).action(EVENT_TURN_ON);
        
        //Click en summoners
        if(Mouse.getX() < finderPos && Mouse.getY() > 70 )
            for(Summoner summoner : selectedFinder){
                if(Mouse.getLeftClick() && summoner.colide(Mouse.getX(),Mouse.getY())){
                    elements.add(summoner.summon(Mouse.getX(),Mouse.getY()));
                    Rfocus = focus;
                    Rfocus_volatile = focus_volatile;
                    focus = elements.size()-1;
                    focus_volatile = true;
                    elements.get(focus).focus_force_drag();
                    SoundPlayer.play(SOUND_SWITCH_UP);
                }
            }
        
        //Scrool de elementos summoneables
        float scroll = Mouse.getScroll()*MOUSE_SCROLL_SEPED;
        if(!selectedFinder.isEmpty() && Math.abs(scroll) > 0.000001 && selectedFinder.get(0).getY() + scroll < LAYOUT_TOP+30 &&
            selectedFinder.get(selectedFinder.size()-1).getY() + scroll > WINDOW_HEIGHT-200)
            for(Summoner s: selectedFinder)
                s.move(0, (int) scroll);
        if(currentRoom == ROOM_OPEN)
            elements.get(0).updateEvent(EVENT_SCROLL, (int) scroll, 0, "");
        if(currentRoom == ROOM_SETTINGS && Mouse.getLeftRelease())
            elements.get(0).action(107);
        
        //Seleccionar elementos
        //for (int i = 0; i < elements.size(); i++) {
        if(!selectRoutine(0))
            for (int i = elements.size()-1; i >= 1; i--)
                selectRoutine(i);
                
        //Rutina de seleccionados
        if(focus != -1 && elements.size() > focus){
            if(Mouse.getLeftDown()){
                if(!elements.get(focus).loop(Mouse.getMoveX(), Mouse.getMoveY())){
                    elements.get(focus).focus_end();
                    resumeFocus();
                }
            }else if(focus_volatile){
                elements.get(focus).focus_end();
                resumeFocus();
            }else{
                elements.get(focus).loop(0, 0);
            }
        }
        
        //Rutina de dibujado
        elements.get(0).action(EVENT_DRAW_BACKGROUND);
        for(int dl = 6; dl >= 0; dl--)
            if(!DEBUG_MODE || layerOnly == -1 || layerOnly == dl)
            for(int i = 0; i < elements.size(); i++){
                if(dl == 2){
                    if(slidingFinder != -1){    //Esto falla cuando el proyecto es nuevo TODO: harreglarlo
                        elements.get(0).action(EVENT_DRAW_FLOD);
                        selectedFinder.forEach((summoner) -> {
                            summoner.draw();
                        });
                    }
                }
                if(elements.get(i).getDepth() == dl && elements.get(i).getDrawable())// && inScreen(elements.get(i))
                    elements.get(i).draw();
            }
        
        
        //Rutina de movimiento auto
        if(movingElement != null){
            movingElement.move( movingPos[movingCounter][0]-movingElement.getX(),
                                movingPos[movingCounter][1]-movingElement.getY());
            movingCounter++;
            if(movingCounter >= movingPos.length){
                movingElement = null;
                Mouse.setBlock(false);
            }
        }
        if(slidingFinder == 1 || slidingFinder == 2){
            int move = 0;
            if(slidingCurrent >= slidingPos.length || slidingCurrent < 0){
                slidingFinder = 0;
            }else{
                if(slidingFinder == 1){
                    move = slidingPos[slidingCurrent];
                    slidingCurrent--;
                }else if(slidingFinder == 2){
                    move = slidingPos[slidingCurrent];
                    slidingCurrent++;
                }
                elements.get(0).updateEvent(EVENT_FINDER_MOVE,move-finderPos,0,"");
                for(Summoner s:selectedFinder)
                    s.move(move-finderPos, 0);
                finderPos = move;
            }
        }
        
        
        //Debug toggle
        if(Keyboard.getClick(GLFW_KEY_P))
            DEBUG_MODE = true;
        else if(Keyboard.getClick(GLFW_KEY_O))
            DEBUG_MODE = false;
        if(DEBUG_MODE){
            if(Keyboard.getClick(GLFW_KEY_1 )) layerOnly = 0;
            if(Keyboard.getClick(GLFW_KEY_2 )) layerOnly = 1;
            if(Keyboard.getClick(GLFW_KEY_3 )) layerOnly = 2;
            if(Keyboard.getClick(GLFW_KEY_4 )) layerOnly = 3;
            if(Keyboard.getClick(GLFW_KEY_5 )) layerOnly = 4;
            if(Keyboard.getClick(GLFW_KEY_6 )) layerOnly = 5;
            if(Keyboard.getClick(GLFW_KEY_7 )) layerOnly = 6;
            if(Keyboard.getClick(GLFW_KEY_0 )) layerOnly = -1;
        }
        
        
        //Eliminador
        if(lastFocus >= elements.size())    //Un parque que no ayuda demasiado
            lastFocus = -1;
        if(focus_volatile && lastFocus != -1 && Keyboard.getDown(GLFW_KEY_DELETE) && elements.get(lastFocus).isRemovableByUser()){
            unLink(lastFocus);
            lastFocus = -1;
            resumeFocus();
        }
        
        //Arrastrar todo el tablero
        if(Mouse.getRightDown())
            dragBoard(Mouse.getMoveX(), Mouse.getMoveY());
        
        
        //Cosas de la simulacion
        if(currentRoom == ROOM_SIMULATE){
            SimulateGUI s = (SimulateGUI) elements.get(0);
            int todo = s.hasTodo();
            if(todo != 0)
                simulationChange();
        }
        
        //Actuar respecto al error
        if(errorI != -1){
            if(currentRoom != ROOM_DESIGN)
                errorTerminate();
            else{
                if(errorState == 40){
                    errorState = 0;
                    elements.get(errorI).effectGlow();
                }else if(errorState == 20)
                    elements.get(errorI).effectGlow();

                errorState++;
            }
        }
    }
    private static boolean selectRoutine(int i){
        boolean colide = false;
        
        Element element = elements.get(i);
        if(Mouse.getLeftClick() && (Mouse.getY() > 70 || i == 0)){//Esto es un apano muy malo
            colide = element.colide(Mouse.getX(), Mouse.getY());
            if((focus == -1 || !focus_volatile) && element.getDragable() && colide){
                if(focus != -1)
                    elements.get(focus).focus_end();
                focus = i;
                focus_volatile = elements.get(focus).focus_init();
            }else if(focus == i && !focus_volatile){
                elements.get(focus).focus_end();
                resumeFocus();
            }
        }
        //System.out.println("SELECTED: "+focus+" VOlATILE: "+focus_volatile);
        return colide;
    }
    private static void dragBoard(int moveX, int moveY) {
        for(Element e:elements)
            if(e.getDepth() != 1 && e.getDragable() && !e.connectedWith(PORT_FEMALE))
                e.move(moveX, moveY);
                
    }
    
    private static boolean inScreen(Element object){
        return !(object.getX() < -DRAWING_RADIUS_LIMIT || object.getX() > WINDOW_WIDTH+DRAWING_RADIUS_LIMIT ||
                object.getY() < -DRAWING_RADIUS_LIMIT || object.getY() > WINDOW_HEIGHT+DRAWING_RADIUS_LIMIT);
    }
    private static void unLink(int n){
        Element e = null;
        if(elements.size() > n)
            e = elements.get(n);
        
        if(e != null)
            e.delete();
        if(e != null)
            elements.remove(e);
    }
    
    public static void moveAuto(ElementBase e, int x2, int y2, int tics){
        Mouse.setBlock(true);
        movingElement = e;
        movingPos = new int[tics+1][];
        int x1 = e.getX(), y1 = e.getY();
        for(int i = 0; i <= tics; i++){
            movingPos[i] = new int[]{   x1+Math.round((x2-x1)*functionSM((float)i/tics)),
                                        y1+Math.round((y2-y1)*functionSM((float)i/tics))};
        }
        movingCounter = 0;
    }
    private static float functionSM(float x){
        return FUNCTION_SM_A + (FUNCTION_SM_B*x) + (FUNCTION_SM_C*x*x);
    }
    
    
    public static long generateID(){
        idCounter += 1;
        while(getElementByID(idCounter) != null) idCounter++;
        return idCounter;
    }
    public static int idSearch(int ID){
        for(int i = 0; i < elements.size(); i++){
            if(ID == elements.get(i).getID())
                return i;
        }
        return -1;
    }
    
    public static void resumeFocus(){
            lastFocus = focus;
        if(Rfocus != -1){
            focus = Rfocus;
            focus_volatile = Rfocus_volatile;
            Rfocus = -1;
        }else{
            if(focus_volatile && focus != -1 && elements.size()-1 >= focus){     //Mecanismo para hacer docking
                Port[] Ports1 = elements.get(focus).getPorts();
                for(Port p1: Ports1){
                    for(Element e:elements){
                        Port[] Ports2 = e.getPorts();
                        for(Port p2: Ports2){
                            docking(elements.get(focus), e, p1, p2, QUICK_MOVE);
                        }
                    }
                }
            }
            focus = -1;
        }
    }
    public static boolean docking(Element e1, Element e2, Port p1, Port p2, boolean auto){
        if(e1.getID() == e2.getID()) return false;
        if(!Port.couple(p1, p2)) return false;
        
        p1.dock(e2, p2);
        p2.dock(e1, p1);
        
        if(p1.getGender() == PORT_MALE)
            dockingAlign(e1,p1, auto);
        else
            dockingAlign(e2,p2, auto);
        
        //System.out.println("DOCKED: "+e1.getClass()+" with "+e2.getClass());
        return true;
    }
    public static void dockingAlign(Element e1, Port p1, boolean auto){
        if(auto)
            e1.move(p1.getConnectedPort().getX1()-p1.getX1(), p1.getConnectedPort().getY1()-p1.getY1());
        else
            moveAuto(e1, p1.getConnectedPort().getX1()-p1.getX1()+e1.getX(), p1.getConnectedPort().getY1()-p1.getY1()+e1.getY(), 30);
    }
    public static void autoDock(String id1, String id2, int port1, int port2){
        if(id1 == null || id1.isEmpty() || id1.equals("-1") ||
           id2 == null || id2.isEmpty() || id2.equals("-1")) return;
        
        Element e1 = getElementByID(Integer.parseInt(id1));
        Element e2 = getElementByID(Integer.parseInt(id2));
        
        if(e1 == null || e1.getPort(port1) == null || e1.getPort(port1).isOcupied() ||
           e2 == null || e2.getPort(port2) == null || e2.getPort(port2).isOcupied()) return;
        
        docking(e1, e2, e1.getPort(port1), e2.getPort(port2), true);
    }
    
    //Game mechanics
    public static void setFinder(int type){
        
        //System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
        if((currentRoom != ROOM_DESIGN && currentRoom != ROOM_IMPLEMENT) || type == -1){
            selectedFinder = new ArrayList<>();
        }
        if(elements.size() > 1 && currentRoom == ROOM_DESIGN)
            loadVars();
        finder = type;
        
        switch(finder){
            case 1:                                     selectedFinder = BasicElements; break;
            case HOLE_VAR_INOUT:                 selectedFinder = InOutElements; break;
            case HOLE_VAR_TYPE:                  selectedFinder = VarTypeElements; break;
            //case CONSTRUCTOR_LITERAL_CONST:      selectedFinder = cLiteralElements; break;
            case CONSTRUCTOR_LITERAL_VARS:       selectedFinder = cLiteralVarElements; break;
            case CONSTRUCTOR_OPERATORS:          selectedFinder = cOperatorsElements; break;
            case CONSTRUCTOR_OPERATORS_PLUS:     selectedFinder = cOperatorsPlusElements; break;
            case HOLE_EDGE:                      selectedFinder = EdgeType; break;
            case HOLE_VAR:                       selectedFinder = cVarElements; break;
            case HOLE_VAR_IN:                    selectedFinder = cVarInsElements; break;
            case HOLE_VAR_OUT:                   selectedFinder = cVarOutsElements; break;
        }
        
        for(Summoner s: selectedFinder)
            s.move(finderPos-375-s.getX(), 0);
        if(!(slidingFinder == 0 && finderPos == 400)){
            finderToggle();
        }
    }

    public static void deleteElement(long ID) {
        for(int i = 0; i < elements.size(); i++)
            if(elements.get(i).getID() == ID){
                if(i == lastFocus)
                    lastFocus = -1;
                unLink(i);
            }
    }
    
    public static long getClolideID(){
        long id;
        for(Element e:elements){
            id = e.colideID(Mouse.getX(), Mouse.getY());
            if(id != -1)
                return id;
        }
        return -1;
    }
    public static Hole searchHole(long id){
        if(id == -1) return null;
        Hole h;
        for(Element e:elements){
            h = e.getHoleByID(id);
            if(h != null)
                return h;
        }
        return null;
    }
    public static Element getElementByID(long id){
        for(Element e:elements)
            if(e.getID() == id)
                return e;
        return null;
    }
    public static Element getElementByID(String id){
        return getElementByID(Integer.parseInt(id));
    }
    
    public static void changeRoom(int room){
        SoundPlayer.play(SOUND_KEY_PRESS);
        
        BUCKLE.save();
        
        elements.clear();//Limpiarlo todo
        slidingFinder = -1;
        finderPos = 0;
        setFinder(-1);
        
        switch(room){
            case ROOM_MENU:
                elements.add(new MenuGUI());
            break;
            case ROOM_CREATE:
                elements.add(new CreateGUI());
            break;
            case ROOM_OPEN:
                elements.add(new OpenGUI());
            break;
            case ROOM_SETTINGS:
                elements.add(new SettingsGUI());
            break;
            case ROOM_DESIGN:
                elements.add(new DesignGUI());
                slidingFinder = 0;
                finderPos = 400;
                setFinder(1);
            break;
            case ROOM_IMPLEMENT:
                elements.add(new ImplementGUI());
                slidingFinder = 0;
                finderPos = 400;
                setFinder(HOLE_VAR);
                implementImplementElements();
            break;
            case ROOM_SIMULATE:
                slidingFinder = -1;
                elements.add(new SimulateGUI());
                elements.get(elements.size()-1).action(EVENT_CREATE_SPARTAN);
            break;
            case ROOM_EASTER:
                elements.add(new EasterGUI());
        }
        currentRoom = room;
        BUCKLE.load();
    }
    public static void finderToggle(){
        if(finderPos == 400){
            slidingFinder = 1;
            slidingCurrent = 29;
        }else if(finderPos == 0){
            slidingFinder = 2;
            slidingCurrent = 0;
        }
        if(!elements.isEmpty())
            elements.get(0).action(EVENT_TOOGLE_TOGGLE);
    }
    
    public static void putOnTop(long id){
        Element e = getElementByID(id);
        if(elements.indexOf(e) == focus)
            focus = elements.size()-1;
        while(elements.indexOf(e) != elements.size()-1){
            int i = elements.indexOf(e);
            if(i != -1)
                Collections.swap(elements, i, i+1);
        }
    }

    private static void implementImplementElements(){
        for(int i = 0; i < 8; i++)
            elements.add(new Implementer(500, 95+(125*i), IMPLEMENTER_LED, i+1));
        for(int i = 0; i < 4; i++)
            elements.add(new Implementer(965, 95+(125*i), IMPLEMENTER_SWITCH, i+1));
        for(int i = 0; i < 4; i++)
            elements.add(new Implementer(965, 95+(125*(i+4)), IMPLEMENTER_BUTTON, i+1));
        for(int i = 0; i < 2; i++)
            elements.add(new Implementer(1430, 95+(125*i), IMPLEMENTER_BCD, i+1));
        elements.add(new Implementer(1430, 95+(125*3), IMPLEMENTER_CLOCK, 1));
        
    }
    
    //HardWork
    public static void loadVars(){
        cLiteralVarElements.clear();
        cVarElements.clear();
        cVarOutsElements.clear();
        cVarInsElements.clear();
        for(Element e: elements){
            if(e.getClass() == Declarator.class){
                Declarator d = (Declarator) e;
                if(d.isComplete()){
                    ArrayList<Variable> tmp = d.getVars();
                    if(tmp != null){
                        vars.addAll(tmp);
                        finderADD(cVarElements, 1f, tmp);
                        finderADD(cLiteralVarElements, 1f, tmp);
                        //finderADD(cLiteralElements, 1f, tmp);
                        for(Variable v:tmp)
                            if(v.inout == TIP_VAR_OUT)
                                finderADD(cVarOutsElements, 1f, v);
                            else if(v.inout == TIP_VAR_IN)
                                finderADD(cVarInsElements, 1f, v);
                    }
                }
            }
        }
        finderADD(cLiteralVarElements, cLiteralElements);
        //finderADD(cLiteralVarElements, cVarElements);
    }
    public static Variable getVarByName(String name){
        for(Variable var:vars)
            if(var.name.equals(name)) return var;
        return null;
    }
    public static ArrayList<Element> getBoard(){
        if(elements.size() <= 1) return null;
        ArrayList<Element> piezes = new ArrayList<>();
        for(Element e:elements)
            if(e.isRemovableByUser() && e.isMother()) piezes.add(e);
        
        return piezes;
    }
    public static ArrayList<Element> getBoardAll(){
        if(elements.size() <= 1) return null;
        ArrayList<Element> piezes = new ArrayList<>();
        for(Element e:elements)
            if((e.isRemovableByUser() && e.getClass() != Tip.class) ||
                    e.getClass() == Implementer.class) piezes.add(e);
        
        return piezes;
    }
    
    public static void addToBoard(Element e){
        elements.add(e);
        e.setDepth(5);
    }
    public static Element getFromBoard(int n){
        if(n < elements.size())
            return elements.get(n);
        else
            return null;
    }
    public static Implementer getImplementerFromIdentifier(String indentifier){
        Implementer Im;
        for(Element e:elements)
            if(e.getClass() == Implementer.class){
                Im = (Implementer) e;
                if(Im.getIdentifier().equals(indentifier))
                    return Im;
            }
        return null;
    }
    public static ArrayList<Variable> getAllVars(){
        return vars;
    }
    
    public static int getRoom(){
        return currentRoom;
    }

    public static void simulationChange() {
        SimulateGUI gui = (SimulateGUI) elements.get(0);
        if(gui != null){
            String[][] list = new String[22][2];
            int i = 1;//recorrer elements
            int j = 0;//recorrer list
            while(i < elements.size() && j < list.length){
                Simulated s = (Simulated) elements.get(i);
                if(s != null){
                    list[j][0] = s.getName();
                    list[j][1] = s.getValue();
                    j++;
                }
                i++;
            }
            gui.simulationStep(list);
        }
    }
    public static void simulationSet(String[][] data){
        if(currentRoom != ROOM_SIMULATE) return;
        
        for(String[] dat:data){
            for(int i = 1; i < elements.size(); i++){
                Simulated s = (Simulated) elements.get(i);
                if(s != null && s.is(dat[0]) && !(s.getClass() == SimulateSwitch.class || s.getClass() == SimulatedButton.class || s.getClass() == SimulatedClock.class)){
                    s.setValue(dat[1]);
                    break;
                }
            }
        }
    }

    public static void setError(int errorCode, int id){
        //Ir a Design
        if(currentRoom != ROOM_DESIGN)
            changeRoom(ROOM_DESIGN);
        
        //Buscar que no exista ya un notifier
        for(int i = 0; i < elements.size(); i++){
            if(elements.get(i).getClass() == Notifier.class){
                elements.remove(i);
                break;
            }
        }
        
        //Crearlo
        addToBoard(new Notifier(errorCode));
        
        //Mover el tablero para centrar el error
        Element e = getElementByID(id);
        if(e != null)
            dragBoard(450-e.getX(), 250-e.getY());
        errorI = elements.indexOf(e);
        errorState = 0;
        
    }
    public static void errorTerminate(){
        if(errorI >= 0 && errorI < elements.size()) elements.get(errorI).effectClear();
        errorI = -1;
        errorState = 0;
    }
    
    public static void ElementsHolesCorrectTipPos(){
        for(Element e:elements)
                e.HolesCorrectTipPos();
    }

    public static void setOpenOnNext(String proyect) {
        openOnNext = proyect;
        openOnNextCounter = 0;
    }

    public static void initBUCKLEload(int length) {
        buckleMax = length;
        buckleLine = 0;
        buckleStage = 1;
    }

    public static void setEasterCheck(boolean b) {
        easterTrue = b;
    }
}
