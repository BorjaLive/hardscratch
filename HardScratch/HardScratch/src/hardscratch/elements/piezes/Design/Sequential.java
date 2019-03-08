package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.inputs.Mouse;

public class Sequential extends Element{

    public Sequential(){
        this(Mouse.getX()-200, Mouse.getY()-27);
    }
    
    public Sequential(int x, int y){
        this(x, y, -1);
    }
    
    public Sequential(int x, int y, int id) {
        super(x, y, id, true, true, true);
        
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
    protected long colideExtra(int x, int y) {
        return -1;
    }

    @Override
    protected void moveExtra(int x, int y) {
    }

    @Override
    protected void drawExtra() {
    }

    @Override
    protected void select_init(long ID) {
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
