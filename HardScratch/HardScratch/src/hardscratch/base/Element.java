package hardscratch.base;

import hardscratch.elements.piezes.Hole;
import hardscratch.elements.piezes.Constructor;
import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.shapes.*;
import java.util.ArrayList;

public abstract class Element extends ElementBase{
    
    private boolean drawable, dragable, deleteable;
    private int maxDepth;
    private long focus_item, focus_itemPRE;
    private boolean drag_forced;
    
    protected ArrayList<Shape> shapes;
    protected ArrayList<Image> images;
    protected ArrayList<TextLabel> labels;
    protected ArrayList<TextBox> boxen;
    protected ArrayList<Hole> holes;            //New
    protected ArrayList<Constructor> creators;  //New
    protected ArrayList<Port> ports;            //New
    protected ArrayList<Shape_Square> bounds;
    private ArrayList<int[]> boundingBoxes;
    
    public Element(int x, int y, int ID, boolean drawable, boolean dragable, boolean deleteable) {
        super(x, y, 2, 1);
        
        if(ID == -1)
            this.ID = Controller.generateID();
        else
            this.ID = ID;
        this.drawable = drawable;
        this.dragable = dragable;
        this.deleteable = deleteable;
        shapes = new ArrayList<>();
        images = new ArrayList<>();
        bounds = new ArrayList<>();
        labels = new ArrayList<>();
        boxen = new ArrayList<>();
        holes = new ArrayList<>();
        ports = new ArrayList<>();
        creators = new ArrayList<>();
        drag_forced = false;
        boundingBoxes = new ArrayList<>();
        maxDepth = 0;
        focus_item = -1; focus_itemPRE = -1;
    }
    
    public final void addShape(Shape shape, int x, int y){
        shape.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        shapes.add(shape);
        if(shape.getDepth() > maxDepth)
            maxDepth = shape.getDepth();
    }
    public final void dropShapes(){
        shapes.clear();
    }
    public final  void addImage(Image image, int x, int y){
        image.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        images.add(image);
        if(image.getDepth() > maxDepth)
            maxDepth = image.getDepth();
    }
    public final void dropImages(){
        images.clear();
    }
    public final  void addLabel(TextLabel label, int x, int y){
        label.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        labels.add(label);
        if(label.getDepth() > maxDepth)
            maxDepth = label.getDepth();
    }
    public final  void addTextBox(TextBox box, int x, int y){
        box.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        boxen.add(box);
        if(box.getDepth() > maxDepth)
            maxDepth = box.getDepth();
    }
    public final void addHole(Hole hole, int x, int y){
        hole.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        holes.add(hole);
        if(hole.getDepth() > maxDepth)
            maxDepth = hole.getDepth();
    }
    public final void addCreator(Constructor creator, int x, int y){
        creator.move(position.getCordX()+x, position.getCordY()+y);
        creators.add(creator);
        if(creator.getDepth() > maxDepth)
            maxDepth = creator.getDepth();
    }
    public final void addPort(int x1, int x2, int y1, int y2, boolean gender, int type){
        ports.add(new Port(getX()+x1,getX()+x2,getY()+y1,getY()+y2,gender,type));
        ports.get(ports.size()-1).setEventCall(this);
    }
    public final  void addBoundingBox(int x1, int x2, int y1, int y2, int action){
        boundingBoxes.add(new int[]{x1,x2,y1,y2,action});
        Shape_Square bound = new Shape_Square(position.getCordX()+x1, position.getCordY()+y1,Global.COLOR_WHITE,1,0,x2-x1,y2-y1);
        bounds.add(bound);
    }
    public final void whipeBounds(){
        boundingBoxes.clear();
        bounds.clear();
    }
    
    public final  void changeColorShape(int n, float[] color){
        shapes.get(n).setColor(color);
    }
    public final void boundingBoxMove(int n, int x, int y){
        if(n >= boundingBoxes.size()) return;
        boundingBoxes.get(n)[0] += x;
        boundingBoxes.get(n)[1] += x;
        boundingBoxes.get(n)[2] += y;
        boundingBoxes.get(n)[3] += y;
        bounds.get(n).move(x, y);
    }
    
    public final boolean colide(int x, int y){
        focus_itemPRE = colideID(x, y);
        if(focus_itemPRE != -1)
            return true;
        
        //TODO: remplazar con un while
        for(int[] boundingBox : boundingBoxes){
            if( x-position.getCordX() > boundingBox[0] && x-position.getCordX() < boundingBox[1] &&
                y-position.getCordY() > boundingBox[2] && y-position.getCordY() < boundingBox[3])
                 if(boundingBox[4] == -1)
                    return true;
                else
                    action(boundingBox[4]);
        }
        
        return false;
    }
    public long colideID(int x, int y){
        long colide = -1;
        
        for(TextBox e: boxen){
            if(x > e.getX() && x < e.getX()+e.getWidth()
            && y > e.getY() && y < e.getY()+e.getHeight() && e.getDepth() != -1)
                    colide = e.getID();
        }
        for(Hole e: holes){
            if(x > e.getX() && x < e.getX()+e.getWidth()
            && y > e.getY() && y < e.getY()+e.getHeight() && e.getDepth() != -1)
                    colide = e.getID();
        }
        for(Constructor e: creators){
            if(x > e.getX() && x < e.getX()+e.getWidth()
            && y > e.getY() && y < e.getY()+e.getHeight() && e.getDepth() != -1)
                    colide = e.getID();
        }
        if(colide != -1)
            return colide;
        
        return colideExtra(x,y);
    }
    protected abstract long colideExtra(int x, int y);
    
    public Hole getHoleByID(long id){
        Hole hole;
        for(Constructor c:creators){
            hole = c.getHoleByID(id);
            if(hole != null)
                return hole;
        }
        
        for(Hole h:holes){
            if(h.getID() == id) return h;
        }
        return null;
    }
    
    @Override
    public void move(int x, int y){
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        shapes.forEach((shape) -> {
            shape.move(x, y);
        });
        images.forEach((image) -> {
            image.move(x, y);
        });
        bounds.forEach((bound) -> {
            bound.move(x, y);
        });
        labels.forEach((label) -> {
            label.move(x, y);
        });
        boxen.forEach((box) -> {
            box.move(x, y);
        });
        holes.forEach((hole) -> {
            hole.move(x, y);
        });
        creators.forEach((creator) -> {
            creator.move(x, y);
        });
        ports.forEach((port) -> {
            port.move(x, y);
        });
        moveExtra(x, y);
    }
    protected abstract void moveExtra(int x, int y);
    
    
    public void setDrawable(boolean d){
        drawable = d;
    }
    public boolean getDrawable(){
        return drawable;
    }
    public void setDragable(boolean d){
        dragable = d;
    }
    public boolean getDragable(){
        return dragable;
    }
    public boolean isRemovableByUser(){
        return deleteable;
    }
    
    @Override
    public void draw(){
        for(int i = maxDepth; i >= 0; i--){
            for (Shape shape : shapes)
                if(shape.getDepth() == i)
                    shape.draw();
            for (Image image : images)
                if(image.getDepth() == i)
                    image.draw();
            for (TextLabel label : labels)
                if(label.getDepth() == i)
                    label.draw();
            for (TextBox box : boxen)
                if(box.getDepth() == i)
                    box.draw();
            for (Hole hole : holes)
                if(hole.getDepth() == i)
                    hole.draw();
            for (Constructor creator : creators)
                if(creator.getDepth() == i)
                    creator.draw();
        }
        drawExtra();
        if(Global.DEBUG_MODE)
            for (Shape bound : bounds)
                bound.draw();
    }
    protected abstract void drawExtra();
    
    public final void focus_force_drag(){
        drag_forced = true;
        if(focus_item != -1){
            depth = 5;
            select_end();
        }
        focus_itemPRE = -1;
        focus_item = -1;
        depth = 2;
        Controller.putOnTop(ID);
        drag_init();
    }
    public final boolean focus_init(){
        if(focus_itemPRE != -1){
            focus_item = focus_itemPRE;
            focus_itemPRE = -1;
            
            for(TextBox box:boxen){
                if(focus_item == box.getID())
                    box.on_selected();
            }
            for(Hole hole:holes){
                if(focus_item == hole.getID())
                    hole.on_selected();
            }
            for(Constructor creator:creators){
                if(focus_item == creator.getID())
                    creator.on_selected();
            }
            
            depth = 4;
            Controller.putOnTop(ID);
            select_init(focus_item);
            return false;
        }else{
            for(Port p:ports)
                if(p.getGender() == Global.PORT_MALE && p.isOcupied())
                    p.undock();
            depth = 4;
            drag_init();
            Controller.putOnTop(ID);
            return true;
        }
    }
    protected abstract void select_init(long ID);
    public final void focus_end(){
        if(focus_item != -1){
            for(TextBox box:boxen){
                if(focus_item == box.getID())
                    box.de_select();
            }
            for(Hole hole:holes){
                if(focus_item == hole.getID())
                    hole.de_select();
            }
            for(Constructor creator:creators){
                if(focus_item == creator.getID())
                    creator.de_select();
            }
            focus_item = -1;
            depth = 5;
            select_end();
        }else{
            for(Port port:ports){
                if(port.isOcupied() && Controller.getElementByID(port.getID()) == null)
                    port.undock();
                
                if(port.isOcupied() && port.getGender()==Global.PORT_MALE && !Port.inBound(port, port.getConnectedPort()))
                    port.undock();
        
                if(port.isOcupied() && port.getGender()==Global.PORT_MALE)
                    Controller.dockingAlign(this, port, Global.QUICK_MOVE);
            }
            depth = 5;
            drag_end();
        }
        //if(drag_forced && (Mouse.getX() < Global.LAYOUT_LEFT || Mouse.getY() < Global.LAYOUT_TOP))
        //    Controller.deleteElement(ID);
    }
    protected abstract void select_end();
    public final boolean loop(int move_x, int move_y){
        if(focus_item != -1){
            for(TextBox box:boxen){
                if(focus_item == box.getID())
                    box.act();
            }
            for(Hole hole:holes){
                if(focus_item == hole.getID())
                    hole.act();
            }
            for(Constructor creator:creators){
                if(focus_item == creator.getID())
                    creator.act();
            }
            
            selected_loop();
            return true;
        }else{
            move(move_x,move_y);
            return drag_loop();
        }
    }
    protected abstract void selected_loop();
    
    //Estos son los metodos que hay que overridear
    public abstract boolean drag_loop(); //Deve devolver true por defecto.
    public abstract void drag_init();
    public abstract void drag_end();
    public abstract void action(int action);
    public abstract void updateEvent(int event, int data1, int data2, String data3);
    public abstract void latWish();
    
    public Port[] getPorts(){
        Port[] ps = new Port[ports.size()];
        for(int i = 0; i < ps.length; i++){
            ps[i] = ports.get(i);
        }
        return ps;
    }
    
    public void delete(){
        for(Hole hole:holes){
            if(hole != null)
                hole.delete();
        }
        for(Port port:ports){
            if(port != null)
                port.delete();
        }
        for(Constructor creator:creators){
            if(creator != null)
                creator.delete();
        }
        
        latWish();
    }
    
    public boolean connectedWith(boolean gender){
        for(Port p:ports){
            if(p.getGender() != gender && p.isOcupied())
                return true;
        }
        return false;
    }
    
    public boolean isComplete(){
            for (TextBox box : boxen)
                if(box.isEmpty())
                    return false;
            for (Hole hole : holes)
                if(!(hole.isAsigned() && hole.getTip().isComplete()))
                    return false;
            for (Constructor creator : creators)
                if(creator.isEmpty())
                    return false;
            return true;
    }
    
    public int getHeight(){return -1;}

    public boolean isMother() {
        for(Port p:ports)
            if(p.getGender() == Global.PORT_MALE && p.isOcupied())
                return false;
        return true;
    }
    
    
    
    public Hole getHole(int n){
        if(n < holes.size()) return holes.get(n); else return null;
    }
    public TextBox getBox(int n){
        if(n < boxen.size()) return boxen.get(n); else return null;
    }
    public Constructor getCreator(int n){
        if(n < creators.size()) return creators.get(n); else return null;
    }
    public Port getPort(int n){
        if(n < ports.size()) return ports.get(n); else return null;
    }
    public TextLabel getLabel(int n){
        if(n < labels.size()) return labels.get(n); else return null;
    }
}
