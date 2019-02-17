package hardscratch.base.shapes;

import hardscratch.Global;
import hardscratch.base.Dot;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

public class Shape_Square extends Shape{
    
    float width, height;
    
    public Shape_Square(int x, int y, int[] color, float scale, int depth, int width, int height) {
        super(x, y, color, scale, depth);
        
        this.width = Dot.relativeSizeX(width);
        this.height = Dot.relativeSizeY(height);
    }
    
    @Override
    public void draw(){
        GL11.glColor3f(color[0], color[1], color[2]);
        glBegin(GL_QUADS);
        for (float[] SHAPE_SQUARE : Global.SHAPE_SQUARE)
            glVertex2f(position.getRelX() + (SHAPE_SQUARE[0] * width * scale),
                       position.getRelY() + (SHAPE_SQUARE[1] * height * scale));
        glEnd();
        Global.unBind();
    }
}
