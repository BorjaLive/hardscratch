package hardscratch;

import hardscratch.elements.piezes.Hole;
import hardscratch.elements.piezes.Tip;
import hardscratch.elements.piezes.GUIs.*;
import hardscratch.elements.piezes.*;
import hardscratch.backend.*;
import hardscratch.base.*;
import hardscratch.elements.piezes.Design.Declarator;
import hardscratch.elements.piezes.Implementation.Implementer;
import hardscratch.inputs.*;
import java.util.ArrayList;
import java.util.Collections;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Controller {
    
    private static long window;
    private static ArrayList<Element> elements;
    private static int focus, Rfocus, lastFocus, currentRoom;
    private static boolean focus_volatile, Rfocus_volatile;
    private static long idCounter = 0;
    
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
    private static int slidingFinder, finderPos;
    
    private static GLFWKeyCallback keyCallback;
    private static int layerOnly = -1;
    
    //Variables y logia
    private static ArrayList<Variable> vars;
    
    
    public static void initer(long window){
        elements = new ArrayList<>();
        vars = new ArrayList<>();
        Controller.window = window;
        focus = -1; Rfocus = -1; lastFocus= -1;
        
        Global.load();
        Mouse.Init(window);
        glfwSetKeyCallback(window, keyCallback = new Keyboard());
        
        //Movement
        movingElement = null;
        
        //Create summonersPack
        finderInit();
        
        
        Global.openProyect("test");
        changeRoom(Global.ROOM_DESIGN);
        
        
        
        glfwShowWindow(window); 
    }
    
    private static void finderInit(){
        BasicElements = new ArrayList<>();
        finderADD(BasicElements, 1f, Global.SUMMON_DECLARATRON);
        finderADD(BasicElements, 1f, Global.SUMMON_INICIALIZER);
        finderADD(BasicElements, 1f, Global.SUMMON_EXTRAVAR);
        finderADD(BasicElements, 1f, Global.SUMMON_CONVERTER);
        finderADD(BasicElements, 1f, Global.SUMMON_ASIGNATOR);
        finderADD(BasicElements, 1f, Global.SUMMON_SETIF);
        finderADD(BasicElements, 1f, Global.SUMMON_SETSWITCH);
        finderADD(BasicElements, 1f, Global.SUMMON_SEQUENTIAL);
        finderADD(BasicElements, 1f, Global.SUMMON_ASIGNATOR_SEQ);
        finderADD(BasicElements, 1f, Global.SUMMON_IFTHEN);
        finderADD(BasicElements, 1f, Global.SUMMON_SWITCHCASE);
        finderADD(BasicElements, 1f, Global.SUMMON_WAITFOR);
        finderADD(BasicElements, 1f, Global.SUMMON_WAITON);
        finderADD(BasicElements, 1f, Global.SUMMON_FORNEXT);
        
        InOutElements = new ArrayList<>();
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_IN);
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_OUT);
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_SIGNAL);
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_CONST);
        
        VarTypeElements = new ArrayList<>();
        finderADD(VarTypeElements, 1f, Global.SUMMON_TIP_INT);
        finderADD(VarTypeElements, 1f, Global.SUMMON_TIP_BIT);
        finderADD(VarTypeElements, 1f, Global.SUMMON_TIP_ARRAY);
        
        cLiteralElements = new ArrayList<>();
        finderADD(cLiteralElements, 1f, Global.SUMMON_CONSTRUCTOR_OPEN);
        finderADD(cLiteralElements, 1f, Global.SUMMON_CONSTRUCTOR_CLOSE);
        finderADD(cLiteralElements, 1f, Global.SUMMON_CONSTRUCTOR_LITERAL_N);
        finderADD(cLiteralElements, 1f, Global.SUMMON_CONSTRUCTOR_LITERAL_B);
        finderADD(cLiteralElements, 1f, Global.SUMMON_CONSTRUCTOR_LITERAL_A);
        
        cLiteralVarElements = new ArrayList<>();
        finderADD(cLiteralVarElements, cLiteralElements);
        //Depende de las variables
        
        cVarElements = new ArrayList<>();
        
        cOperatorsElements = new ArrayList<>();
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_OPEN);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_CLOSE);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_ARITH_ADD);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_ARITH_SUB);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_ARITH_TIM);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_ARITH_TAK);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_CONCAT);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_AND);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_OR);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_XOR);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_NAND);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_NOR);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_XNOR);
        finderADD(cOperatorsElements, 1f, Global.SUMMON_CONSTRUCTOR_LOGIC_NOT);
        
        cOperatorsPlusElements = new ArrayList<>();
        finderADD(cOperatorsPlusElements, 1f, Global.SUMMON_CONSTRUCTOR_EQUALITY);
        finderADD(cOperatorsPlusElements, cOperatorsElements);
        
        EdgeType = new ArrayList<>();
        finderADD(EdgeType, 1f, Global.SUMMON_TIP_RISSING);
        finderADD(EdgeType, 1f, Global.SUMMON_TIP_LOWERING);
        
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
        finderADD(list, scale, Global.SUMMON_VAR);
        list.get(list.size()-1).setVar(var);
        if(var.type == Global.TIP_VAR_ARRAY){
            finderADD(list, scale, Global.SUMMON_VAR_SUBARRAY);
            list.get(list.size()-1).setVar(var);
        }else if(var.type == Global.TIP_VAR_BIT){
            finderADD(list, scale, Global.SUMMON_VAR_CLOCK);
            list.get(list.size()-1).setVar(var);
        }
    }
    private static void finderADD(ArrayList<Summoner> list, float scale, ArrayList<Variable> vars){
        for(Variable var:vars)
            finderADD(list,scale,var);
    }
    
    public static void loop(){
        Mouse.read();
        
        
        
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
                }
            }
        
        //Scrool de elementos summoneables
        float scroll = Mouse.getScroll()*Global.MOUSE_SCROLL_SEPED;
        if(!selectedFinder.isEmpty() && Math.abs(scroll) > 0.000001 && selectedFinder.get(0).getY() + scroll < Global.LAYOUT_TOP+30 &&
            selectedFinder.get(selectedFinder.size()-1).getY() + scroll > Global.WINDOW_HEIGHT-200)
            for(Summoner s: selectedFinder)
                s.move(0, (int) scroll);
        
        //Seleccionar elementos
        //for (int i = 0; i < elements.size(); i++) {
        if(!selectRoutine(0))
            for (int i = elements.size()-1; i >= 1; i--)
                selectRoutine(i);
                
        //Rutina de seleccionados
        if(focus != -1){
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
        elements.get(0).action(Global.EVENT_DRAW_BACKGROUND);
        for(int dl = 6; dl >= 0; dl--)
            if(!Global.DEBUG_MODE || layerOnly == -1 || layerOnly == dl)
            for(int i = 0; i < elements.size(); i++){
                if(dl == 2){
                    if(slidingFinder != -1){
                        elements.get(0).action(Global.EVENT_DRAW_FLOD);
                        selectedFinder.forEach((summoner) -> {
                            summoner.draw();
                        });
                    }
                }
                if(elements.get(i).getDepth() == dl && elements.get(i).getDrawable() && inScreen(elements.get(i)))
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
            int move;
            if(slidingFinder == 1 && finderPos <= 0){
                move = 0 - finderPos;
                slidingFinder = 0;
            }else if(slidingFinder == 2 && finderPos >= 400){
                move = 400 - finderPos;
                slidingFinder = 0;
            }else{
                move = Math.round(Global.FUNCTION_QR_A + (Global.FUNCTION_QR_B*finderPos) + (Global.FUNCTION_QR_C*finderPos*finderPos));
                move *= (slidingFinder==1?-1:1);
            }
            //System.out.println(Global.FUNCTION_QR_A + (Global.FUNCTION_QR_B*finderPos) + (Global.FUNCTION_QR_C*finderPos*finderPos));
            finderPos += move;
            elements.get(0).updateEvent(Global.EVENT_FINDER_MOVE,move,0,"");
            for(Summoner s:selectedFinder)
                s.move(move, 0);
        }
        
        
        //Debug toggle
        if(Keyboard.getClick(GLFW_KEY_P))
            Global.DEBUG_MODE = true;
        else if(Keyboard.getClick(GLFW_KEY_O))
            Global.DEBUG_MODE = false;
        if(Global.DEBUG_MODE){
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
        if(Mouse.getRightDown()){
            for(Element e:elements)
                if(e.getDepth() != 1 && e.getDragable() && !e.connectedWith(Global.PORT_FEMALE))
                    e.move(Mouse.getMoveX(), Mouse.getMoveY());
        }
    }
    private static boolean selectRoutine(int i){
        boolean doneSomething = false;
        
        Element element = elements.get(i);
        if(Mouse.getLeftClick()){
            if((focus == -1 || !focus_volatile) && element.getDragable() && element.colide(Mouse.getX(), Mouse.getY())){
                if(focus != -1)
                    elements.get(focus).focus_end();
                focus = i;
                focus_volatile = elements.get(focus).focus_init();
                
                doneSomething = true;
            }else if(focus == i && !focus_volatile){
                elements.get(focus).focus_end();
                resumeFocus();
            }
        }
        //System.out.println("SELECTED: "+focus+" VOlATILE: "+focus_volatile);
        return doneSomething;
    }
    
    
    private static boolean inScreen(Element object){
        return !(object.getX() < -Global.DRAWING_RADIUS_LIMIT || object.getX() > Global.WINDOW_WIDTH+Global.DRAWING_RADIUS_LIMIT ||
                object.getY() < -Global.DRAWING_RADIUS_LIMIT || object.getY() > Global.WINDOW_HEIGHT+Global.DRAWING_RADIUS_LIMIT);
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
            movingPos[i] = new int[]{   x1+Math.round((x2-x1)*((float)i/tics)),
                                        y1+Math.round((y2-y1)*((float)i/tics))};
        }
        movingCounter = 0;
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
                            docking(elements.get(focus), e, p1, p2, Global.QUICK_MOVE);
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
        
        if(p1.getGender() == Global.PORT_MALE)
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
        if((currentRoom != Global.ROOM_DESIGN && currentRoom != Global.ROOM_IMPLEMENT) || type == -1){
            selectedFinder = new ArrayList<>();
        }
        if(elements.size() > 1 && currentRoom == Global.ROOM_DESIGN)
            loadVars();
        finder = type;
        
        switch(finder){
            case 1:                                     selectedFinder = BasicElements; break;
            case Global.HOLE_VAR_INOUT:                 selectedFinder = InOutElements; break;
            case Global.HOLE_VAR_TYPE:                  selectedFinder = VarTypeElements; break;
            //case Global.CONSTRUCTOR_LITERAL_CONST:      selectedFinder = cLiteralElements; break;
            case Global.CONSTRUCTOR_LITERAL_VARS:       selectedFinder = cLiteralVarElements; break;
            case Global.CONSTRUCTOR_OPERATORS:          selectedFinder = cOperatorsElements; break;
            case Global.CONSTRUCTOR_OPERATORS_PLUS:     selectedFinder = cOperatorsPlusElements; break;
            case Global.HOLE_EDGE:                      selectedFinder = EdgeType; break;
            case Global.HOLE_VAR:                       selectedFinder = cVarElements; break;
            case Global.HOLE_VAR_IN:                    selectedFinder = cVarInsElements; break;
            case Global.HOLE_VAR_OUT:                   selectedFinder = cVarOutsElements; break;
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
        BUCKLE.save();
        switch(room){
            case Global.ROOM_MENU: break;
            case Global.ROOM_CREATE: break;
            case Global.ROOM_OPEN: break;
            case Global.ROOM_SETTINGS: break;
            case Global.ROOM_DESIGN:
                elements.clear();
                elements.add(new DesignGUI());
                slidingFinder = 0;
                finderPos = 400;
                setFinder(1);
            break;
            case Global.ROOM_IMPLEMENT:
                elements.clear();
                elements.add(new ImplementGUI());
                slidingFinder = 0;
                finderPos = 400;
                setFinder(Global.HOLE_VAR);
                implementImplementElements();
            break;
            case Global.ROOM_SIMULATE:
                elements.clear();
                slidingFinder = -1;
                elements.add(new SimulateGUI());
                elements.get(elements.size()-1).action(Global.EVENT_CREATE_SPARTAN);
                finderPos = 0;
                setFinder(-1);
            break;
        }
        currentRoom = room;
        BUCKLE.load();
    }
    public static void finderToggle(){
        if(finderPos == 400){
            slidingFinder = 1;
        }else if(finderPos == 0){
            slidingFinder = 2;
        }
        elements.get(0).action(Global.EVENT_TOOGLE_TOGGLE);
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
            elements.add(new Implementer(500, 95+(125*i), Global.IMPLEMENTER_LED, i+1));
        for(int i = 0; i < 4; i++)
            elements.add(new Implementer(965, 95+(125*i), Global.IMPLEMENTER_SWITCH, i+1));
        for(int i = 0; i < 4; i++)
            elements.add(new Implementer(965, 95+(125*(i+4)), Global.IMPLEMENTER_BUTTON, i+1));
        for(int i = 0; i < 2; i++)
            elements.add(new Implementer(1430, 95+(125*i), Global.IMPLEMENTER_BCD, i+1));
        elements.add(new Implementer(1430, 95+(125*3), Global.IMPLEMENTER_CLOCK, 1));
        
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
                        finderADD(cLiteralElements, 1f, tmp);
                        for(Variable v:tmp)
                            if(v.inout == Global.TIP_VAR_OUT)
                                finderADD(cVarOutsElements, 1f, v);
                            else if(v.inout == Global.TIP_VAR_IN)
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
}
