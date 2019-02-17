package hardscratch.base.shapes;

import hardscratch.Global;
import hardscratch.base.*;
import static org.lwjgl.opengl.GL11.*;

public class Image extends ElementBase{
    
    private final Texture texture;
    private int[] color;
    private int width, height;
    
    public Image(int x, int y, int depth, Texture texture, float scale, int[] color) {
        super(x, y, depth, scale);
        
        this.texture = texture;
        this.color = color;
        width = texture.getWidth();
        height = texture.getHeight();
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    public void draw(){
        texture.bind();
        glBegin(GL_QUADS);
        glColor3f(color[0], color[1], color[2]);
        for(int i = 0; i < Global.SHAPE_TEXTURE.length; i++){
            glTexCoord2f(Global.SHAPE_TEXTURE[i][0], Global.SHAPE_TEXTURE[i][1]);
            glVertex2f(position.getRelX()+(Global.SHAPE_TEXTURE[i][0]*scale*Dot.relativeSizeX(width)), position.getRelY()+(Global.SHAPE_TEXTURE[i][1]*scale*Dot.relativeSizeY(height)));
        }
        glEnd();
        Global.unBind();
    }
    
}
