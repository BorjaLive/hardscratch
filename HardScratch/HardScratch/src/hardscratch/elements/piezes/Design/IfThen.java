package hardscratch.elements.piezes.Design;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.Port;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Constructor;
import hardscratch.inputs.Mouse;
import java.util.ArrayList;

public class IfThen extends Element{
    
    private int width;
    private static ArrayList<Constructor> Creator_To_Delete;

    public IfThen(){
        this(Mouse.getX()-115, Mouse.getY()-85);
    }
    
    public IfThen(int x, int y){
        this(x, y, -1);
    }

    public IfThen(int x, int y, int id) {
        super(x, y, id, true, true, true);
        width = 120;
        Creator_To_Delete = new ArrayList<>();
        
        Global.addSEQPorts(this,0,0,0,170,Global.COLOR_IFTHEN);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 4, 230, 170), 0, 0);    // Shape 5: MAIN BODY
        addBoundingBox(0, 230, 0, 170, -1);
        
        
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "ELSE", true), 50, 127);
        addCreator(new Constructor(0, 0, Global.CREATOR_E, this, 1), 100, 95);
        creators.get(creators.size()-1).setDepthNegative();
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 4, 210, 55), 110+width, 85);
        addPort(110+width, 330+width, 140, 170, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 110+width, 140);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 200+width, 140);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 290+width, 140);
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "THEN", true), 215+width, 112);
        addBoundingBox(230, 440, 85, 140, -1);
        
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "IF", true), 50, 42);
        addCreator(new Constructor(0, 0, Global.CREATOR_E, this, 1), 100, 10);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 4, 210, 55), 110+width, 0);
        addPort(110+width, 330+width, 55, 85, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 110+width, 55);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 200+width, 55);
        addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 290+width, 55);
        addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "THEN", true), 215+width, 27);
        addBoundingBox(230, 440, 0, 55, -1);
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        switch(event){
            case Global.EVENT_DOCK:
                if(ports.get(0).isOcupied())
                    ports.get(0).getDock().updateEvent(Global.EVENT_DOCK, data1, data2, data3);
                //No tiene break para que se ejecute el resize
            case Global.EVENT_RESIZE:
                //Anchar el ancho
                width = 0;
                creators.stream().filter((c) -> (c.getWidth() > width)).forEachOrdered((c) -> {
                    width = c.getWidth();
                });
                
                //Agregar una fila si la ultima no esta vacia
                if(ports.size() >= 4 && creators.size() >= 2 && (ports.get(ports.size()-1).isOcupied() ||
                                                                 !creators.get(creators.size()-1).isEmpty())){
                    addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "IF", true), 0, 0);
                    addCreator(new Constructor(0, 0, Global.CREATOR_E, this, 1), 100, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 4, 210, 55), 0, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 0, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 0, 0);
                    addShape(new Shape_Square(0, 0, Global.COLOR_IFTHEN, 1, 3, 30, 30), 0, 0);
                    addLabel(new TextLabel(0, 0, 3, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "THEN", true), 0, 0);
                    addPort(0, 210, 0, 30, Global.PORT_FEMALE, Global.PORT_SEQUENTIAL);
                }else{
                    //Eliminar todas las que sobren
                    for(int i = creators.size()-1; i > 1; i--){
                        if(i+2 < ports.size() && i < creators.size()
                                && creators.get(i).isEmpty() && !ports.get(i+2).isOcupied()&&
                                   creators.get(i-1).isEmpty() && !ports.get(i+1).isOcupied()){
                            Creator_To_Delete.add(creators.get(i));
                            for(int j = 0; j < 2; j++)
                                labels.remove(2*i);
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
                    Global.moveA2R(creators.get(i),this, 100, shudPos+10);
                    Global.moveA2R(labels.get(i*2),this, 50, shudPos+42);
                    Global.moveA2R(labels.get((i*2)+1),this, width+215, shudPos+27);
                    Global.moveA2R(ports.get(i+2), this, width+110, shudPos+55);
                    
                    Global.moveA2R(shapes.get(6+(4*i)),this, width+110, shudPos);
                    Global.moveA2R(shapes.get(7+(4*i)),this, width+110, shudPos+55);
                    Global.moveA2R(shapes.get(8+(4*i)),this, width+200, shudPos+55);
                    Global.moveA2R(shapes.get(9+(4*i)),this, width+290, shudPos+55);
                    
                    addBoundingBox(width+110, width+320, shudPos, shudPos+55, -1);
                    
                }
                shudPos = getHeightFromTo(0, -1);
                shapes.get(5).resize(width+110, shudPos);
                Global.moveA2R(shapes.get(2),this, 0, shudPos);
                Global.moveA2R(shapes.get(3),this, 90, shudPos);
                Global.moveA2R(shapes.get(4),this, 180, shudPos);
                Global.moveA2R(ports.get(1), this, 0, shudPos);
                addBoundingBox(0, width+110, 0, shudPos, -1);
                
            break;
        }
        
        if(event != Global.EVENT_RESIZE)
            actDeleteQuee();
    }
    private int getHeightFromTo(int a, int b){
        if(a==-1) a = 2; else a+=2;
        if(b==-1) b = ports.size()-1; else b+=2;
        int h = 0;
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
    protected void select_end() {
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
