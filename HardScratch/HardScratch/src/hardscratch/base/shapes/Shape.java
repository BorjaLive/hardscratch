package hardscratch.base.shapes;

import hardscratch.base.ElementBase;

public class Shape extends ElementBase{
    
    protected int color[];
    
    public Shape(int x, int y, int[] color, float scale, int depth) {
        super(x, y, depth, scale);
        
        this.color = color;
    }
    
    public void setColor(int c[]){
        color = c;
    }
    public int[] getColor(){
        return color;
    }
    
    public void draw(){
    }
}
