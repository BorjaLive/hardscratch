package hardscratch.inputs;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

public class Mouse {
    
    private static long window;
    
    private static final DoubleBuffer buffer_mouse_x = BufferUtils.createDoubleBuffer(1);
    private static final DoubleBuffer buffer_mouse_y = BufferUtils.createDoubleBuffer(1);
    private static int mouse_x, mouse_y, mouse_x_last, mouse_y_last;
    
    private static boolean left_click, left_down, left_release, right_click, right_down, right_release;
     
    public static void Init(long window){
        Mouse.window = window;
        left_click = false;
        left_down = false;
        left_release = false;
        right_click = false;
        right_down = false;
        right_release = false;
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
    
    public static boolean getLeftClick(){return left_click;}
    public static boolean getLeftDown(){return left_down;}
    public static boolean getLeftRelease(){return left_release;}
    public static boolean getRightClick(){return right_click;}
    public static boolean getRightDown(){return right_down;}
    public static boolean getRightRelease(){return right_release;}
    
    public static void read(){
        glfwGetCursorPos(window, buffer_mouse_x, buffer_mouse_y);
        mouse_x_last = mouse_x;
        mouse_y_last = mouse_y;
        mouse_x = (int) buffer_mouse_x.get(0);
        mouse_y = (int) buffer_mouse_y.get(0);
        
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
