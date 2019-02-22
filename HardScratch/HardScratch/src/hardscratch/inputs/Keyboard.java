package hardscratch.inputs;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class Keyboard extends GLFWKeyCallback{
    
    public static boolean[] keys_down = new boolean[65536];
    public static boolean[] keys_click = new boolean[65536];
    
    private static final int[] abc_keys = new int[]{GLFW_KEY_A,GLFW_KEY_B,GLFW_KEY_C,GLFW_KEY_D,GLFW_KEY_E,GLFW_KEY_F,GLFW_KEY_G,GLFW_KEY_H,GLFW_KEY_I,GLFW_KEY_J,
    GLFW_KEY_K,GLFW_KEY_L,GLFW_KEY_M,GLFW_KEY_N,GLFW_KEY_O,GLFW_KEY_P,GLFW_KEY_Q,GLFW_KEY_R,GLFW_KEY_S,GLFW_KEY_T,GLFW_KEY_U,GLFW_KEY_V,GLFW_KEY_W,GLFW_KEY_X,GLFW_KEY_Y,GLFW_KEY_Z};
    private static final int[] num_keys = new int[]{GLFW_KEY_0,GLFW_KEY_1,GLFW_KEY_2,GLFW_KEY_3,GLFW_KEY_4,GLFW_KEY_5,GLFW_KEY_6,GLFW_KEY_7,GLFW_KEY_8,GLFW_KEY_9};
    
    public Keyboard(){
    }
    
     @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys_click[key] = action == GLFW_PRESS;
        keys_down[key] = action != GLFW_RELEASE;
    }
    
    public static boolean getClick(int keycode) {//Delete es GLFW_KEY_DELETE
        boolean ret = keys_click[keycode];
        keys_click[keycode] = false;
        return ret;
    }
    public static boolean getDown(int keycode) {
        return keys_down[keycode];
    }
    
    public static char getInput(){
        int base = (keys_down[GLFW_KEY_CAPS_LOCK] || keys_down[GLFW_KEY_LEFT_SHIFT])?65:97;
        
        for(int i = 0; i < abc_keys.length; i++){
            if(keys_click[abc_keys[i]]){
                keys_click[abc_keys[i]] = false;
                return (char) (base+i);
            }
        }
        for(int i = 0; i < num_keys.length; i++){
            if(keys_click[num_keys[i]]){
                keys_click[num_keys[i]] = false;
                return (char) (48+i);
            }
        }
        
        if(keys_click[GLFW_KEY_SPACE]){
            keys_click[GLFW_KEY_SPACE] = false;
            return ' ';
        }else if(keys_click[GLFW_KEY_ENTER]){
            keys_click[GLFW_KEY_ENTER] = false;
            return '|';
        }else if(keys_click[GLFW_KEY_BACKSPACE]){
            keys_click[GLFW_KEY_BACKSPACE] = false;
            return '~';
        }else if(keys_click[GLFW_KEY_DELETE]){
            keys_click[GLFW_KEY_BACKSPACE] = false;
            return '£';
        }
        
        return 0;
    }
}
