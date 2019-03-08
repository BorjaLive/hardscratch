package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Constructor;
import hardscratch.inputs.Mouse;

public class WaitFor extends Element{

    public WaitFor(){
        this(Mouse.getX()-198, Mouse.getY()-42);
    }
    
    public WaitFor(int x, int y){
        this(x, y, -1);
    }

    public WaitFor(int x, int y, int id) {
        super(x, y, id, true, true, true);
        
        addShape(new Shape_Square(0, 0, Global.COLOR_WAITFOR, 1, 3, 330, 85), 0, 0);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "WAIT FOR", true), 100, 42);
        addCreator(new Constructor(0, 0, Global.CREATOR_A, this, 1), 200, 10);
        addBoundingBox(0, 330, 0, 85, -1);
        
        Global.addSEQPorts(this,0,0,0,85,Global.COLOR_WAITFOR);
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        switch(event){
            case Global.EVENT_RESIZE:
                whipeBounds();
                shapes.get(0).resize(210+data1, 85);
                addBoundingBox(0, 210+data1, 0, 85, -1);
            break;
            case Global.EVENT_DOCK:
                if(ports.get(0).isOcupied())
                    ports.get(0).getDock().updateEvent(Global.EVENT_DOCK, data1, data2, data3);
            break;
        }
    }
    
    @Override
    public int getHeight(){
        return 115+(ports.get(1).isOcupied()?ports.get(1).getDock().getHeight():0);
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
