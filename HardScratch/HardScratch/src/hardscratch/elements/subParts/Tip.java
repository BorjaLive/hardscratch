package hardscratch.elements.subParts;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.Port;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextBox;
import hardscratch.base.shapes.TextLabel;
import hardscratch.inputs.Mouse;

public class Tip extends Element{
    
    private int value, compativility;
    private int width, height;
    private int selectedI;
    
    public Tip(int x, int y, int value) {
        super(x, y, 3, true, true, true);
        this.value = value;
        selectedI = -1;
        
        switch(value){
            case Global.TIP_VAR_IN:
                width = 155; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "IN", true), (width/2), (height/2));
            break;
            case Global.TIP_VAR_OUT:
                width = 155; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "OUT", true), (width/2), (height/2));
            break;
            case Global.TIP_VAR_SIGNAL:
                width = 155; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "SIGNAL", true), (width/2), (height/2));
            break;
            case Global.TIP_VAR_CONST:
                width = 155; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "CONST", true), (width/2), (height/2));
            break;
            case Global.TIP_CONSTRUCTOR_LITERAL_N:
                width = 155; height = 44;
                addTextBox(new TextBox(0, 0, 3, 0.4f, Global.FONT_MONOFONTO, "NUMBER", Global.COLOR_TEXT_INPUT, Global.COLOR_TEXT_INPUT_PLACEHOLDER, Global.TEXT_INPUT_BACK, Global.COLOR_BORDER_UNSELECTED, Global.COLOR_BORDER_SELECTED, 155, 44, 10, false, true, true), 0, 0);
            break;
            case Global.TIP_CONSTRUCTOR_LITERAL_B:
                width = 50; height = 44;
                addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "'", true), 8, 20);
                addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "'", true), 42, 20);
                addTextBox(new TextBox(0, 0, 3, 0.4f, Global.FONT_MONOFONTO, "0", Global.COLOR_TEXT_INPUT, Global.COLOR_TEXT_INPUT_PLACEHOLDER, Global.TEXT_INPUT_BACK, Global.COLOR_BORDER_UNSELECTED, Global.COLOR_BORDER_SELECTED, 1, 1, 10, true, true, true), 10, 3);
            break;
            case Global.TIP_CONSTRUCTOR_LITERAL_A:
                width = 193; height = 44;
                addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "\"", true), 10, 20);
                addLabel(new TextLabel(0, 0, 2, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "\"", true), 182, 20);
                addTextBox(new TextBox(0, 0, 3, 0.4f, Global.FONT_MONOFONTO, "00000000", Global.COLOR_TEXT_INPUT, Global.COLOR_TEXT_INPUT_PLACEHOLDER, Global.TEXT_INPUT_BACK, Global.COLOR_BORDER_UNSELECTED, Global.COLOR_BORDER_SELECTED, 8, 1, 10, true, true, true), 15, 3);
            break;
            case Global.TIP_CONSTRUCTOR_VAR_I:
            break;
            case Global.TIP_CONSTRUCTOR_VAR_B:
            break;
            case Global.TIP_CONSTRUCTOR_VAR_A:
            break;
            case Global.TIP_CONSTRUCTOR_VAR_C:
            break;
            case Global.TIP_CONSTRUCTOR_OPEN:
                width = 30; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "(", true), 10, (height/2));
            break;
            case Global.TIP_CONSTRUCTOR_CLOSE:
                width = 30; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, ")", true), 20, (height/2));
            break;
            case Global.TIP_CONSTRUCTOR_ARITH_ADD:
                width = 40; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "+", true), (width/2), (height/2));
            break;
            
            
            case Global.TIP_CONSTRUCTOR_LOGIC_AND:
                width = 95; height = 44;
                addLabel(new TextLabel(0, 0, 3, 0.5f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "AND", true), (width/2), (height/2));
            break;
        }
        switch(value){
            case Global.TIP_VAR_IN:
            case Global.TIP_VAR_OUT:
            case Global.TIP_VAR_SIGNAL:
            case Global.TIP_VAR_CONST:  compativility = Global.HOLE_VAR_INOUT; break;
            case Global.TIP_CONSTRUCTOR_LITERAL_A:
            case Global.TIP_CONSTRUCTOR_LITERAL_B:
            case Global.TIP_CONSTRUCTOR_LITERAL_N:
            case Global.TIP_CONSTRUCTOR_VAR_I:
            case Global.TIP_CONSTRUCTOR_VAR_B:
            case Global.TIP_CONSTRUCTOR_VAR_A:
            case Global.TIP_CONSTRUCTOR_VAR_C:
            case Global.TIP_CONSTRUCTOR_EQUALITY:
            case Global.TIP_CONSTRUCTOR_CONCAT:
            case Global.TIP_CONSTRUCTOR_OPEN:
            case Global.TIP_CONSTRUCTOR_CLOSE:
            case Global.TIP_CONSTRUCTOR_ARITH_ADD:
            case Global.TIP_CONSTRUCTOR_ARITH_SUB:
            case Global.TIP_CONSTRUCTOR_ARITH_TIM:
            case Global.TIP_CONSTRUCTOR_ARITH_TAK:
            case Global.TIP_CONSTRUCTOR_LOGIC_AND:
            case Global.TIP_CONSTRUCTOR_LOGIC_OR:
            case Global.TIP_CONSTRUCTOR_LOGIC_XOR:
            case Global.TIP_CONSTRUCTOR_LOGIC_NAND:
            case Global.TIP_CONSTRUCTOR_LOGIC_NOR:
            case Global.TIP_CONSTRUCTOR_LOGIC_XNOR:
            case Global.TIP_CONSTRUCTOR_LOGIC_NOT:      compativility = Global.HOLE_CONSTRUCTOR; break;
        }
        
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
    protected void select_init(int ID) {
        int x = Mouse.getX(), y = Mouse.getY();
        for(int i = 0; i < boxen.size(); i++){
            TextBox box = boxen.get(i);
            if(x > box.getX() && x < box.getX()+box.getWidth() &&
               y > box.getY() && y < box.getY()+box.getHeight())
                selectedI = i;
        }
        if(selectedI != -1)
            boxen.get(selectedI).on_selected();
        changeColorShape(0, Global.COLOR_BORDER_SELECTED);
    }

    @Override
    protected void select_end() {
        if(selectedI != -1)
            boxen.get(selectedI).de_select();
        selectedI = -1;
        changeColorShape(0, Global.COLOR_BORDER_UNSELECTED);
    }

    @Override
    protected void selected_loop() {
        if(selectedI != -1)
            boxen.get(selectedI).act();
    }

    @Override
    public boolean drag_loop() {return true;}

    @Override
    public void drag_init() {}

    @Override
    public void drag_end(){
        //Alertar de que se ha dejado o borrar
        changeColorShape(0, Global.COLOR_BORDER_UNSELECTED);
        Hole h = Controller.searchHole(Controller.getClolideID());
        //System.out.println("ME DIERON: "+h.getID());
        if(h == null || !h.assign(this))
            Controller.deleteElement(ID);
        setDragable(false);
    }
    public int getCompativility(){
        return compativility;
    }
    public int getValue(){
        return value;
    }
    
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    @Override
    public void action(int action){}

    @Override
    public Hole getHoleByID(int id) {
        return null;
    }

    public boolean middleSelected(){
        return selectedI != -1;
    }
    @Override
    public void delete() {
    }

    @Override
    public Port[] getPorts() {
        return new Port[]{};
    }
}
