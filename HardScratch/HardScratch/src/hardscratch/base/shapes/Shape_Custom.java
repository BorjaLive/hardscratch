package hardscratch.base.shapes;

import hardscratch.Global;
import hardscratch.base.Dot;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

public class Shape_Custom extends Shape{
    
    private float width, height;
    private float[][] shape;
    
    public Shape_Custom(int x, int y, float[] color, float scale, int depth, int width, int height, float[][] shape) {
        super(x, y, color, scale, depth);
        
        this.width = Dot.relativeSizeX(width);
        this.height = Dot.relativeSizeY(height);
        this.shape = shape;
    }
    
     @Override
    public void draw(){
        GL11.glColor3f(color[0]/255f, color[1]/255f, color[2]/255f);
        glBegin(GL_POLYGON);
        for (float[] vertex : shape)
            glVertex2f(position.getRelX() + (vertex[0] * width * scale),
                       position.getRelY() + (vertex[1] * height * scale));
        glEnd();
        Global.unBind();
    }
    
}
