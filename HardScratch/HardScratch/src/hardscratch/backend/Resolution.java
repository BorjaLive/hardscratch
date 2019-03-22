package hardscratch.backend;

public class Resolution {
    
    public int width;
    public int height;
    
    public Resolution(){
        width = 0;
        height = 0;
    }
    public Resolution(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    public String name(){
        return width+"x"+height;
    }
}
