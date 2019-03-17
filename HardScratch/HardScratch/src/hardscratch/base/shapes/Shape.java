package hardscratch.base.shapes;

import hardscratch.base.ElementBase;

public class Shape extends ElementBase{
    
    protected float color[];
    
    public Shape(int x, int y, float[] color, float scale, int depth) {
        super(x, y, -1, depth, scale);
        
        this.color = color;
    }
    
    public void setColor(float c[]){
        color = c;
    }
    public float[] getColor(){
        return color;
    }
    
    public void draw(){
    }
    
    public void resize(int x, int y){}
}
