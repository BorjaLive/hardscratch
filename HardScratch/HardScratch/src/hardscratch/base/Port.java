package hardscratch.base;

public class Port {
    
    private int x1, x2, y1, y2;
    private final int type;
    private final boolean gender;
    Element dock;
    Port port;
    
    public Port(int x1, int x2, int y1, int y2, boolean gender, int type){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.gender = gender;
        this.type = type;
        dock = null; port = null;
    }
    
    public boolean isOcupied(){
        return dock != null && port != null;
    }
    public Element getDock(){
        return dock;
    }
    public int getX1(){return x1;}
    public int getX2(){return x2;}
    public int getY1(){return y1;}
    public int getY2(){return y2;}
    public boolean getGender(){
        return gender;
    }
    public int getType(){
        return type;
    }
    
    public void move(int x, int y){
        x1 += x;
        x2 += x;
        y1 += y;
        y2 += y;
        if(getGender() && isOcupied())
            dock.move(x, y);
    }
    public int getID(){ //La id de lo que esta conectada
        if(isOcupied())
            return dock.getID();
        else
            return -1;
    }
    public Port getConnectedPort(){
        if(isOcupied())
            return port;
        else
            return null;
    }
    
    public void dock(Element e, Port p){
        dock = e;
        port = p;
    }
    public void undock(){
        dock = null;
        if(port != null && port.isOcupied())
           port.undock();
        port = null;
    }
    
    
    public static boolean couple(Port p1, Port p2){
        return  !(p1.isOcupied() || p2.isOcupied()) &&
                 (p1.getGender() != p2.getGender()) &&
                 (p1.getType() == p2.getType()) &&
                 inBound(p1, p2);
    }
    public static boolean inBound(Port p1, Port p2){
        return p1.getX1() < p2.getX2() && p1.getX2() > p2.getX1() &&
               p1.getY1() < p2.getY2() && p1.getY2() > p2.getY1();
    }
    
}
