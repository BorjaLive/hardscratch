package hardscratch.base.shapes;

import hardscratch.Controller;
import hardscratch.base.*;
import hardscratch.inputs.Keyboard;

public class TextBox extends ElementBase{
    
    TextLabel label;
    Font font;
    String placeholder;
    Shape_Square box1, box2;
    int maxW, maxH, weight, height, border;
    //boolean selected;
    boolean empty, tight;
    int alignW,alignH;
    float[] color_selected, color_notSelected, color_text, color_placeholder;
    private final int ID;
    
    public TextBox(int x, int y, int depth, float scale, Font font, String placeholder, float[] color_text, float[] color_placeholder, float[] color_back, float[] color_border_1, float[] color_border_2, int width, int height, int border, boolean sizeMode, boolean tight, boolean align) {
        super(x, y, depth, scale);
        
        ID = Controller.generateID();
        
        this.color_notSelected = color_border_1;
        this.color_selected = color_border_2;
        this.color_text = color_text;
        this.color_placeholder = color_placeholder;
        this.font = font;
        this.placeholder = placeholder;
        if(sizeMode){
            maxW = width;
            maxH = height-1;
            width *= (int) (scale*font.getLetterSpace());
            height *= (int) (scale*font.getLinesPace());
            width += border*(tight?1:2); height += border*(tight?1:2);
        }else{
            maxW = (int) ((width-border*(tight?1:2))/(scale*font.getLetterSpace()));
            maxH = (int) ((height-border*(tight?1:2))/(scale*font.getLinesPace()))-1;
        }
        this.weight = width;
        this.height = height;
        this.border = border;
        this.tight = tight;
        this.alignW = (align?(weight/2)-(tight?border/2:0):0)+(tight?border/2:border);
        this.alignH = (align?(height/2)-(tight?border/2:0):0)+(tight?border/2:border);
        
        label = new TextLabel(x+alignW,y+alignH,0,scale,font,color_placeholder,placeholder, align);
        box1 = new Shape_Square(x+border/2,y+border/2,color_back,1,1,width-border,height-border);
        box2 = new Shape_Square(x,y,color_border_1,1,2,width,height);
        //this.selected = false;
        empty = true;
    }
    
    @Override
    public void moveAbsolute(int x, int y){
        position.setCords(x, y);
        box1.moveAbsolute(x+border/2,y+border/2);
        box2.moveAbsolute(x, y);
        label.moveAbsolute(x+alignW,y+alignH);
    }
    
    public int getWidth(){
        return weight;
    }
    public int getHeight(){
        return height;
    }
    
    public void on_selected(){
        //selected = true;
        box2.setColor(color_selected);
    }
    public void de_select(){
        //selected = false;
        box2.setColor(color_notSelected);
    }
    public void act(){
        char c = Keyboard.getInput();
        //System.out.println("INPUT: "+c+" CHARS: "+label.getText()+"   SIZE_W:"+label.getCharsLastLine()+"/"+maxW+"   SIZE_H:"+label.getLines()+"/"+maxH);
        switch(c){
            case 0:
                break;
            case '|':
                if(label.getLines() < maxH)
                    label.concatText("\n");
            break;
            case '~':
                if(!label.deleteLastChar()){
                    empty = true;
                }
            break;
            case '£':
                empty = true;
                label.setText("");
            break;
            default:
                if(empty){
                    empty = false;
                    label.setText("");
                    label.setColor(color_text);
                }
                if(label.getCharsLastLine() < maxW)
                    label.concatText(c+"");
                else if(label.getLines() < maxH && c != ' ')
                    label.concatText("\n"+c);
        }
        if(empty){
            label.setColor(color_placeholder);
            label.setText(placeholder);
        }
    }
    
    
    public void draw(){
        box2.draw();
        box1.draw();
        label.draw();
    }

    public int getID() {
        return ID;
    }
    
}
