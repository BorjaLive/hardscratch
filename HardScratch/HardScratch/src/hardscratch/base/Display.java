package hardscratch.base;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.shapes.Texture;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Display {
    public long window;
    
    public Display(){
    }
    
    public void init(){
        
        if(!glfwInit()) {
            throw new IllegalStateException("Failed to initalize GLFW!");
        }
        
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        window = glfwCreateWindow(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, "HardScratch", Global.FULLSCREEN?glfwGetPrimaryMonitor():0, 0);
        
        if(window == 0) {
            throw new IllegalStateException("Failed to initalize Window!");
        }
        
        glfwSetWindowIcon(window, Texture.lwjglIsStupid(Global.TASKBAR_ICON));

        glfwMakeContextCurrent(window);
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - Global.WINDOW_WIDTH) / 2, (videoMode.height() - Global.WINDOW_HEIGHT) / 2);
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        SyncTimer.initer(Global.FRAME_RATE);
        Controller.initer(window);
    }
    
    public boolean isRunning() {
        return(!glfwWindowShouldClose(this.window));
    }
    
    public void update() {
        Controller.loop();
        glfwSwapBuffers(window); 
        glfwPollEvents();
        try{
            SyncTimer.sync(); 
        }catch(Exception e){
            System.err.println("SYNC ERROR: \n"+e);
        }
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void terminate() {
        glfwTerminate();
    }
}
