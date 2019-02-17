package hardscratch.base;

import hardscratch.Global;

public final class Dot {
    int cord_x, cord_y;
    float rel_x, rel_y;
    
    public Dot(int cord_x, int cord_y){
        setCords(cord_x, cord_y);
    }
    public Dot(float rel_x, float rel_y, boolean relative){
        setRel(cord_x, cord_y);
    }
    
    public void setCords(int x, int y){
        cord_x = x;
        cord_y = y;
        rel_x = calculateRelativeX(x);
        rel_y = calculateRelativeY(y);
    }
    public void setRel(float x, float y){
        rel_x = x;
        rel_y = y;
        cord_x = calculateCordsX(x);
        cord_y = calculateCordsY(y);
    }
    
    public int getCordX(){return cord_x;}
    public int getCordY(){return cord_y;}
    public float getRelX(){return rel_x;}
    public float getRelY(){return rel_y;}
    
    public static float calculateRelativeX(int x){
        return  (float)(x-(Global.WINDOW_WIDTH/2))/(Global.WINDOW_WIDTH/2);
    }
    public static float calculateRelativeY(int y){
        return  -(float)(y-(Global.WINDOW_HEIGHT/2))/(Global.WINDOW_HEIGHT/2);
    }
    public static int calculateCordsX(float x){
        return  (int) ((x/2)*Global.WINDOW_WIDTH)+(Global.WINDOW_WIDTH/2);
    }
    public static int calculateCordsY(float y){
        return  (int) ((-y/2)*Global.WINDOW_HEIGHT)+(Global.WINDOW_HEIGHT/2);
    }
    
    public static float relativeSizeX(int size){
        return (size*2)/(float)Global.WINDOW_WIDTH;
    }
    public static float relativeSizeY(int size){
        return -(size*2)/(float)Global.WINDOW_HEIGHT;
    }
}
