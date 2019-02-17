package hardscratch.base.shapes;

import hardscratch.Global;
import hardscratch.base.*;
import static org.lwjgl.opengl.GL11.*;

public class TextLabel extends ElementBase{
    
    private Font font;
    private String text;
    private char[] letters;
    private int[] color;
    
    public TextLabel(int x, int y, int depth, float scale, Font font, int[] color, String text) {
        super(x, y, depth, scale);
        
        this.font = font;
        this.text = text;
        this.color = color;
        generateLetters();
    }
    
    
    public void setFont(Font f){
        font = f;
    }
    public int[] getColor(){
        return color;
    }
    public void setColor(int[] c){
        color = c;
    }
    public String getText(){
        return text;
    }
    public void setText(String t){
        text = t;
        generateLetters();
    }
    public void concatText(String t){
        text += t;
        generateLetters();
    }
    public boolean deleteLastChar(){
        if(text.length() > 0){
            text = text.substring(0, text.length()-1);
            generateLetters();
            return true;
        }
        return false;
    }
    
    private void generateLetters(){
        letters = text.toCharArray();
    }
    
    public int getLines(){
        return text.length() - text.replace("\n", "").length();
    }
    public int getCharsLastLine(){
        String lines[] = text.split("\n");
        return lines[lines.length-1].length();
    }
    
    
    public void draw(){
        Texture letter;
        int line = 0, count = 0;
        float   width = scale*Dot.relativeSizeX(font.getWidth()),
                height = scale*Dot.relativeSizeY(font.getHeight());
        
        glColor3f(color[0], color[1], color[2]);
        
        for(int i = 0; i < letters.length; i++){
            if(letters[i] == '\n'){
                line++;
                count = 0;
            }else{
                letter = font.getCharacter((int)letters[i]);
                letter.bind();
                glBegin(GL_QUADS);
                for(int j = 0; j < Global.SHAPE_TEXTURE.length; j++){
                    glTexCoord2f(Global.SHAPE_TEXTURE[j][0], Global.SHAPE_TEXTURE[j][1]);
                    glVertex2f( position.getRelX()+(Global.SHAPE_TEXTURE[j][0]*width) +(Dot.relativeSizeX(font.getLetterSpace()*count)),
                                position.getRelY()+(Global.SHAPE_TEXTURE[j][1]*height)+(Dot.relativeSizeY(font.getLinesPace()*line)));
                }
                glEnd();
                Global.unBind();
                count++;
            }
        }
    }
    
}
