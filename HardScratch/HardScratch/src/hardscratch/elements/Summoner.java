package hardscratch.elements;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.ElementBase;
import hardscratch.base.shapes.*;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.Declarator;
import hardscratch.elements.subParts.Tip;
import java.util.ArrayList;

public class Summoner extends ElementBase{
    
    private int item;
    private TextLabel name;
    private ArrayList<Shape_Square> shapes;
    private int[] bounds;
    
    public Summoner(int x, int y, int depth, float scale, int item) {
        super(x, y, depth, scale);
        shapes = new ArrayList<>();
        this.item = item;
        
        name = new TextLabel(0, 0, depth, (float) (0.5*scale), Global.FONT_MONOFONTO, Global.COLOR_WHITE, "", true);
        switch(item){
            case Global.SUMMON_DECLARATRON:
                bounds = new int[]{x,(int)(x+(483*scale)),y,(int)(y+(118*scale))};
                name.setText("DECLARATE VARIABLE");
                name.moveAbsolute((int) (x+(scale*483/2)), (int) (y+(scale*118/2)));
                shapes.add(new Shape_Square(x, y, Global.COLOR_DECLARATOR, scale, 2, 483, 118));
                shapes.add(new Shape_Square(x, y+(int) (118*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (228*scale), y+(int) (118*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (483*scale), y, Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (483*scale), y+(int) (88*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
            break;
            case Global.SUMMON_TIP_IN:
                name.setText("IN");
            break;
            case Global.SUMMON_TIP_OUT:
                name.setText("OUT");
            break;
            case Global.SUMMON_TIP_SIGNAL:
                name.setText("SIGNAL");
            break;
            case Global.SUMMON_TIP_CONST:
                name.setText("CONST");
            break;
        }
        switch(item){
            case Global.SUMMON_TIP_IN:
            case Global.SUMMON_TIP_OUT:
            case Global.SUMMON_TIP_SIGNAL:
            case Global.SUMMON_TIP_CONST:
                bounds = new int[]{x,(int)(x+(155*scale)),y,(int)(y+(44*scale))};
                name.moveAbsolute((int) (x+(scale*155/2)), (int) (y+(scale*44/2)));
                shapes.add(new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, 3, 155, 44));
                shapes.add(new Shape_Square(x+5, y+5, Global.COLOR_HOLE_BACK, 1, 3, 145, 34));
            break;
        }
    }
    
    public boolean colide(int x, int y){
        return x > bounds[0] && x < bounds[1]
            && y > bounds[2] && y < bounds[3];
    }
    public Element summon(int x, int y){
        switch(item){
            case Global.SUMMON_DECLARATRON: return new Declarator(x-241, y-64);
            case Global.SUMMON_TIP_IN: return new Tip(x-77,y-22,Global.TIP_VAR_IN);
            case Global.SUMMON_TIP_OUT: return new Tip(x-77,y-22,Global.TIP_VAR_OUT);
            case Global.SUMMON_TIP_SIGNAL: return new Tip(x-77,y-22,Global.TIP_VAR_SIGNAL);
            case Global.SUMMON_TIP_CONST: return new Tip(x-77,y-22,Global.TIP_VAR_CONST);
        }
        return null;
    }
    
    @Override
    public void move(int x, int y){
        name.move(x, y);
        bounds[0] += x; bounds[1] += x;
        bounds[2] += y; bounds[3] += y;
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        shapes.forEach((shape) -> {
            shape.move(x,y);
        });
    }
    
    public void draw(){
        shapes.forEach((shape) -> {
            shape.draw();
        });
        name.draw();
    }
    
}
