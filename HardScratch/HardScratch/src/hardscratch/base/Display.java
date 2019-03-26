package hardscratch.base;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.backend.CONF;
import hardscratch.base.shapes.Texture;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Display {
    public long window;
    
    public Display(){
    }
    
    public void init(){
        //Recolectar informacion sobre el sistema
        if(!glfwInit()) {
            throw new IllegalStateException("Failed to initalize GLFW!");
        }
        
        CONF.load();
        preload();
        
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        FULLSCREEN = CONF.get(CONF.WINDOW)==2;
        if(FULLSCREEN){
            WINDOW_WIDTH  = videoMode.width();
            WINDOW_HEIGHT = videoMode.height();
        }else{
            WINDOW_WIDTH  = RESOLUTION_LIST[CONF.get(CONF.RESOLUTION)].width;
            WINDOW_HEIGHT = RESOLUTION_LIST[CONF.get(CONF.RESOLUTION)].height;
        }
        
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "HardScratch", FULLSCREEN?glfwGetPrimaryMonitor():0, 0);
        
        if(window == 0) {
            throw new IllegalStateException("Failed to initalize Window!");
        }
        
        glfwSetWindowIcon(window, Texture.lwjglIsStupid(TASKBAR_ICON));

        glfwMakeContextCurrent(window);
        glfwSetWindowPos(window, (videoMode.width() - WINDOW_WIDTH) / 2, (videoMode.height() - WINDOW_HEIGHT) / 2);
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        //Recolectar informacion del sistema
        String glvendor = GL11.glGetString(GL11.GL_VENDOR);
        String glrenderer = GL11.glGetString(GL11.GL_RENDERER);
        String glversion = GL11.glGetString(GL11.GL_VERSION);
        String gldriver = null;
        int i = glversion.indexOf(' ');
        if (i != -1) {
            gldriver = glversion.substring(i + 1);
            glversion = glversion.substring(0, i);
        }
        System.out.println("GRAPHICS ADAPTER INFORMATION:\n"
                + "Vendor: "+glvendor+"\n"
                + "Render: "+glrenderer+"\n"
                + "Version: "+glversion+"\n"
                + "Driver: "+gldriver);
        
        SyncTimer.initer(FRAME_RATE);
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
