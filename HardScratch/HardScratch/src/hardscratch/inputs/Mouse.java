package hardscratch.inputs;

import hardscratch.Global;
import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Mouse {
    
    private static long window;
    
    private static final DoubleBuffer buffer_mouse_x = BufferUtils.createDoubleBuffer(1);
    private static final DoubleBuffer buffer_mouse_y = BufferUtils.createDoubleBuffer(1);
    private static int mouse_x, mouse_y, mouse_x_last, mouse_y_last;
    private static float scrollSpeed;
    private static boolean block; //Para que no se tengan en cuenta las pulsaciones
    
    private static boolean left_click, left_down, left_release, right_click, right_down, right_release;
     
    public static void Init(long window){
        Mouse.window = window;
        block = false;
        
        left_click = false;
        left_down = false;
        left_release = false;
        right_click = false;
        right_down = false;
        right_release = false;
        
        glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override public void invoke (long win, double dx, double dy) {
                scrollSpeed = (float) dy;
            }
        });
    }
    
    public static int getX(){
        return mouse_x;
    }
    public static int getY(){
        return mouse_y;
    }
    public static int getMoveX(){
        return mouse_x - mouse_x_last;
    }
    public static int getMoveY(){
        return mouse_y - mouse_y_last;
    }
    
    public static float getScroll(){
        float s = scrollSpeed;
        scrollSpeed*=0.9;
        if(Math.abs(scrollSpeed) < 0.00001)
            scrollSpeed = 0;
        return s;
    }
    
    public static void setBlock(boolean b){
        block = b;
    }
    
    public static boolean getLeftClick(){   return block?false:left_click;}
    public static boolean getLeftDown(){    return block?false:left_down;}
    public static boolean getLeftRelease(){ return block?false:left_release;}
    public static boolean getRightClick(){  return block?false:right_click;}
    public static boolean getRightDown(){   return block?false:right_down;}
    public static boolean getRightRelease(){return block?false:right_release;}
    
    public static void read(){
        glfwGetCursorPos(window, buffer_mouse_x, buffer_mouse_y);
        mouse_x_last = mouse_x;
        mouse_y_last = mouse_y;
        mouse_x = (int) buffer_mouse_x.get(0);
        mouse_y = (int) buffer_mouse_y.get(0);
        mouse_x = mouse_x<0?0:(mouse_x>Global.WINDOW_WIDTH?Global.WINDOW_WIDTH:mouse_x);
        mouse_y = mouse_y<0?0:(mouse_y>Global.WINDOW_HEIGHT?Global.WINDOW_HEIGHT:mouse_y);
        
        if(glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_1) == 1){
            if(left_down)
                left_click = false;
            else
                left_click = true;
            left_down = true;
            left_release = false;
        }else{
            if(left_down)
                left_release = true;
            else
                left_release = false;
            left_down = false;
            left_click = false;
        }
        if(glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_2) == 1){
            if(right_down)
                right_click = false;
            else
                right_click = true;
            right_down = true;
            right_release = false;
        }else{
            if(right_down)
                right_release = true;
            else
                right_release = false;
            right_down = false;
            right_click = false;
        }
    }
}
