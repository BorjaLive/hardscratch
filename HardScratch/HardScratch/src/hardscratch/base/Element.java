package hardscratch.base;

import hardscratch.Global;
import hardscratch.base.shapes.*;
import java.util.ArrayList;

public abstract class Element extends ElementBase{
    
    private boolean drawable, dragable;
    private int maxDepth;
    private int boxSelected;
    
    private ArrayList<Shape> shapes;
    private ArrayList<Image> images;
    private ArrayList<TextLabel> labels;
    private ArrayList<TextBox> boxen;
    private ArrayList<Shape_Square> bounds;
    private ArrayList<int[]> boundingBoxes;
    
    public Element(int x, int y, int depth, boolean drawable, boolean dragable) {
        super(x, y, depth, 1);
        
        this.drawable = drawable;
        this.dragable = dragable;
        shapes = new ArrayList<>();
        images = new ArrayList<>();
        bounds = new ArrayList<>();
        labels = new ArrayList<>();
        boxen = new ArrayList<>();
        boundingBoxes = new ArrayList<>();
        maxDepth = 0;
        boxSelected = -1;
    }
    
    public final void addShape(Shape shape, int x, int y){
        shape.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        shapes.add(shape);
        if(shape.getDepth() > maxDepth)
            maxDepth = shape.getDepth();
    }
    public final  void addImage(Image image, int x, int y){
        image.moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        images.add(image);
        if(image.getDepth() > maxDepth)
            maxDepth = image.getDepth();
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
    public final  void addBoundingBox(int x1, int x2, int y1, int y2, int action){
        boundingBoxes.add(new int[]{x1,x2,y1,y2,action});
        Shape_Square bound = new Shape_Square(position.getCordX()+x1, position.getCordY()+y1,Global.COLOR_WHITE,1,0,x2-x1,y2-y1);
        bounds.add(bound);
    }
    
    public final  void changeColorShape(int n, int[] color){
        shapes.get(n).setColor(color);
    }
    
    public final boolean colide(int x, int y){
        for(int i = 0; i < boxen.size(); i++){
            TextBox box = boxen.get(i);
            if(x > box.getX() && x < box.getX()+box.getWidth()
            && y > box.getY() && y < box.getY()+box.getHeight()){
                boxSelected = i;
                return true;
            }
        }
        
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
    }
    
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
        }
        if(Global.DEBUG_MODE)
            for (Shape bound : bounds)
                bound.draw();
    }
    
    public final boolean focus_init(){
        if(boxSelected == -1){
            drag_init();
            return true;
        }else{
            boxen.get(boxSelected).on_selected();
            return false;
        }
    }
    public final void focus_end(){
        if(boxSelected == -1){
            drag_end();
        }else{
            boxen.get(boxSelected).de_select();
            boxSelected = -1;
        }
    }
    public final boolean loop(int move_x, int move_y){
        if(boxSelected == -1){
            move(move_x,move_y);
            return drag_loop();
        }else{
            boxen.get(boxSelected).act();
            return true;
        }
    }
    
    //Estos son los metodos que hay que overridear
    public abstract boolean drag_loop(); //Deve devolver true por defecto.
    public abstract void drag_init();
    public abstract void drag_end();
    public abstract void action(int action);
}
