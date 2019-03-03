package hardscratch.elements.piezes;

import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.subParts.Hole;

public class Converter extends Element{
    
    public Converter(int x, int y) {
        super(x, y, true, true, true);
        
        addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "CONVERTER", true), 239, 20);
        addShape(new Shape_Square(0, 0, Global.COLOR_CONVERTER, 1, 2, 478, 97), 0, 0);
        addImage(new Image(0, 0, 2, Global.TEXTURE_ARROW_1, 1, Global.COLOR_WHITE), 217, 43);
        
        addHole(new Hole(0,0,1,Global.HOLE_VAR), 10,43);
        addHole(new Hole(0,0,1,Global.HOLE_VAR), 266,43);
        
        addBoundingBox(0, 478, 0, 97, -1);
    }

    @Override
    protected int colideExtra(int x, int y) {
        return -1;
    }

    @Override
    public Hole getHoleByID(int id) {
        return null;
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
    public Port[] getPorts() {
        return new Port[]{};
    }

    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }
    
}
