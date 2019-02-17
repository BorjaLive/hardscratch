package hardscratch.base.shapes;

import hardscratch.base.*;
import hardscratch.inputs.Keyboard;

public class TextBox extends ElementBase{
    
    TextLabel label;
    Font font;
    String placeholder;
    Shape_Square box1, box2;
    int maxW, maxH, weight, height, border;
    //boolean selected;
    boolean empty;
    int[] color_selected, color_notSelected, color_text, color_placeholder;
    
    public TextBox(int x, int y, int depth, float scale, Font font, String placeholder, int[] color_text, int[] color_placeholder, int[] color_back, int[] color_border_1, int[] color_border_2, int width, int height, int border) {
        super(x, y, depth, scale);
        
        this.color_notSelected = color_border_1;
        this.color_selected = color_border_2;
        this.color_text = color_text;
        this.color_placeholder = color_placeholder;
        this.font = font;
        this.placeholder = placeholder;
        this.weight = width;
        this.height = height;
        this.border = border;
        
        label = new TextLabel(x+border,y+border,0,scale,font,color_placeholder,placeholder);
        box1 = new Shape_Square(x+border/2,y+border/2,color_back,scale,1,width-border,height-border);
        box2 = new Shape_Square(x,y,color_border_1,scale,2,width,height);
        maxW = (int) (width-border*2)/font.getLetterSpace();
        maxH = (int) ((height-border*2)/font.getLinesPace())-1;
        //this.selected = false;
        empty = true;
    }
    
    @Override
    public void moveAbsolute(int x, int y){
        position.setCords(x, y);
        box1.moveAbsolute(x+border/2,y+border/2);
        box2.moveAbsolute(x, y);
        label.moveAbsolute(x+border,y+border);
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
            default:
                if(empty){
                    empty = false;
                    label.setText("");
                    label.setColor(color_text);
                }
                if(label.getCharsLastLine() < maxW)
                    label.concatText(c+"");
                else if(label.getLines() < maxH)
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
    
}
