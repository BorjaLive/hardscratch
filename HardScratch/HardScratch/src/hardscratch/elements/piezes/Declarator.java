package hardscratch.elements.piezes;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.subParts.Hole;

public class Declarator extends Element{
    
    private final TextBox identifier_text;
    private final Hole type_hole, inOut_hole;
    private int selected_ID;
    
    private Port extra_port, initer_port;

    public Declarator(int x, int y) {
        super(x, y, 5, true, true, true); //El ultimo true es que es borrable
        
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 483, 118), 0, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 0, 118);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 288, 118);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 483, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 483, 88);
        addLabel(new TextLabel(0, 0, 1, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "Declarator", true), 159, 25);
        
        extra_port = new Port(x+0,x+258,y+118,y+148, Global.PORT_FEMALE, Global.PORT_EXTRAVAR);
        initer_port = new Port(x+483,x+513,y+0,y+118, Global.PORT_FEMALE, Global.PORT_INICIALIZER);
        
        selected_ID = -1;
        identifier_text = new TextBox(x+10, y+50, 1, 0.5f, Global.FONT_MONOFONTO, "VAR NAME", Global.COLOR_TEXT_INPUT, Global.COLOR_TEXT_INPUT_PLACEHOLDER, Global.TEXT_INPUT_BACK, Global.COLOR_BORDER_UNSELECTED, Global.COLOR_BORDER_SELECTED, 12, 1, 10, true, true, true);
        inOut_hole = new Hole(x+318,y+10,1,Global.HOLE_VAR_INOUT);
        type_hole = new Hole(x+318,y+64,1,Global.HOLE_VAR_TYPE);
        
        addBoundingBox(0, 483, 0, 118, -1);
    }
    
    
    @Override
    protected int colideExtra(int x, int y) {
        int colide = -1;
        
        if(x > identifier_text.getX() && x < identifier_text.getX()+identifier_text.getWidth()
        && y > identifier_text.getY() && y < identifier_text.getY()+identifier_text.getHeight())
                colide = identifier_text.getID();
        
        if(x > inOut_hole.getX() && x < inOut_hole.getX()+inOut_hole.getWidth()
        && y > inOut_hole.getY() && y < inOut_hole.getY()+inOut_hole.getHeight())
                colide = inOut_hole.getID();
        if(x > type_hole.getX() && x < type_hole.getX()+type_hole.getWidth()
        && y > type_hole.getY() && y < type_hole.getY()+type_hole.getHeight())
                colide = type_hole.getID();
        
        return colide;
    }
    
    @Override
    protected void select_init(int ID) {
        selected_ID = ID;
        if(ID == identifier_text.getID()){
            identifier_text.on_selected();
        }else if(ID == inOut_hole.getID()){
            inOut_hole.on_selected();
        }else if(ID == type_hole.getID()){
            type_hole.on_selected();
        }else{
            selected_ID = -1;
        }
    }

    @Override
    protected void select_end() {
        if(selected_ID == identifier_text.getID()){
            identifier_text.de_select();
        }else if(selected_ID == inOut_hole.getID()){
            inOut_hole.de_select();
        }else if(selected_ID == type_hole.getID()){
            type_hole.de_select();
        }
    }

    @Override
    protected void selected_loop() {
        if(selected_ID == identifier_text.getID()){
            identifier_text.act();
        }else if(selected_ID == inOut_hole.getID()){
            inOut_hole.act();
        }else if(selected_ID == type_hole.getID()){
            type_hole.act();
        }
    }
    

    @Override
    public boolean drag_loop() {
        return true;
    }
    @Override
    public void drag_init() {
        
    }
    @Override
    public void drag_end() {
        if(extra_port.isOcupied() && Controller.getElementByID(extra_port.getID()) == null)
            extra_port.undock();
        if(initer_port.isOcupied() && Controller.getElementByID(initer_port.getID()) == null)
            initer_port.undock();
    }
    @Override
    public void action(int action) {
        
    }

    
    
    @Override
    protected void moveExtra(int x, int y) {
        type_hole.move(x, y);
        inOut_hole.move(x, y);
        identifier_text.move(x, y);
        extra_port.move(x, y);
        initer_port.move(x, y);
    }
    @Override
    protected void drawExtra() {
        type_hole.draw();
        inOut_hole.draw();
        identifier_text.draw();
    }

    @Override
    public Hole getHoleByID(int id) {
        if(type_hole.getID() == id) return type_hole;
        else if(inOut_hole.getID() == id) return inOut_hole;
        else return null;
    }

    @Override
    public void delete() {
        if(type_hole != null)
            type_hole.delete();
        if(inOut_hole != null)
            inOut_hole.delete();
        
        if(initer_port.isOcupied())
            Controller.deleteElement(initer_port.getDock().getID());
        if(extra_port.isOcupied())
            Controller.deleteElement(extra_port.getDock().getID());
    }

    @Override
    public Port[] getPorts() {
        return new Port[]{extra_port,initer_port};
    }

    
}
