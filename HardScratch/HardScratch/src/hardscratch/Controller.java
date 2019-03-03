package hardscratch;

import hardscratch.base.*;
import hardscratch.elements.*;
import hardscratch.elements.subParts.*;
import hardscratch.inputs.*;
import java.util.ArrayList;
import java.util.Collections;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Controller {
    
    private static long window;
    private static ArrayList<Element> elements;
    private static int focus, Rfocus, lastFocus;
    private static boolean focus_volatile, Rfocus_volatile;
    private static int idCounter = 0;
    
    private static int finder;
    private static ArrayList<Summoner>
            BasicElements,  //Iniciadores y partes principales
            InOutElements,  //In, Out, Const, Signal para variables
            VarTypeElements,    //Integer, bit, bit array
            cLiteralConstElements,   //Dafts tipo I
            cLiteralVarElements,    //Variables
            cOperatorsElements,     //Linkers tipo a
            cOperatorsPlusElements, //Linker tipo e
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
    
    
    public static void initer(long window){
        elements = new ArrayList<>();
        Controller.window = window;
        focus = -1; Rfocus = -1; lastFocus= -1;
        
        Global.load();
        Mouse.Init(window);
        glfwSetKeyCallback(window, keyCallback = new Keyboard());
        
        //Movement
        movingElement = null;
        
        //Create summonersPack
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
        
        cLiteralConstElements = new ArrayList<>();
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_OPEN);
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_CLOSE);
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_LITERAL_N);
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_LITERAL_B);
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_LITERAL_A);
        //cLiteralConstElements.add(new Summoner(25, 260, 3, 1f, Global.SUMMON_CONSTRUCTOR_VAR_C)); //Esto depende de las variables
        
        cLiteralVarElements = new ArrayList<>();
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_OPEN);
        finderADD(cLiteralConstElements, 1f, Global.SUMMON_CONSTRUCTOR_CLOSE);
        cLiteralVarElements.addAll(cLiteralConstElements);
        //Depende de las variables
        
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
        cOperatorsPlusElements.addAll(cOperatorsElements);
        finderADD(cOperatorsPlusElements, 1f, Global.SUMMON_CONSTRUCTOR_EQUALITY);
        
        EdgeType = new ArrayList<>();
        finderADD(EdgeType, 1f, Global.SUMMON_TIP_RISSING);
        finderADD(EdgeType, 1f, Global.SUMMON_TIP_LOWERING);
        
        selectedFinder = new ArrayList<>();
        
        
        changeRoom(Global.ROOM_DESIGN);
        
        glfwShowWindow(window); 
    }
    
    private static void finderADD(ArrayList<Summoner> list, float scale, int type){
        int height = 95;
        for(Summoner s:list)
            height += s.getHeight()+15;
        list.add(new Summoner(0, height, 3, scale, type));
        list.get(list.size()-1).move((400-list.get(list.size()-1).getWidth())/2, 0);
    }
    
    public static void loop(){
        Mouse.read();
        
        
        
        //Click en summoners
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
        for (int i = elements.size()-1; i >= 0; i--) {
            Element element = elements.get(i);
            if(Mouse.getLeftClick()){
                if((focus == -1 || !focus_volatile) && element.getDragable() && element.colide(Mouse.getX(), Mouse.getY())){
                    if(focus != -1)
                        elements.get(focus).focus_end();
                    focus = i;
                    focus_volatile = elements.get(focus).focus_init();
                }else if(focus == i && !focus_volatile){
                    elements.get(focus).focus_end();
                    resumeFocus();
                }
            }
        }
        System.out.println(focus);
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
                    elements.get(0).action(Global.EVENT_DRAW_FLOD);
                    selectedFinder.forEach((summoner) -> {
                        summoner.draw();
                    });
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
    
    
    private static boolean inScreen(Element object){
        return !(object.getX() < -Global.DRAWING_RADIUS_LIMIT || object.getX() > Global.WINDOW_WIDTH+Global.DRAWING_RADIUS_LIMIT ||
                object.getY() < -Global.DRAWING_RADIUS_LIMIT || object.getY() > Global.WINDOW_HEIGHT+Global.DRAWING_RADIUS_LIMIT);
    }
    private static void unLink(int n){
        if(elements.size() > n){
            elements.get(n).delete();
            elements.remove(n);
        }
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
    
    
    public static int generateID(){
        idCounter += 1;
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
                            docking(elements.get(focus), e, p1, p2);
                        }
                    }
                }
            }
            focus = -1;
        }
    }
    public static boolean docking(Element e1, Element e2, Port p1, Port p2){
        if(e1.getID() == e2.getID()) return false;
        if(!Port.couple(p1, p2)) return false;
        
        p1.dock(e2, p2);
        p2.dock(e1, p1);
        
        if(p1.getGender() == Global.PORT_MALE)
            dockingAlign(e1,p1);
        else
            dockingAlign(e2,p2);
        
        return true;
    }
    public static void dockingAlign(Element e1, Port p1){
        moveAuto(e1, p1.getConnectedPort().getX1()-p1.getX1()+e1.getX(), p1.getConnectedPort().getY1()-p1.getY1()+e1.getY(), 30);
    }
    
    //Game mechanics
    public static void setFinder(int type){
        finder = type;
        switch(finder){
            case 1: selectedFinder = BasicElements; break;
            case Global.HOLE_VAR_INOUT: selectedFinder = InOutElements; break;
            case Global.HOLE_VAR_TYPE: selectedFinder = VarTypeElements; break;
            case Global.CONSTRUCTOR_LITERAL_CONST: selectedFinder = cLiteralConstElements; break;
            case Global.CONSTRUCTOR_LITERAL_VARS: selectedFinder = cLiteralVarElements; break;
            case Global.CONSTRUCTOR_OPERATORS: selectedFinder = cOperatorsElements; break;
            case Global.CONSTRUCTOR_OPERATORS_PLUS: selectedFinder = cOperatorsPlusElements; break;
            case Global.HOLE_EDGE: selectedFinder = EdgeType; break;
        }
        for(Summoner s: selectedFinder)
            s.move(finderPos-375-s.getX(), 0);
        if(!(slidingFinder == 0 && finderPos == 400)){
            finderToggle();
        }
    }

    public static void deleteElement(int ID) {
        for(int i = 0; i < elements.size(); i++)
            if(elements.get(i).getID() == ID){
                if(i == lastFocus)
                    lastFocus = -1;
                unLink(i);
            }
    }
    
    public static int getClolideID(){
        int id;
        for(Element e:elements){
            id = e.colideID(Mouse.getX(), Mouse.getY());
            if(id != -1)
                return id;
        }
        return -1;
    }
    public static Hole searchHole(int id){
        if(id == -1) return null;
        Hole h;
        for(Element e:elements){
            h = e.getHoleByID(id);
            if(h != null)
                return h;
        }
        return null;
    }
    public static Element getElementByID(int id){
        for(Element e:elements)
            if(e.getID() == id)
                return e;
        return null;
    }
    
    public static void changeRoom(int room){
        elements.clear();
        switch(room){
            case Global.ROOM_MENU: break;
            case Global.ROOM_CREATE: break;
            case Global.ROOM_OPEN: break;
            case Global.ROOM_SETTINGS: break;
            case Global.ROOM_DESIGN:
                elements.add(new DesignGUI());
                slidingFinder = 0;
                finderPos = 400;
                setFinder(1);
                break;
            case Global.ROOM_IMPLEMENT: break;
            case Global.ROOM_SIMULATE: break;
        }
    }
    public static void finderToggle(){
        if(finderPos == 400){
            slidingFinder = 1;
        }else if(finderPos == 0){
            slidingFinder = 2;
        }
        elements.get(0).action(Global.EVENT_TOOGLE_TOGGLE);
    }
    
    public static void putOnTop(int id){
        Element e = getElementByID(id);
        if(elements.indexOf(e) == focus)
            focus = elements.size()-1;
        while(elements.indexOf(e) != elements.size()-1){
            int i = elements.indexOf(e);
            Collections.swap(elements, i, i+1);
        }
    }
}
