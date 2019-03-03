package hardscratch.elements.piezes;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.subParts.Hole;

public class WaitOn extends Element{

    public WaitOn(int x, int y) {
        super(x, y, true, true, true);
        
        addShape(new Shape_Square(0, 0, Global.COLOR_WAITON, 1, 3, 434, 96), 0, 0);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "WAIT FOR", true), 111, 21);
        addHole(new Hole(0, 0, 2, Global.HOLE_EDGE), 10, 42);
        addHole(new Hole(0, 0, 2, Global.HOLE_VAR), 222, 26);
        addBoundingBox(0, 434, 0, 96, -1);
        
        Global.addSEQPorts(this,0,0,0,95,Global.COLOR_WAITON);
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
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }
    
}
