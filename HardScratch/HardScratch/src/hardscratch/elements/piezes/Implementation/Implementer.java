package hardscratch.elements.piezes.Implementation;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Hole;

public class Implementer extends Element{

    private int type;
    private String identifier;
    
    public Implementer(int x, int y, int type, int n) {
        super(x, y, -1, true, true, false);
        this.type = type;
        
        int hole_type = -1;
        String text = "ERROR";
        switch(type){
            case Global.IMPLEMENTER_LED:    hole_type = Global.HOLE_VAR_OUT; text = "LIGHT EMITING DIODE "+n;   identifier = "LED"+n; break;
            case Global.IMPLEMENTER_SWITCH: hole_type = Global.HOLE_VAR_IN;  text = "SWITCH PULL UP "+n;        identifier = "SWITCH"+n; break;
            case Global.IMPLEMENTER_BUTTON: hole_type = Global.HOLE_VAR_IN;  text = "BUTTON PRESS "+n;          identifier = "BUTTON"+n; break;
            case Global.IMPLEMENTER_BCD:    hole_type = Global.HOLE_VAR_OUT; text = "7 SEGMENTS DISPLAY "+n;    identifier = "BCD"+n; break;
            case Global.IMPLEMENTER_CLOCK:  hole_type = Global.HOLE_VAR_IN;  text = "BASE CLOCK "+n;            identifier = "CLOCK"+n; break;
        }
        
        addShape(new Shape_Square(0, 0, Global.COLOR_IMPLEMENTER, 1, 5, 452, 108), 0, 0);
        addHole(new Hole(0, 0, 2, hole_type), 125, 10);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, text, true), 226, 80);
    }
    
    public String getIdentifier(){
        return identifier;
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
        return false;
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
