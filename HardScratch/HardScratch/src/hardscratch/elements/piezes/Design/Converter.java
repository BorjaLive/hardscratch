package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.piezes.Hole;
import hardscratch.inputs.Mouse;

public class Converter extends Element{
    
    public Converter(){
        this(Mouse.getX()-239, Mouse.getY()-48);
    }
    
    public Converter(int x, int y){
        this(x, y, -1);
    }
    
    public Converter(int x, int y, int id) {
        super(x, y, id, true, true, true);
        
        addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "CONVERTER", true), 239, 20);
        addShape(new Shape_Square(0, 0, Global.COLOR_CONVERTER, 1, 3, 478, 97), 0, 0);
        addImage(new Image(0, 0, 2, Global.TEXTURE_ARROW_1, 1, Global.COLOR_WHITE), 217, 43);
        
        addHole(new Hole(0,0, 1, Global.HOLE_VAR), 10, 43);
        addHole(new Hole(0,0, 1, Global.HOLE_VAR), 266, 43);
        
        addBoundingBox(0, 478, 0, 97, -1);
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
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }
    
}
