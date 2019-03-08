package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.Port;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Constructor;
import hardscratch.elements.piezes.Hole;
import hardscratch.inputs.Mouse;
import java.util.ArrayList;

public class SwitchCase extends Element{

    private int width;
    private static ArrayList<Constructor> Creator_To_Delete;

    public SwitchCase(){
        this(Mouse.getX()-181, Mouse.getY()-117);
    }
    
    public SwitchCase(int x, int y){
        this(x, y, -1);
    }
    
    public SwitchCase(int x, int y, int id) {
        super(x, y, id, true, true, true);
        width = 362;
        Creator_To_Delete = new ArrayList<>();
        
        Global.addSEQPorts(this,0,0,0,234,Global.COLOR_SWITCHCASE);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 4, 362, 234), 0, 0);    // Shape 5: MAIN BODY
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "SWITCH", true), 75, 32);
        addHole(new Hole(0, 0, 2, Global.HOLE_VAR), 150, 10);
        addBoundingBox(0, 362, 0, 234, -1);
        
        
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "ELSE", true), 75, 191);
        addCreator(new Constructor(0, 0, Global.CREATOR_I, this, 1), 150, 149);
        creators.get(creators.size()-1).setDepthNegative();
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 4, 210, 55), width, 149);
        addPort(width, width+210, 204, 234, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), width, 204);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), width+90, 204);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), width+180, 204);
        addBoundingBox(width, width+210, 150, 204, -1);
        
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "CASE", true), 75, 106);
        addCreator(new Constructor(0, 0, Global.CREATOR_I, this, 1), 150, 64);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 4, 210, 55), width, 64);
        addPort(width, width+210, 119, 149, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), width, 119);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), width+90, 119);
        addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), width+180, 119);
        addBoundingBox(width, width+210, 64, 119, -1);
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        switch(event){
            case Global.EVENT_DOCK:
                if(ports.get(0).isOcupied())
                    ports.get(0).getDock().updateEvent(Global.EVENT_DOCK, data1, data2, data3);
            case Global.EVENT_RESIZE:
                //Anchar el ancho
                width = 0;
                creators.stream().filter((c) -> (c.getWidth() > width-202)).forEachOrdered((c) -> {
                    width = c.getWidth()+202;
                });
                if(width < 362) width =362;
                
                //Agregar una fila si la ultima no esta vacia
                if(ports.size() >= 4 && creators.size() >= 2 && (ports.get(ports.size()-1).isOcupied() ||
                                                                 !creators.get(creators.size()-1).isEmpty())){
                    addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "CASE", true), 0, 0);
                    addCreator(new Constructor(0, 0, Global.CREATOR_I, this, 1), 100, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 4, 210, 55), 0, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), 0, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), 0, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_SWITCHCASE, 1, 3, 30, 30), 0, 0);
                    addPort(0, 210, 0, 30, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
                }else{
                    //Eliminar todas las que sobren
                    for(int i = creators.size()-1; i > 1; i--){
                        if(i+2 < ports.size() && i < creators.size()
                                && creators.get(i).isEmpty() && !ports.get(i+2).isOcupied()&&
                                   creators.get(i-1).isEmpty() && !ports.get(i+1).isOcupied()){
                            Creator_To_Delete.add(creators.get(i));
                            labels.remove(i+1);
                            for(int j = 0; j < 4; j++)
                                shapes.remove(6+(i*4));
                            ports.remove(i+2);
                        }else{
                            break;
                        }
                    }
                }
                
                whipeBounds();
                
                //Mover a su posicion todos los elementos
                int shudPos;
                for(int i = 0; i < creators.size()-Creator_To_Delete.size(); i++){
                    shudPos = getHeightFromTo(1, i-1);
                    //System.out.println("EL ShudPos de "+i+" es "+shudPos);
                    Global.moveA2R(creators.get(i),this, 150, shudPos);
                    Global.moveA2R(labels.get(i+1),this, 75, shudPos+42);
                    Global.moveA2R(ports.get(i+2), this, width, shudPos+55);
                    
                    Global.moveA2R(shapes.get(6+(4*i)),this, width, shudPos);
                    Global.moveA2R(shapes.get(7+(4*i)),this, width, shudPos+55);
                    Global.moveA2R(shapes.get(8+(4*i)),this, width+90, shudPos+55);
                    Global.moveA2R(shapes.get(9+(4*i)),this, width+180, shudPos+55);
                    
                    addBoundingBox(width, width+210, shudPos, shudPos+55, -1);
                    
                }
                shudPos = getHeightFromTo(0, -1);
                shapes.get(5).resize(width, shudPos);
                Global.moveA2R(shapes.get(2),this, 0, shudPos);
                Global.moveA2R(shapes.get(3),this, 90, shudPos);
                Global.moveA2R(shapes.get(4),this, 180, shudPos);
                Global.moveA2R(ports.get(1), this, 0, shudPos);
                addBoundingBox(0, width, 0, shudPos, -1);
                
            break;

        }
        
        if(event != Global.EVENT_RESIZE)
            actDeleteQuee();
    }
    private int getHeightFromTo(int a, int b){
        if(a==-1) a = 2; else a+=2;
        if(b==-1) b = ports.size()-1; else b+=2;
        int h = 64;
        for(int i = a; i <= b; i++)
            h += getHeightDock(i);
        return h;
    }
    private int getHeightDock(int n){
        Port p = ports.get(n);
        if(p.isOcupied()){
            return p.getDock().getHeight()+85;
        }else{
            return 85;
        }
    }
    
    @Override
    public int getHeight(){
        return getHeightFromTo(-1,-1)+30+(ports.get(1).isOcupied()?ports.get(1).getDock().getHeight():0);
    }
    
    private void actDeleteQuee(){
        if(!Creator_To_Delete.isEmpty()){
            for(Constructor c: Creator_To_Delete)
                creators.remove(c);
            Creator_To_Delete.clear();
        }
    }
    
    @Override
    protected void selected_loop() {
        actDeleteQuee();
    }
    
    
    
    
    //HardWork
    public ArrayList<Constructor> getConditions(){
        ArrayList<Constructor> list = new ArrayList<>();
        
        for(int i = 1; i < creators.size(); i++)
            list.add(creators.get(i));
        
        return list;
    }
    public ArrayList<Element> getInstructions(){
        ArrayList<Element> list = new ArrayList<>();
        
        for(int i = 2; i < ports.size(); i++)
            list.add(ports.get(i).getDock());
        
        return list;
    }
    public ArrayList<Port> getInstructionsPort(){
        ArrayList<Port> list = new ArrayList<>();
        
        for(int i = 2; i < ports.size(); i++)
            list.add(ports.get(i));
        
        return list;
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
