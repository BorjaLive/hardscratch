package hardscratch.elements.subParts;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;

public class Tip extends Element{
    
    private int value, compativility;
    private int width, height;
    
    public Tip(int x, int y, int value) {
        super(x, y, 3, true, true, true);
        this.value = value;
        
        TextLabel label = new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "", true);
        switch(value){
            case Global.TIP_VAR_IN:
            case Global.TIP_VAR_OUT:
            case Global.TIP_VAR_SIGNAL:
            case Global.TIP_VAR_CONST:
                width = 155; height = 44;
            break;
        }
        switch(value){
            case Global.TIP_VAR_IN:     label.setText("IN");    compativility = Global.HOLE_VAR_INOUT; break;
            case Global.TIP_VAR_OUT:    label.setText("OUT");   compativility = Global.HOLE_VAR_INOUT; break;
            case Global.TIP_VAR_SIGNAL: label.setText("SIGNAL");compativility = Global.HOLE_VAR_INOUT; break;
            case Global.TIP_VAR_CONST:  label.setText("CONST"); compativility = Global.HOLE_VAR_INOUT; break;
        }
        
        addLabel(label, (width/2), (height/2));
        addShape(new Shape_Square(0, 0, Global.COLOR_BORDER_SELECTED, 1, 3, width, height), 0, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_HOLE_BACK, 1, 3, width-10, height-10), 5, 5);
    }
    
    @Override
    public void moveExtra(int x, int y){}

    @Override
    protected int colideExtra(int x, int y) {return -1;}

    @Override
    protected void drawExtra() {}

    @Override
    protected void select_init(int ID) {}

    @Override
    protected void select_end() {}

    @Override
    protected void selected_loop() {}

    @Override
    public boolean drag_loop() {return true;}

    @Override
    public void drag_init() {}

    @Override
    public void drag_end(){
        //Alertar de que se ha dejado o borrar
        changeColorShape(0, Global.COLOR_BORDER_UNSELECTED);
        Hole h = Controller.searchHole(Controller.getClolideID());
        System.out.println("END");
        if(h == null || !h.assign(this))
            Controller.deleteElement(ID);
    }
    public int getCompativility(){
        return compativility;
    }

    @Override
    public void action(int action){}

    @Override
    public Hole getHoleByID(int id) {
        return null;
    }

    @Override
    public void delete() {
    }
}
