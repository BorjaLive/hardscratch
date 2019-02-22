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
            InOutElements  //In, Out, Const, Signal para variables
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
        BasicElements.add(new Summoner(25, 95, 3, 0.5f, Global.SUMMON_DECLARATRON));
        
        InOutElements = new ArrayList<>();
        InOutElements.add(new Summoner(25, 95, 3, 1f, Global.SUMMON_TIP_IN));
        InOutElements.add(new Summoner(25, 150, 3, 1f, Global.SUMMON_TIP_OUT));
        InOutElements.add(new Summoner(25, 205, 3, 1f, Global.SUMMON_TIP_SIGNAL));
        InOutElements.add(new Summoner(25, 260, 3, 1f, Global.SUMMON_TIP_CONST));
        
        setFinder(1);
        //Elementos
        //elements.add(new Declarator(0,0));
        //elements.add(new TestElement(0, 0, 0, true, true));
        
        glfwShowWindow(window); 
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
    }
    
    
    private static boolean inScreen(Element object){
        if(object.getX() < -Global.DRAWING_RADIUS_LIMIT || object.getX() > Global.WINDOW_WIDTH+Global.DRAWING_RADIUS_LIMIT ||
           object.getY() < -Global.DRAWING_RADIUS_LIMIT || object.getY() > Global.WINDOW_HEIGHT+Global.DRAWING_RADIUS_LIMIT)
            return false;
        else
            return true;
    }
    private static void unLink(int n){
        elements.get(n).delete();
        elements.remove(n);
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
            focus = -1;
        }
    }
    
    //Game mechanics
    public static void setFinder(int type){
        finder = type;
        switch(finder){
            case 1: selectedFinder = BasicElements; break;
            case Global.HOLE_VAR_INOUT: selectedFinder = InOutElements; break;
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
}
