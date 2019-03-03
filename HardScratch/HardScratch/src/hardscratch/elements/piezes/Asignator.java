package hardscratch.elements.piezes;

import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.subParts.Constructor;
import hardscratch.elements.subParts.Hole;

public class Asignator extends Element{
    
    public Asignator(int x, int y) {
        super(x, y, true, true, true);
        
        addHole(new Hole(0,0, 1, Global.HOLE_VAR), 10, 53);
        addCreator(new Constructor(0,0,Global.CREATOR_A,this,1), 266, 43);
        
        addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "ASIGNATOR", true), 198, 25);
        addShape(new Shape_Square(0, 0, Global.COLOR_ASIGNATOR, 1, 4, 396, 118), 0, 0);
        addImage(new Image(0, 0, 2, Global.TEXTURE_ARROW_1, 1, Global.COLOR_WHITE), 217, 53);
        
        addBoundingBox(0, 396, 0, 118, -1);
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
        switch(event){
            case Global.EVENT_RESIZE:
                if(shapes.size() < 1)  return;
                shapes.remove(0);
                whipeBounds();
                if(data1 <= 120){
                    addShape(new Shape_Square(0, 0, Global.COLOR_ASIGNATOR, 1, 4, 396, 118), 0, 0);
                    addBoundingBox(30, 396, 0, 118, -1);
                }else{
                    addShape(new Shape_Square(0, 0, Global.COLOR_ASIGNATOR, 1, 4, 276+data1, 118), 0, 0);
                    addBoundingBox(30, data1+276, 0, 118, -1);
                }
            break;
        }
    }

    @Override
    public void latWish() {
    }
    
}
