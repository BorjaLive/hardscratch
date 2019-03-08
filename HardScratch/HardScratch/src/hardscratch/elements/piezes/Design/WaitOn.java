package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Hole;
import hardscratch.inputs.Mouse;

public class WaitOn extends Element{

    public WaitOn(){
        this(Mouse.getX()-198, Mouse.getY()-42);
    }
    
    public WaitOn(int x, int y){
        this(x, y, -1, "-1");
    }

    public WaitOn(int x, int y, int id, String tipEdge) {
        super(x, y, id, true, true, true);
        
        addShape(new Shape_Square(0, 0, Global.COLOR_WAITON, 1, 3, 434, 96), 0, 0);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "WAIT ON", true), 111, 21);
        addHole(new Hole(0, 0, 2, Global.HOLE_EDGE), 10, 42);
        addHole(new Hole(0, 0, 2, Global.HOLE_VAR), 222, 26);
        addBoundingBox(0, 434, 0, 96, -1);
        
        holes.get(0).forceAsign(tipEdge);
        
        Global.addSEQPorts(this,0,0,0,95,Global.COLOR_WAITON);
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        switch(event){
            case Global.EVENT_DOCK:
                if(ports.get(0).isOcupied())
                    ports.get(0).getDock().updateEvent(Global.EVENT_DOCK, data1, data2, data3);
            break;
        }
    }
    
    @Override
    public int getHeight(){
        return 126+(ports.get(1).isOcupied()?ports.get(1).getDock().getHeight():0);
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
