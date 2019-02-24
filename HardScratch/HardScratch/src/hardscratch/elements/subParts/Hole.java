package hardscratch.elements.subParts;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.inputs.Keyboard;
import hardscratch.inputs.Mouse;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;

public class Hole extends ElementBase{
    
    private final int BORDER = 5;
    private int hole_type, width, height, forcedCompativility;
    private Shape_Square border, back;
    private Tip tip;
    private Constructor fedBack;

    public Hole(int x, int y, int depth, int hole_type) {
        super(x, y, depth, 1);
        
        ID = Controller.generateID();
        this.hole_type = hole_type;
        tip = null;
        forcedCompativility = -1;
        //System.out.println("SOY DE TIPO: "+hole_type+" CON ID: "+ID);
        
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
                width = 298;
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
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void on_selected() {
        if(tip == null){
            border.setColor(Global.COLOR_BORDER_SELECTED);
        }else{
            tip.select_init(-1);
        }
        Controller.setFinder(hole_type);
    }

    public void de_select() {
        if(tip == null)
            border.setColor(Global.COLOR_BORDER_UNSELECTED);
        else
            tip.select_end();
        Controller.setFinder(1);
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
        if(tip == null && (t.getCompativility() == hole_type || t.getCompativility() == forcedCompativility)){
            tip = t;
            //tip.move(getX()-tip.getX(), getY()-tip.getY());
            Controller.moveAuto(tip, getX(), getY(), 30);
            on_selected();
            if(fedBack != null){
                fedBack.checkHoles();
                Controller.setFinder(1);
            }
            resizeAbsolute(t.getWidth(),t.getHeight());
            return true;
        }
        return false;
    }
    
    public void delete(){
        if(tip != null){
            Controller.deleteElement(tip.getID());
            tip = null;
            if(fedBack != null)
                fedBack.checkHoles();
            //setDefaultSize();
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
}
