package hardscratch.base.shapes;

public class Shape_BorderedBox extends Shape{
    
    private Shape_Square borderbox, backbox;
    private int border;
    
    public Shape_BorderedBox(int x, int y, float[] backColor, float[] borderColor, float scale, int depth, int width, int height, int border) {
        super(x, y, backColor, scale, depth);
        this.border = border;
        
        borderbox = new Shape_Square(x, y, borderColor, scale, depth, width, height);
        backbox = new Shape_Square((int)(x+(scale*border)),(int)(y+(scale*border)),backColor,scale,depth,width-(border*2),height-(border*2));
    }
    
    @Override
    public void moveAbsolute(int x, int y){
        position.setCords(x, y);
        borderbox.moveAbsolute(x, y);
        backbox.moveAbsolute(x+border, y+border);
    }
    
    public void changeBorderColor(float[] color){
        borderbox.setColor(color);
    }
    public void changeBackColor(float[] color){
        backbox.setColor(color);
    }
    public void setPalete(float[] base){
        backbox.setColor(base);
        borderbox.setColor(new float[]{base[0]*0.5f,base[1]*0.5f,base[2]*0.5f});
    }
    
    @Override
    public void draw(){
        borderbox.draw();
        backbox.draw();
    }
}
