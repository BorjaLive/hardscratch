package hardscratch.base.shapes;

import hardscratch.Global;
import hardscratch.base.*;
import static org.lwjgl.opengl.GL11.*;

public class Image extends ElementBase{
    
    private Texture texture;
    private float[] color;
    private int width, height;
    private boolean reverseW, reverseH;
    
    public Image(int x, int y, int depth, Texture texture, float scale, float[] color) {
        super(x, y, depth, scale);
        
        this.texture = texture;
        this.color = color;
        width = texture.getWidth();
        height = texture.getHeight();
        
        reverseW = false;
        reverseH = false;
    }
    
    public void setTexture(Texture t){
        texture = t;
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void setColor(float[] c){
        color = c;
    }
    
    public void setInvertW(boolean i){
        reverseW = i;
    }
    public void setInvertH(boolean i){
        reverseH = i;
    }
    public void setInvertW(){
        reverseW = !reverseW;
    }
    public void setInvertH(){
        reverseH = !reverseH;
    }
    
    public void draw(){
        texture.bind();
        glBegin(GL_QUADS);
        glColor3f(color[0], color[1], color[2]);
        for(int i = 0; i < Global.SHAPE_TEXTURE.length; i++){
            glTexCoord2f(reverseW?(1-Global.SHAPE_TEXTURE[i][0]):(Global.SHAPE_TEXTURE[i][0]),
                         reverseH?(1-Global.SHAPE_TEXTURE[i][1]):(Global.SHAPE_TEXTURE[i][1]));
            glVertex2f(position.getRelX()+(Global.SHAPE_TEXTURE[i][0]*scale*Dot.relativeSizeX(width)), position.getRelY()+(Global.SHAPE_TEXTURE[i][1]*scale*Dot.relativeSizeY(height)));
        }
        glEnd();
        Global.unBind();
    }
    
}
