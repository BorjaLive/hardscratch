package hardscratch.base;

import hardscratch.base.shapes.Texture;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Font {
    
    private Texture[] chars;
    private int width, height, letterSpace, lineSpace;
    
    public Font(String file, int width, int height, int letterSpace, int lineSpace){
        BufferedImage buffer;
        try {
            buffer = ImageIO.read(new File(file));
            chars = new Texture[128];
            for(int i = 0; i < 16; i++){
                for(int j = 0; j < 8; j++){
                    chars[i*8 + j] = new Texture(buffer,i,j,width,height);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.width = width;
        this.height = height;
        this.letterSpace = letterSpace;
        this.lineSpace = lineSpace;
    }
    
    public int getLetterSpace(){
        return letterSpace;
    }
    public int getLinesPace(){
        return lineSpace;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    public Texture getCharacter(int n){
        return chars[n-32];
    }
    
    public float getFixedScale(float size){
        return size/height;
    }
    public float getFixedScale(float size, float stretch){
        return getFixedScale(size/stretch);
    }
    
    /*
    public void draw(){
        Texture letter = getCharacter(36);
        letter.bind();
        glBegin(GL_QUADS);
        glColor3f(255, 255, 255);
        for(int i = 0; i < Global.SHAPE_TEXTURE.length; i++){
            glTexCoord2f(Global.SHAPE_TEXTURE[i][0], Global.SHAPE_TEXTURE[i][1]);
            glVertex2f((Global.SHAPE_TEXTURE[i][0]*Dot.relativeSizeX(width)), (Global.SHAPE_TEXTURE[i][1]*Dot.relativeSizeY(height)));
        }
        glEnd();
        Global.unBind();
    }
    */
}
