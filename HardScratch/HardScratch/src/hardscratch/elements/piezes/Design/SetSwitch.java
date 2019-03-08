package hardscratch.elements.piezes.Design;

import hardscratch.elements.piezes.Hole;
import hardscratch.elements.piezes.Constructor;
import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;
import hardscratch.inputs.Mouse;
import java.util.ArrayList;

public class SetSwitch extends Element{

    private int width1, width2, rows;
    private static ArrayList<Constructor> Creator_To_Delete;
    
    public SetSwitch(){
        this(Mouse.getX()-331, Mouse.getY()-112);
    }
    
    public SetSwitch(int x, int y){
        this(x, y, -1);
    }
    
    public SetSwitch(int x, int y, int id) {
        super(x, y, id, true, true, true);
        width1 = 120; width2 = 120; rows = 1;
        Creator_To_Delete = new ArrayList<>();
        
        addShape(new Shape_Square(0, 0, Global.COLOR_SETSWITCH, 1, 2, 662, 224), 0, 0);
        addLabel(new TextLabel(0, 0, 1, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "SET", true), 51, 32);
        addHole(new Hole(0,0,1,Global.HOLE_VAR), 102, 10);
        addLabel(new TextLabel(0, 0, 1, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "SWITCH", true), 376, 32);
        addHole(new Hole(0,0,1,Global.HOLE_VAR), 450, 10);
        
        addCreator(new Constructor(0,0,Global.CREATOR_I,this,2),184,149);
        addLabel(new TextLabel(0, 0, 1, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_TEXT_INPUT, "ELSE", true), 70, 181);
        addImage(new Image(0, 0, 1, Global.TEXTURE_ARROW_2, 1, Global.COLOR_WHITE), 135, 159);
        
        addCreator(new Constructor(0,0,Global.CREATOR_I,this,1), 10, 74);
        addCreator(new Constructor(0,0,Global.CREATOR_I,this,2), 184, 74);
        addImage(new Image(0, 0, 1, Global.TEXTURE_ARROW_2, 1, Global.COLOR_WHITE), 135, 84);
        
        addBoundingBox(0, 662, 0, 224, -1);
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        if(creators.size() < 3) return;
        
        int diff = width1, elseMove = rows;
        if(data2 == 1){
            width1 = 0;
            creators.stream().filter((c) -> (c.getIdentifier() == 1 && c.getWidth() > width1)).forEachOrdered((c) -> {
                width1 = c.getWidth();
            });
            diff = width1 - diff;
        }else if(data2 == 2){
            width2 = 0;
            creators.stream().filter((c) -> (c.getIdentifier() == 2 && c.getWidth() > width2)).forEachOrdered((c) -> {
                width2 = c.getWidth();
            });
            diff = 0;
        }
        
        for(Image i:images)
            i.move(diff,0);
        for(Constructor c:creators)
            if(c.getIdentifier() == 2)
                c.move(diff, 0);
        
        //Agregar una fila si la ultima no esta vacia
        if(creators.size() >= 2 && (!creators.get(creators.size()-1).isEmpty() || !creators.get(creators.size()-2).isEmpty())){
            addCreator(new Constructor(0,0,Global.CREATOR_I,this,1), 10, 74+(rows*75));
            addCreator(new Constructor(0,0,Global.CREATOR_I,this,2), 64+width1, 74+(rows*75));
            addImage(new Image(0, 0, 1, Global.TEXTURE_ARROW_2, 1, Global.COLOR_WHITE), 15+width1, 84+(rows*75));
            rows++;
        }else{
            //Eliminar todas las que sobren
            for(int i = creators.size()-1; i > 0; i-=2){
                if(i >= 4 && creators.get(i).isEmpty() && creators.get(i-1).isEmpty() &&
                             creators.get(i-2).isEmpty() && creators.get(i-3).isEmpty()){
                    rows--;
                    Creator_To_Delete.add(creators.get(i));
                    Creator_To_Delete.add(creators.get(i-1));
                    images.remove(i/2);
                }else{
                    break;
                }
            }
        }
        elseMove = rows - elseMove;
        
        creators.get(0).move(0, elseMove*75);
        labels.get(2).move(0, elseMove*75);
        images.get(0).move(0, elseMove*75);
        
        int width = (74+width1+width2)<662?662:(74+width1+width2);
        dropShapes();
        addShape(new Shape_Square(0, 0, Global.COLOR_SETSWITCH, 1, 2, width, 149+(rows*75)), 0, 0);
        whipeBounds();
        addBoundingBox(0, width, 0, 149+(rows*75), -1);
    }
    
    @Override
    protected void selected_loop() {
        if(!Creator_To_Delete.isEmpty()){
            for(Constructor c: Creator_To_Delete)
                creators.remove(c);
            Creator_To_Delete.clear();
        }
    }
    
    
    //HardWork
    public ArrayList<Constructor> getExpresions(){
        ArrayList<Constructor> expresions = new ArrayList<>();
        
        for(int i = 1; i < creators.size(); i+=2)
            expresions.add(creators.get(i));
        
        return expresions;
    }
    public ArrayList<Constructor> getValues(){
        ArrayList<Constructor> values = new ArrayList<>();
        
        for(int i = 0; i < creators.size(); i+=2)
            values.add(creators.get(i));
        
        return values;
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
