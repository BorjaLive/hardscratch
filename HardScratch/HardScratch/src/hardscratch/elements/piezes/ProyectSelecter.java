package hardscratch.elements.piezes;

import static hardscratch.Global.*;
import hardscratch.base.ElementBase;
import hardscratch.base.shapes.Shape_BorderedBox;
import hardscratch.base.shapes.TextLabel;

public class ProyectSelecter extends ElementBase{

    String name;
    private TextLabel text;
    private Shape_BorderedBox shape;
    private int[] bounds;
    
    public ProyectSelecter(int x, int y, String name, int width, int height, float Fontscale) {
        super(x, y, -1, 3, 1);
        this.name = name;
        
        shape = new Shape_BorderedBox(x, y, COLOR_SUMMONER_BACK, COLOR_SUMMONER_BORDER, scale, depth, width, height, width/70);
        text = new TextLabel(x+(width/2), y+(height/2), depth, Fontscale, FONT_MONOFONTO, COLOR_WHITE, name, true);
        bounds = new int[]{x,x+width,y,y+height};
        text.setScretch(1.5f);
        
    }

    public String getName(){
        return name;
    }
    public int getHeight(){
        return bounds[3]-bounds[2];
    }
    
    public boolean colide(int x, int y){
        return x > bounds[0] && x < bounds[1]
            && y > bounds[2] && y < bounds[3];
    }
    
    @Override
    public void move(int x, int y){
        text.move(x, y);
        bounds[0] += x; bounds[1] += x;
        bounds[2] += y; bounds[3] += y;
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        shape.move(x, y);
    }
    
    @Override
    public void draw() {
        shape.draw();
        text.draw();
    }

    public void select(){
        shape.changeBorderColor(COLOR_BORDER_SELECTED);
    }
    public void deSelect() {
        shape.changeBorderColor(COLOR_SUMMONER_BORDER);
    }
    
    
}
