package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.elements.piezes.Constructor;
import hardscratch.inputs.Mouse;

public class Inicializer extends Element{
    
    public Inicializer(){
        this(Mouse.getX()-140, Mouse.getY()-59);
    }
    
    public Inicializer(int x, int y){
        this(x, y, -1);
    }
    
    public Inicializer(int x, int y, int id) {
        super(x, y, id, true, true, true);
        
        addPort(0,30,0,118, Global.PORT_MALE, Global.PORT_INICIALIZER);
        
        addCreator(new Constructor(0,0,Global.CREATOR_I,this, 1), 40, 43);
        
        addLabel(new TextLabel(0, 0, 1, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "INICIALIZER", true), 190, 25);
        addShape(new Shape_Square(0, 0, Global.COLOR_INICIALIZER, 1, 3, 30, 58), 0, 30);
        addShape(new Shape_Square(0, 0, Global.COLOR_INICIALIZER, 1, 3, 320, 118), 30, 0);
        
        addBoundingBox(30, 350, 0, 118, -1);
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
        switch(event){
            case Global.EVENT_RESIZE:
                if(shapes.size() < 2)  return;
                shapes.remove(1);
                whipeBounds();
                if(data1 <= 300){
                    addShape(new Shape_Square(0, 0, Global.COLOR_INICIALIZER, 1, 2, 320, 118), 30, 0);
                    addBoundingBox(30, 350, 0, 118, -1);
                }else{
                    addShape(new Shape_Square(0, 0, Global.COLOR_INICIALIZER, 1, 2, 20+data1, 118), 30, 0);
                    addBoundingBox(30, data1+20, 0, 118, -1);
                }
            break;
        }
    }

    @Override
    public void latWish() {
    }

    
    
}
