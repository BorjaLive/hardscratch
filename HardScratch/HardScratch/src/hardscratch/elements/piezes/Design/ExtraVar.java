package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextBox;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Constructor;
import hardscratch.inputs.Mouse;
import java.util.ArrayList;

public class ExtraVar extends Element{
    
    public ExtraVar(){
        this(Mouse.getX()-159, Mouse.getY()-59);
    }
    
    public ExtraVar(int x, int y){
        this(x, y, -1, "");
    }
    
    public ExtraVar(int x, int y, int id, String varName) {
        super(x, y, id, true, true, true);
        
        addTextBox(new TextBox(0, 0, 1, 0.5f, null, "VAR NAME", null, null, null, null, null, 8, 1, 10, true, true, true), 10, 75);
        addPort(0,318,0,30, Global.PORT_MALE, Global.PORT_EXTRAVAR);
        addPort(222,252,30,148, Global.PORT_FEMALE, Global.PORT_INICIALIZER);
        addPort(0,318,148,178, Global.PORT_FEMALE, Global.PORT_EXTRAVAR);
        
        addLabel(new TextLabel(0, 0, 1, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "Extra Var", true), 111, 55);
        addShape(new Shape_Square(0, 0, Global.COLOR_EXTRAVAR, 1, 2, 162, 30), 30, 0);
        addShape(new Shape_Square(0, 0, Global.COLOR_EXTRAVAR, 1, 2, 30, 30), 222, 30);
        addShape(new Shape_Square(0, 0, Global.COLOR_EXTRAVAR, 1, 2, 30, 30), 222, 118);
        addShape(new Shape_Square(0, 0, Global.COLOR_EXTRAVAR, 1, 2, 30, 30), 0, 148);
        addShape(new Shape_Square(0, 0, Global.COLOR_EXTRAVAR, 1, 2, 30, 30), 192, 148);
        addShape(new Shape_Square(0, 0, Global.COLOR_EXTRAVAR, 1, 2, 222, 118), 0, 30);
        
        boxen.get(0).setText(varName);
        
        addBoundingBox(0, 222, 30, 148, -1);
    }

    
    //HardWork
    public ArrayList<String> getVars(){
        ArrayList<String> names;
        if(ports.get(2).isOcupied()){
            ExtraVar e = (ExtraVar) ports.get(2).getDock();
            names = e.getVars();
        }else{
            names = new ArrayList<>();
        }
        
        if(isComplete())
            names.add(boxen.get(0).getText());
        else
            names.add(null);
        
        return names;
    }
    public ArrayList<Long> getIDs(){
        ArrayList<Long> ids;
        
        if(ports.get(2).isOcupied()){
            ExtraVar e = (ExtraVar) ports.get(2).getDock();
            ids = e.getIDs();
        }else{
            ids = new ArrayList<>();
        }
        
        ids.add(ID);
        
        return ids;
    }
    public ArrayList<Constructor> getCreators(){
        ArrayList<Constructor> crts;
        if(ports.get(2).isOcupied()){
            ExtraVar e = (ExtraVar) ports.get(2).getDock();
            crts = e.getCreators();
        }else{
            crts = new ArrayList<>();
        }
        
        if(ports.get(1).isOcupied())
            crts.add(ports.get(1).getDock().getCreator(0));
        else
            crts.add(null);
        
        return crts;
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
    public void updateEvent(int Identifier, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }

    
    
}
