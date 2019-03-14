package hardscratch.elements.piezes;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.backend.Variable;
import hardscratch.base.*;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.inputs.Keyboard;
import hardscratch.inputs.Mouse;
import java.util.regex.Pattern;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;

public class Hole extends ElementBase{
    
    private final int BORDER = 5;
    private int hole_type, width, height, forcedCompativility;
    private final Shape_Square border, back;
    private Tip tip;
    private Constructor fedBack;
    private boolean selected;

    public Hole(int x, int y, int depth, int hole_type) {
        super(x, y, depth, 1);
        
        ID = Controller.generateID();
        this.hole_type = hole_type;
        tip = null;
        forcedCompativility = -1;
        
        setDefaultSize();
        
        border = new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, depth, width, height);
        back = new Shape_Square(x+BORDER, y+BORDER, Global.COLOR_HOLE_BACK, 1, depth, width-BORDER*2, height-BORDER*2);
    }
    public void forceCompativity(int fc){
        forcedCompativility = fc;
    }
    public void SetConstructorFedBack(Constructor c){
        fedBack = c;
    }
    
    public final void setDefaultSize(){
        switch(hole_type){
            case Global.HOLE_VAR:
            case Global.HOLE_EDGE:
            case Global.HOLE_VAR_IN:
            case Global.HOLE_VAR_OUT:
                width = 202;
                height = 44;
                break;
            case Global.HOLE_VAR_INOUT:
            case Global.HOLE_VAR_TYPE:
                width = 155;
                height = 44;
                break;
            case Global.CONSTRUCTOR_LITERAL_CONST:
            case Global.CONSTRUCTOR_LITERAL_VARS:
                width = 100;
                height = 44;
                break;
            case Global.CONSTRUCTOR_OPERATORS:
            case Global.CONSTRUCTOR_OPERATORS_PLUS:
                width = 50;
                height = 44;
                break;
        }
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
        resizeAbsolute(width+x, height+y);
    }
    public final void resizeAbsolute(int x, int y){
        width = x;
        height = y;
        border.resize(width, height);
        back.resize(width-BORDER*2, height-BORDER*2);
    }
    

    @Override
    public void draw(){
        border.draw();
        back.draw();
        if(tip != null)
            tip.draw();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void on_selected() {
        selected = true;
        
        if(tip == null){
            border.setColor(Global.COLOR_BORDER_SELECTED);
        }else{
            tip.select_init(-1);
        }
        Controller.setFinder(hole_type);
    }

    public void de_select() {
        selected = false;
        
        if(tip == null)
            border.setColor(Global.COLOR_BORDER_UNSELECTED);
        else
            tip.select_end();
        if(Controller.getRoom() == Global.ROOM_DESIGN)
            Controller.setFinder(1);
        else if(Controller.getRoom() == Global.ROOM_IMPLEMENT)
            Controller.setFinder(Global.HOLE_VAR);
        ///so on
    }

    public void act() {
        //ACT
        if(tip != null)
            tip.selected_loop();
        if(Keyboard.getClick(GLFW_KEY_DELETE) && (tip == null || !tip.middleSelected() || true)){//porque esta idea no es tan buena
            delete();
            de_select();
        }
    }
    
    public boolean assign(Tip t){
        if(tip == null && (t.getCompativility() == hole_type || t.getCompativility() == forcedCompativility ||
                          (forcedCompativility == Global.HOLE_CONSTRUCTOR && t.getCompativility() == Global.HOLE_VAR)) ||
                          (t.getCompativility() == Global.HOLE_VAR && (hole_type == Global.HOLE_VAR_IN || hole_type == Global.HOLE_VAR_OUT))){
            tip = t;
            //tip.move(getX()-tip.getX(), getY()-tip.getY());
            if(Global.QUICK_MOVE)
                tip.move(getX()-tip.getX(),getY()-tip.getY());
            else
                Controller.moveAuto(tip, getX(), getY(), 30);
            on_selected();
            if(fedBack != null){
                fedBack.checkHoles();
                Controller.setFinder(1);
            }
            resizeAbsolute(t.getWidth(),t.getHeight());
            Controller.deleteElement(tip.getID());
            
            if(!selected) tip.select_end();
            return true;
        }
        return false;
    }
    
    public void delete(){
        if(tip != null){
            //Controller.deleteElement(tip.getID());
            tip = null;
            //setDefaultSize();
            if(fedBack != null)
                fedBack.checkHoles();
        }
    }
    
    public int getHoleType(){
        return hole_type;
    }
    
    public boolean colide(){
        int x = Mouse.getX(), y = Mouse.getY();
        return x > getX() && x < getX()+width &&
               y > getY() && y < getY()+height;
    }

    public boolean isAsigned() {
        return tip != null;
    }
    public Tip getTip(){
        return tip;
    }
    public int getTipType(){
        if(tip != null)
            return tip.getValue();
        else
            return -1;
    }
    
    public void forceAsign(String value){
        if(value.equals("-1")) return;
        
        if(isAsigned())
            tip = null;
        
        Variable var = Controller.getVarByName(value);
        if(var != null){
            tip = new Tip(getX(), getY(), Global.TIP_VAR).setVar(var);
        }else if(value.contains(":")){
            String[] parts = value.split(Pattern.quote(":"));
            if(parts[0].equals("29")){  //Array
                tip = new Tip(getX(),getY(), Integer.parseInt(parts[0]));
                if(parts.length > 1)
                    tip.getBox(0).setText(parts[1]);
            }else if(parts[0].equals("SA")){
                tip = new Tip(getX(),getY(), Global.TIP_VAR_SUBARRAY);
                Variable variable = Controller.getVarByName(parts[2]);
                if(variable != null){
                    tip.setVar(variable);
                    if(!parts[1].equals("100"))
                        tip.getBox(0).setText(parts[1]);
                }else{
                    tip = null;
                }
            }
        }else{
            tip = new Tip(getX(),getY(), Integer.parseInt(value));
        }
        
        if(tip != null)
            tip.select_end();
    }

    public boolean isSelected() {
        return selected;
    }
}
