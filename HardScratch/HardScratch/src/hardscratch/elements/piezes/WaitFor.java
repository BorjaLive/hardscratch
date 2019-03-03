package hardscratch.elements.piezes;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.subParts.Constructor;

public class WaitFor extends Element{

    public WaitFor(int x, int y) {
        super(x, y, true, true, true);
        
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
        }
    }
    
    @Override
    public int getHeight(){
        return 85+(ports.get(1).isOcupied()?ports.get(1).getDock().getHeight():0);
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
