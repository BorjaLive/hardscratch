package hardscratch;

import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.*;
import hardscratch.inputs.*;
import java.util.ArrayList;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Controller {
    
    private final long window;
    private ArrayList<Element> elements;
    private int focus;
    private boolean focus_volatile;
    
    private GLFWKeyCallback keyCallback;
    
    
    public Controller(long window){
        elements = new ArrayList<>();
        this.window = window;
        focus = -1;
        
        Global.load();
        
        elements.add(new TestElement(0,0,0,true,true));
        
        Mouse.Init(window);
        glfwSetKeyCallback(window, keyCallback = new Keyboard());
        
        glfwShowWindow(window); 
    }
    
    
    public void loop(){
        Mouse.read();
        
        
        //Seleccionar elementos
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if(element.getDrawable() && inScreen(element)){
                element.draw();
                if(Mouse.getLeftClick()){
                    if((focus == -1 || !focus_volatile) && element.getDragable() && element.colide(Mouse.getX(), Mouse.getY())){
                        if(focus != -1)
                            elements.get(focus).focus_end();
                        focus = i;
                        focus_volatile = elements.get(focus).focus_init();
                    }else if(focus != -1 && !focus_volatile){
                        elements.get(focus).focus_end();
                        focus = -1;
                    }
                }
            }
        }
        
        //Rutina de seleccionados
        if(focus != -1){
            if(Mouse.getLeftDown()){
                if(!elements.get(focus).loop(Mouse.getMoveX(), Mouse.getMoveY())){
                    elements.get(focus).focus_end();
                    focus = -1;
                }
            }else if(focus_volatile){
                elements.get(focus).focus_end();
                focus = -1;
            }else{
                elements.get(focus).loop(0, 0);
            }
        }
        
        
        //Debug toggle
        if(Keyboard.getClick(GLFW_KEY_P))
            Global.DEBUG_MODE = true;
        else if(Keyboard.getClick(GLFW_KEY_O))
            Global.DEBUG_MODE = false;
    }
    
    
    private boolean inScreen(Element object){
        if(object.getX() < -Global.DRAWING_RADIUS_LIMIT || object.getX() > Global.WINDOW_WIDTH+Global.DRAWING_RADIUS_LIMIT ||
           object.getY() < -Global.DRAWING_RADIUS_LIMIT || object.getY() > Global.WINDOW_HEIGHT+Global.DRAWING_RADIUS_LIMIT)
            return false;
        else
            return true;
    }
}
