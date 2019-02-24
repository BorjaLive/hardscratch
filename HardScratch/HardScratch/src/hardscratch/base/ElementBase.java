package hardscratch.base;

import hardscratch.Controller;

public abstract class ElementBase {
    protected Dot position;
    
    protected int depth, rot;
    protected float scale;
    protected int ID;
    
    public ElementBase(int x, int y, int depth, float scale){
        position = new Dot(x, y);
        this.depth = depth;
        this.scale = scale;
        this.rot = 0;
        
        ID = Controller.generateID();
    }
    
    public void move(int x, int y){
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
    }
    public void moveAbsolute(int x, int y){
        position.setCords(x, y);
    }
    
    public void setDepth(int dep){
        if(dep < 0)
            dep = 0;
        depth = dep;
    }
    public int getDepth(){
        return depth;
    }
    public void setRot(int r){
        setRotAbsolute(r+rot);
    }
    public void setRotAbsolute(int r){
        rot = r;
    }
    public int getRot(){
        return rot;
    }
    public void setScale(float s){
        scale = s;
    }
    public float getScale(){
        return scale;
    }
    public int getX(){
        return position.getCordX();
    }
    public int getY(){
        return position.getCordY();
    }
    
    public int getID(){
        return ID;
    }
    
    public abstract void draw();
}
