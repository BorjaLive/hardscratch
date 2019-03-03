package hardscratch.elements.piezes;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;

public class Sequential extends Element{

    public Sequential(int x, int y) {
        super(x, y, true, true, true);
        
        addShape(new Shape_Square(0, 0, Global.COLOR_SEQUENTIAL, 1, 3, 400, 54), 0, 0);
        addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "SEQUENTIAL", true), 200, 27);
       
        addShape(new Shape_Square(0, 0, Global.COLOR_SEQUENTIAL, 1, 3, 30, 30), 0, 54);
        addShape(new Shape_Square(0, 0, Global.COLOR_SEQUENTIAL, 1, 3, 30, 30), 90, 54);
        addShape(new Shape_Square(0, 0, Global.COLOR_SEQUENTIAL, 1, 3, 30, 30), 180, 54);
        addPort(0, 210, 54, 84, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
        
        addBoundingBox(0, 400, 0, 54, -1);
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    protected int colideExtra(int x, int y) {
        return -1;
    }

    @Override
    protected void moveExtra(int x, int y) {
    }

    @Override
    protected void drawExtra() {
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
    public void latWish() {
    }
    
}
