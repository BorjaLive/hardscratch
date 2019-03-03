package hardscratch.elements.piezes;

import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.subParts.Hole;

public class Declarator extends Element{

    public Declarator(int x, int y) {
        super(x, y, true, true, true); //El ultimo true es que es borrable
        
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 388, 118), 0, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 0, 118);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 192, 118);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 388, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_DECLARATOR, 1, 2, 30, 30), 388, 88);
        addLabel(new TextLabel(0, 0, 1, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "DECLARATOR", true), 111, 25);
        
        addPort(0, 222,118,148, Global.PORT_FEMALE, Global.PORT_EXTRAVAR);
        addPort(388,418,0,118, Global.PORT_FEMALE, Global.PORT_INICIALIZER);
        
        addTextBox(new TextBox(0, 0, 1, 0.5f, null, "VAR NAME", null, null, null, null, null, 8, 1, 10, true, true, true), 10, 50);
        addHole(new Hole(0,0,1,Global.HOLE_VAR_INOUT), 222, 10);
        addHole(new Hole(0,0,1,Global.HOLE_VAR_TYPE), 222, 64);
        
        addBoundingBox(0, 388, 0, 118, -1);
    }
    
    
    @Override
    protected int colideExtra(int x, int y) {
        return -1;
    }
    
    @Override
    protected void select_init(int ID) {
    }

    @Override
    protected void select_end() {
    }

    @Override
    protected void selected_loop() {
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
    }
    @Override
    public void action(int action) {
    }
    
    @Override
    protected void moveExtra(int x, int y) {
    }
    @Override
    protected void drawExtra() {
    }

    @Override
    public void updateEvent(int Identifier, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }

    
}
