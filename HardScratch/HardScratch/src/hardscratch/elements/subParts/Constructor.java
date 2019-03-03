package hardscratch.elements.subParts;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.ElementBase;
import hardscratch.base.shapes.Shape_Square;
import java.util.ArrayList;

public class Constructor extends ElementBase{
    
    private final int type, identifier;
    private int width, height;
    private int selectedID, selectedI;
    private Element father;
    
    private final int BORDER = 5, DAFT_W = 100, DAFT_H = 44, LINKER_W = 50, LINKER_H = 44;
    private Shape_Square border, back;
    private ArrayList<Hole> holes;
    private int iniciate;
    
    public Constructor(int x, int y, int type, Element father, int identifier) {
        super(x, y, 2, 1);
        this.type = type;
        this.father = father;
        holes = new ArrayList<>();
        selectedID = -1; selectedI = -1;
        this.identifier = identifier;
        iniciate = 0;
        
        height = 65; width = BORDER*3;
        border = new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, depth, width, height);
        back = new Shape_Square(x+BORDER, y+BORDER, Global.COLOR_HOLE_BACK, 1, depth, width-BORDER*2, height-BORDER*2);
        
        iniciate();
    }
    
    private void iniciate(){
        holes.clear();
        setWidth(BORDER*3);
        insertDaft();
    }
    
    private void insertDaft(){
        int compativility = 0;
        switch(type){
            case Global.CREATOR_I: compativility=Global.CONSTRUCTOR_LITERAL_CONST; break;
            case Global.CREATOR_A: compativility=Global.CONSTRUCTOR_LITERAL_VARS; break;
            case Global.CREATOR_E: compativility=Global.CONSTRUCTOR_LITERAL_VARS; break;
            case Global.CREATOR_N:  break;
        }
        holes.add(new Hole(getX()+width-BORDER,getY()+10,2,compativility));
        holes.get(holes.size()-1).forceCompativity(Global.HOLE_CONSTRUCTOR);
        holes.get(holes.size()-1).SetConstructorFedBack(this);
        changeWidth(DAFT_W+BORDER);
    }
    private void insertLinker(){
        int compativility = 0;
        switch(type){
            case Global.CREATOR_I: compativility=Global.CONSTRUCTOR_OPERATORS; break;
            case Global.CREATOR_A: compativility=Global.CONSTRUCTOR_OPERATORS; break;
            case Global.CREATOR_E: compativility=Global.CONSTRUCTOR_OPERATORS_PLUS; break;
            case Global.CREATOR_N:  break;
        }
        holes.add(new Hole(getX()+width-BORDER,getY()+10,2,compativility));
        holes.get(holes.size()-1).forceCompativity(Global.HOLE_CONSTRUCTOR);
        holes.get(holes.size()-1).SetConstructorFedBack(this);
        changeWidth(LINKER_W+BORDER);
    }
    private void deleteHole(int I){
        for(int i = I; i < holes.size(); i++){
            changeWidth(-holes.get(i).getWidth()-BORDER);
            holes.remove(i);
        }
        if(selectedI == I)
            selectedI = -1;
        if(holes.isEmpty())
            iniciate();
    }
    public void checkHoles(){
        //Mover los agujetos para que quepan
        int difference;
        for(int i = 0; i < holes.size(); i++){
            if(holes.get(i).isAsigned())
                difference = holes.get(i).getTip().getWidth() - holes.get(i).getWidth();
            else
                difference = 0;
            if(difference != 0){
                for(int j = i+1; j < holes.size(); j++){
                    holes.get(j).move(difference, 0);
                }
            }
            changeWidth(difference);
        }
        
        //Buscar que no haya más de dos agujeros libres al final
        for(int i = holes.size()-1; i > 0; i--){
            if(holes.get(i).isAsigned()) break;
            if(!holes.get(i).isAsigned() && !holes.get(i-1).isAsigned()){
                deleteHole(i);
            }
        }
        //Buscar que haya al menos un agujeto libre al final
        if(holes.get(holes.size()-1).isAsigned()){
            if(holes.get(holes.size()-1).getTip().getValue() == Global.TIP_CONSTRUCTOR_OPEN || holes.get(holes.size()-1).getTip().getValue() == Global.TIP_CONSTRUCTOR_CLOSE){
                switch(holes.get(holes.size()-1).getHoleType()){
                    case Global.CONSTRUCTOR_LITERAL_CONST:
                    case Global.CONSTRUCTOR_LITERAL_VARS:   insertDaft();break;
                    case Global.CONSTRUCTOR_OPERATORS:
                    case Global.CONSTRUCTOR_OPERATORS_PLUS: insertLinker(); break;
                }
            }else{
                switch(holes.get(holes.size()-1).getHoleType()){
                    case Global.CONSTRUCTOR_LITERAL_CONST:
                    case Global.CONSTRUCTOR_LITERAL_VARS:   insertLinker();break;
                    case Global.CONSTRUCTOR_OPERATORS:
                    case Global.CONSTRUCTOR_OPERATORS_PLUS: insertDaft(); break;
                }
            }
        }
        
        father.updateEvent(Global.EVENT_RESIZE,width,identifier,"");
    }
    
    private void changeWidth(int w){
        setWidth(width+w);
    }
    private void setWidth(int w){
        width=w;
        border.resize(width, height);
        back.resize(width-BORDER*2, height-BORDER*2);
    }
    
    public boolean isEmpty(){
        for(Hole h:holes)
            if(h.isAsigned())
                return false;
        return true;
    }
    
    
    @Override
    public void move(int x, int y){
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        border.move(x, y);
        back.move(x, y);
        for(Hole hole:holes)
            hole.move(x, y);
    }
    
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    
    
    @Override
    public void draw(){
        border.draw();
        back.draw();
        for(Hole hole:holes)
            hole.draw();
    }
    
    public int getColideHoleID(){
        for(int i = 0; i < holes.size(); i++){
            if(holes.get(i).colide()){
                return holes.get(i).getID();
            }
        }
        return -1;
    }
    public Hole getHoleByID(int id){
        for(int i = 0; i < holes.size(); i++){
            if(holes.get(i).colide()){
                return holes.get(i);
            }
        }
        //Esto es un poco de truco
        return null;
    }
    public int getIdentifier(){
        return identifier;
    }

    public void on_selected() {
        border.setColor(Global.COLOR_BORDER_SELECTED);
        
        for(int i = 0; i < holes.size(); i++){
            if(holes.get(i).colide()){
                selectedI = i;
                selectedID = holes.get(i).getID();
                break;
            }
        }
        if(selectedI == -1){
            Controller.setFinder(1);
        }else{
            Controller.setFinder(holes.get(selectedI).getHoleType());
            holes.get(selectedI).on_selected();
        }
    }

    public void de_select() {
        border.setColor(Global.COLOR_BORDER_UNSELECTED);
        if(selectedI != -1)
            holes.get(selectedI).de_select();
        selectedI = -1; selectedID = -1;
        Controller.setFinder(1);
    }

    public void act() {
        if(selectedI != -1)
            holes.get(selectedI).act();
    }

    public void delete() {
        for(Hole h:holes){
            h.SetConstructorFedBack(null);
            h.delete();       }
    }
    
}
