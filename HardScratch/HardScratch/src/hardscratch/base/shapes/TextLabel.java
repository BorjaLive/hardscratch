package hardscratch.base.shapes;

import hardscratch.Global;
import hardscratch.base.*;
import static org.lwjgl.opengl.GL11.*;

public class TextLabel extends ElementBase{
    
    private Font font;
    private String text;
    private char[] letters;
    private float[] color;
    boolean align;
    private float strech;
    private float alpha;
    
    public TextLabel(int x, int y, int depth, float scale, float[] color, String text, boolean align) {
        this(x, y, depth, scale, Global.FONT_MONOFONTO, color, text, align);
        strech = 1.5f;
    }
    public TextLabel(int x, int y, int depth, float scale, Font font, float[] color, String text, boolean align) {
        super(x, y, -1, depth, scale);
        strech = 1;
        alpha = 1;
        
        this.font = font;
        this.text = text;
        this.color = color;
        this.align = align; //Center align
        generateLetters();
    }
    
    
    public void setFont(Font f){
        font = f;
    }
    public float[] getColor(){
        return color;
    }
    public void setColor(float[] c){
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
        }
        return text.length() > 0;
    }
    
    private void generateLetters(){
        if(text == null)
            letters = new char[0];
        else
            letters = text.toCharArray();
    }
    
    public int getLines(){
        return text.length() - text.replace("\n", "").length();
    }
    public int getCharsLastLine(){
        return getCharsLine(getLines());
    }
    public int getCharsLine(int n){
        String lines[] = text.split("\n");
        return n>=lines.length?0:lines[n].length();
    }
    
    public void setScretch(float s){
        strech = s;
    }
    public void setAlpha(float a){
        alpha = a;
    }
    
    
    @Override
    public void draw(){
        Texture letter;
        int line = 0, count = 0;
        float   width = scale*Dot.relativeSizeX(font.getWidth()),
                height = scale*Dot.relativeSizeY(font.getHeight()),
                spacingW = scale*Dot.relativeSizeX(font.getLetterSpace()),
                spacingH = scale*Dot.relativeSizeX((int) (font.getLinesPace()*strech));
        
        glColor4f(color[0], color[1], color[2], alpha);
        
        for(int i = 0; i < letters.length; i++){
            if(letters[i] == '\n'){
                line++;
                count = 0;
            }else{
                float paddingW, paddingH;
                if(align)
                    paddingH = (spacingH*(getLines()+1.5f))/2;
                else paddingH = 0;
                
                letter = font.getCharacter((int)letters[i]);
                letter.bind();
                glBegin(GL_QUADS);
                for(int j = 0; j < Global.SHAPE_TEXTURE.length; j++){
                    if(align)
                        paddingW = (spacingW*getCharsLine(line))/2;
                    else paddingW = 0;
                    glTexCoord2f(Global.SHAPE_TEXTURE[j][0], Global.SHAPE_TEXTURE[j][1]);
                    glVertex2f( position.getRelX()+(Global.SHAPE_TEXTURE[j][0]*width) +(spacingW*count) - paddingW,
                                position.getRelY()+(Global.SHAPE_TEXTURE[j][1]*height*strech)-(spacingH*line)  + paddingH);
                }
                glEnd();
                Global.unBind();
                count++;
            }
        }
    }
    
}
