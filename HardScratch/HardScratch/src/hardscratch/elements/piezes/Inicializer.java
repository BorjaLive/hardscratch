package hardscratch.elements.piezes;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.subParts.Constructor;
import hardscratch.elements.subParts.Hole;

public class Inicializer extends Element{

    private final Constructor creator;
    private int selected_ID;
    
    Port declarator_port;
    
    public Inicializer(int x, int y) {
        super(x, y, 5, true, true, true);
        selected_ID = -1;
        
        declarator_port = new Port(x+0,x+30,y+0,y+118, Global.PORT_MALE, Global.PORT_INICIALIZER);
        
        creator = new Constructor(x+40,y+43,Global.CREATOR_I,this);
        
        addLabel(new TextLabel(0, 0, 1, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "INICIALIZER", true), 190, 25);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 58), 0, 30);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 320, 118), 30, 0);
        
        addBoundingBox(30, 350, 0, 118, -1);
    }

    @Override
    protected int colideExtra(int x, int y) {
        int colide = -1;
        if(x > creator.getX() && x < creator.getX()+creator.getWidth()
        && y > creator.getY() && y < creator.getY()+creator.getHeight())
                colide = creator.getID();
        return colide;
    }

    @Override
    public Hole getHoleByID(int id) {       //RETURN THE RIGHT HOLE in constructor
        return creator.getHoleByID(id);
    }

    @Override
    protected void moveExtra(int x, int y) {
        creator.move(x, y);
        declarator_port.move(x, y);
    }

    @Override
    protected void drawExtra() {
        creator.draw();
    }

    @Override
    protected void select_init(int ID) {
        selected_ID = ID;
        if(ID == creator.getID()){
            creator.on_selected();
        }else{
            selected_ID = -1;
        }
    }

    @Override
    protected void select_end() {
        if(selected_ID == creator.getID()){
            creator.de_select();
        }
    }

    @Override
    protected void selected_loop() {
        if(selected_ID == creator.getID()){
            creator.act();
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
        
        if(declarator_port.isOcupied() && !Port.inBound(declarator_port, declarator_port.getConnectedPort()))
            declarator_port.undock();
        
        if(declarator_port.isOcupied())
            Controller.dockingAlign(this, declarator_port);
    }
    @Override
    public void action(int action) {
        //action es el tamaño del constructor
        if(action == -1 || shapes.size() < 2)  return;
        shapes.remove(1);
        whipeBounds();
        if(action <= 300){
            addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 320, 118), 30, 0);
            addBoundingBox(30, 350, 0, 118, -1);
        }else{
            addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 20+action, 118), 30, 0);
            addBoundingBox(30, action+50, 0, 118, -1);
        }
    }

    @Override
    public void delete() {
        creator.delete();
        declarator_port.undock();
    }

    @Override
    public Port[] getPorts() {
        return new Port[]{declarator_port};
    }

    
    
}
