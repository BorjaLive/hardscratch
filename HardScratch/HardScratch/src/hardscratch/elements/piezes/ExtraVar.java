package hardscratch.elements.piezes;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.Port;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextBox;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.subParts.Hole;

public class ExtraVar extends Element{

    private int selected_ID;
    private final TextBox identifier_text;
    
    private Port declarator_port, initer_port, extra_port;
    
    public ExtraVar(int x, int y) {
        super(x, y, 5, true, true, true);
        
        selected_ID = -1;
        identifier_text = new TextBox(x+10, y+67, 1, 0.5f, Global.FONT_MONOFONTO, "VAR NAME", Global.COLOR_TEXT_INPUT, Global.COLOR_TEXT_INPUT_PLACEHOLDER, Global.TEXT_INPUT_BACK, Global.COLOR_BORDER_UNSELECTED, Global.COLOR_BORDER_SELECTED, 12, 1, 10, true, true, true);
        
        declarator_port = new Port(x+0,x+318,y+0,y+30, Global.PORT_MALE, Global.PORT_EXTRAVAR);
        initer_port = new Port(x+318,x+348,y+30,y+148, Global.PORT_FEMALE, Global.PORT_INICIALIZER);
        extra_port = new Port(x+0,x+318,y+148,y+178, Global.PORT_FEMALE, Global.PORT_EXTRAVAR);
        
        addLabel(new TextLabel(0, 0, 1, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "Extra Var", true), 160, 50);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 258, 30), 30, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 318, 30);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 318, 118);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 0, 148);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 288, 148);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 318, 118), 0, 30);
        
        addBoundingBox(0, 318, 30, 148, -1);
    }

    @Override
    protected int colideExtra(int x, int y) {
        int colide = -1;
        
        if(x > identifier_text.getX() && x < identifier_text.getX()+identifier_text.getWidth()
        && y > identifier_text.getY() && y < identifier_text.getY()+identifier_text.getHeight())
                colide = identifier_text.getID();
        
        return colide;
    }

    @Override
    public Hole getHoleByID(int id) {
        return null;
    }

    @Override
    protected void moveExtra(int x, int y) {
        identifier_text.move(x, y);
        declarator_port.move(x, y);
        initer_port.move(x, y);
        extra_port.move(x, y);
    }

    @Override
    protected void drawExtra() {
        identifier_text.draw();
    }

    @Override
    protected void select_init(int ID) {
        selected_ID = ID;
        if(ID == identifier_text.getID()){
            identifier_text.on_selected();
        }else{
            selected_ID = -1;
        }
    }

    @Override
    protected void select_end() {
        if(selected_ID == identifier_text.getID()){
            identifier_text.de_select();
        }
    }

    @Override
    protected void selected_loop() {
        if(selected_ID == identifier_text.getID()){
            identifier_text.act();
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
        if(declarator_port.isOcupied() && Controller.getElementByID(declarator_port.getID()) == null)
            declarator_port.undock();
        if(initer_port.isOcupied() && Controller.getElementByID(initer_port.getID()) == null)
            initer_port.undock();
        if(extra_port.isOcupied() && Controller.getElementByID(extra_port.getID()) == null)
            extra_port.undock();
        
        if(declarator_port.isOcupied() && !Port.inBound(declarator_port, declarator_port.getConnectedPort()))
            declarator_port.undock();
        
        if(declarator_port.isOcupied())
            Controller.dockingAlign(this, declarator_port);
    }

    @Override
    public void action(int action) {
    }

    @Override
    public void delete() {
        if(initer_port.isOcupied())
            Controller.deleteElement(initer_port.getDock().getID());
        if(extra_port.isOcupied())
            Controller.deleteElement(extra_port.getDock().getID());
        
        declarator_port.undock();
    }

    @Override
    public Port[] getPorts() {
        return new Port[]{declarator_port,initer_port,extra_port};
    }

    
    
}
