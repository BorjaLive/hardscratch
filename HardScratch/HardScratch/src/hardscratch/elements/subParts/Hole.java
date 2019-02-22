package hardscratch.elements.subParts;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.inputs.Keyboard;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;

public class Hole extends ElementBase{
    
    private final int BORDER = 5;
    private int hole_type, width, height;
    Shape_Square border, back;
    private final int ID;
    private Tip tip;

    public Hole(int x, int y, int depth, int hole_type) {
        super(x, y, depth, 1);
        
        ID = Controller.generateID();
        this.hole_type = hole_type;
        tip = null;
        
        switch(hole_type){
            case 1:
                width = 298;
                height = 44;
                break;
            case 2:
            case 3:
                width = 155;
                height = 44;
                break;
        }
        border = new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, depth, width, height);
        back = new Shape_Square(x+BORDER, y+BORDER, Global.COLOR_HOLE_BACK, 1, depth, width-BORDER*2, height-BORDER*2);
    }
    
    @Override
    public final void move(int x, int y){
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        if(tip != null)
            tip.move(x, y);
    }
    @Override
    public final void moveAbsolute(int x, int y){
        position.setCords(x, y);
        border.moveAbsolute(x, y);
        back.moveAbsolute(x+BORDER, y+BORDER);
    }
    
    public final void resize(int x, int y){
        width += x;
        height += y;
        border.resize(width, height);
        back.resize(width-BORDER*2, height-BORDER*2);
    }
    

    public void draw(){
        border.draw();
        back.draw();
    }

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

    public void on_selected() {
        if(tip == null){
            border.setColor(Global.COLOR_BORDER_SELECTED);
        }else{
            tip.changeColorShape(0, Global.COLOR_BORDER_SELECTED);
        }
        Controller.setFinder(hole_type);
    }

    public void de_select() {
        if(tip == null)
            border.setColor(Global.COLOR_BORDER_UNSELECTED);
        else
            tip.changeColorShape(0, Global.COLOR_BORDER_UNSELECTED);
        Controller.setFinder(1);
        ///so on
    }

    public void act() {
        //ACT
        if(Keyboard.getClick(GLFW_KEY_DELETE)){
            delete();
            de_select();
        }
    }
    
    public int getID(){
        return ID;
    }
    
    public boolean assign(Tip t){
        if(tip == null && t.getCompativility() == hole_type){
            tip = t;
            //tip.move(getX()-tip.getX(), getY()-tip.getY());
            Controller.moveAuto(tip, getX(), getY(), 45);
            on_selected();
            return true;
        }
        return false;
    }
    
    public void delete(){
        if(tip != null){
            Controller.deleteElement(tip.getID());
            tip = null;
        }
    }
}
