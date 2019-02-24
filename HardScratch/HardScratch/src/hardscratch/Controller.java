package hardscratch;

import hardscratch.base.*;
import hardscratch.elements.*;
import hardscratch.elements.subParts.*;
import hardscratch.inputs.*;
import java.util.ArrayList;
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
            cLiteralConstElements,   //Dafts tipo I
            cLiteralVarElements,
            cOperatorsElements,     //Linkers tipo a
            cOperatorsPlusElements  //Linker tipo e
            ;  
    private static ArrayList<Summoner> selectedFinder;
    
    //AutoMuvement
    private static ElementBase movingElement;
    private static int[][] movingPos;
    private static int movingCounter;
    
    private static GLFWKeyCallback keyCallback;
    
    
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
        finderADD(BasicElements, 0.5f, Global.SUMMON_DECLARATRON);
        finderADD(BasicElements, 0.5f, Global.SUMMON_INICIALIZER);
        finderADD(BasicElements, 0.5f, Global.SUMMON_EXTRAVAR);
        
        InOutElements = new ArrayList<>();
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_IN);
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_OUT);
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_SIGNAL);
        finderADD(InOutElements, 1f, Global.SUMMON_TIP_CONST);
        
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
        
        setFinder(1);
        //Elementos
        //elements.add(new Declarator(0,0));
        //elements.add(new TestElement(0, 0, 0, true, true));
        
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
        if( Math.abs(scroll) > 0.000001 && selectedFinder.get(0).getY() + scroll < Global.LAYOUT_TOP+30 &&
            selectedFinder.get(selectedFinder.size()-1).getY() + scroll > Global.WINDOW_HEIGHT-200)
            for(Summoner s: selectedFinder)
                s.move(0, (int) scroll);
        
        //Seleccionar elementos
        for (int i = 0; i < elements.size(); i++) {
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
        selectedFinder.forEach((summoner) -> {
            summoner.draw();
        });
        for(int dl = 6; dl >= 0; dl--)
            for(int i = elements.size()-1; i >= 0; i--) //Al reves para que el que agarres sea el primero
                if(elements.get(i).getDepth() == dl && elements.get(i).getDrawable() && inScreen(elements.get(i)))
                    elements.get(i).draw();
        
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
        
        
        //Debug toggle
        if(Keyboard.getClick(GLFW_KEY_P))
            Global.DEBUG_MODE = true;
        else if(Keyboard.getClick(GLFW_KEY_O))
            Global.DEBUG_MODE = false;
        
        
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
                if(e.getDragable())
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
            case Global.CONSTRUCTOR_LITERAL_CONST: selectedFinder = cLiteralConstElements; break;
            case Global.CONSTRUCTOR_LITERAL_VARS: selectedFinder = cLiteralVarElements; break;
            case Global.CONSTRUCTOR_OPERATORS: selectedFinder = cOperatorsElements; break;
            case Global.CONSTRUCTOR_OPERATORS_PLUS: selectedFinder = cOperatorsPlusElements; break;
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
}
