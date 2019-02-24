package hardscratch.elements;

import hardscratch.Global;
import hardscratch.base.Element;
import hardscratch.base.ElementBase;
import hardscratch.base.shapes.*;
import hardscratch.base.shapes.TextLabel;
import hardscratch.elements.piezes.*;
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
                bounds = new int[]{x,(int)(x+(513*scale)),y,(int)(y+(148*scale))};
                name.setText("DECLARATE VARIABLE");
                name.moveAbsolute((int) (x+(scale*483/2)), (int) (y+(scale*118/2)));
                shapes.add(new Shape_Square(x, y, Global.COLOR_DECLARATOR, scale, 2, 483, 118));
                shapes.add(new Shape_Square(x, y+(int) (118*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (228*scale), y+(int) (118*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (483*scale), y, Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (483*scale), y+(int) (88*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
            break;
            case Global.SUMMON_INICIALIZER:
                bounds = new int[]{x,(int)(x+(483*scale)),y,(int)(y+(118*scale))};
                name.setText("INICIALIZE VARIABLE");
                name.moveAbsolute((int) (x+(scale*280/2)), (int) (y+(scale*118/2)));
                shapes.add(new Shape_Square(x, y+(int) (30*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 58));
                shapes.add(new Shape_Square(x+(int) (30*scale), y, Global.COLOR_DECLARATOR, scale, 2, 250, 118));
            break;
            case Global.SUMMON_EXTRAVAR:
                bounds = new int[]{x,(int)(x+(318*scale)),(int)(y+(30*scale)),(int)(y+(148*scale))};
                name.setText("EXTRA VARIABLE");
                name.moveAbsolute((int) (x+(scale*318/2)), (int) (y+(scale*148/2)));
                shapes.add(new Shape_Square(x+(int) (30*scale), y, Global.COLOR_DECLARATOR, scale, 2, 258, 30));
                shapes.add(new Shape_Square(x+(int) (318*scale), y+(int) (30*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x+(int) (318*scale), y+(int) (118*scale), Global.COLOR_DECLARATOR, scale, 2, 30, 30));
                shapes.add(new Shape_Square(x, y+(int) (30*scale), Global.COLOR_DECLARATOR, scale, 2, 318, 118));
            break;
            case Global.SUMMON_TIP_IN:
                name.setText("IN");  break;
            case Global.SUMMON_TIP_OUT:
                name.setText("OUT"); break;
            case Global.SUMMON_TIP_SIGNAL:
                name.setText("SIGNAL"); break;
            case Global.SUMMON_TIP_CONST:
                name.setText("CONST"); break;
            case Global.SUMMON_CONSTRUCTOR_LITERAL_N:
                name.setText("NUMBER"); break;
            case Global.SUMMON_CONSTRUCTOR_LITERAL_B:
                name.setText("BIT"); break;
            case Global.SUMMON_CONSTRUCTOR_LITERAL_A:
                name.setText("ARRAY"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_I:
                name.setText("VAR INTEGER"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_B:
                name.setText("VAR BIT"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_A:
                name.setText("VAR ARRAY"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_C:
                name.setText("CONSTANT"); break;
            case Global.SUMMON_CONSTRUCTOR_EQUALITY:
                name.setText("EQUALITY"); break;
            case Global.SUMMON_CONSTRUCTOR_CONCAT:
                name.setText("CONCATENATE"); break;
            case Global.SUMMON_CONSTRUCTOR_OPEN:
                name.setText("(open"); break;
            case Global.SUMMON_CONSTRUCTOR_CLOSE:
                name.setText(" close)"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_ADD:
                name.setText("ADDITION"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_SUB:
                name.setText("SUBSTRACT"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_TIM:
                name.setText("MULTIPLY"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_TAK:
                name.setText("DIVIDE"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_AND:
                name.setText("AND"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_OR:
                name.setText("OR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XOR:
                name.setText("XOR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NAND:
                name.setText("NAND"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOR:
                name.setText("NOR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XNOR:
                name.setText("XNOR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOT:
                name.setText("NOT"); break;
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
            case Global.SUMMON_CONSTRUCTOR_LITERAL_N:
            case Global.SUMMON_CONSTRUCTOR_LITERAL_B:
            case Global.SUMMON_CONSTRUCTOR_LITERAL_A:   //CAMBIAR UN POCO LOS COLORES
                bounds = new int[]{x,(int)(x+(155*scale)),y,(int)(y+(44*scale))};
                name.moveAbsolute((int) (x+(scale*155/2)), (int) (y+(scale*44/2)));
                shapes.add(new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, 3, 155, 44));
                shapes.add(new Shape_Square(x+5, y+5, Global.COLOR_HOLE_BACK, 1, 3, 145, 34));
            break;
            case Global.SUMMON_CONSTRUCTOR_VAR_I:
            case Global.SUMMON_CONSTRUCTOR_VAR_B:
            case Global.SUMMON_CONSTRUCTOR_VAR_A:
            case Global.SUMMON_CONSTRUCTOR_VAR_C:   //CAMBIAR UN POCO LOS COLORES
                bounds = new int[]{x,(int)(x+(155*scale)),y,(int)(y+(44*scale))};
                name.moveAbsolute((int) (x+(scale*155/2)), (int) (y+(scale*44/2)));
                shapes.add(new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, 3, 155, 44));
                shapes.add(new Shape_Square(x+5, y+5, Global.COLOR_HOLE_BACK, 1, 3, 145, 34));
            break;
            case Global.SUMMON_CONSTRUCTOR_EQUALITY:
            case Global.SUMMON_CONSTRUCTOR_CONCAT:
            case Global.SUMMON_CONSTRUCTOR_OPEN:
            case Global.SUMMON_CONSTRUCTOR_CLOSE:
            case Global.SUMMON_CONSTRUCTOR_ARITH_ADD:
            case Global.SUMMON_CONSTRUCTOR_ARITH_SUB:
            case Global.SUMMON_CONSTRUCTOR_ARITH_TIM:
            case Global.SUMMON_CONSTRUCTOR_ARITH_TAK:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_AND:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_OR:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XOR:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NAND:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOR:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XNOR:
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOT:   //CAMBIAME IN PÔCO
                bounds = new int[]{x,(int)(x+(155*scale)),y,(int)(y+(44*scale))};
                name.moveAbsolute((int) (x+(scale*155/2)), (int) (y+(scale*44/2)));
                shapes.add(new Shape_Square(x, y, Global.COLOR_BORDER_UNSELECTED, 1, 3, 155, 44));
                shapes.add(new Shape_Square(x+5, y+5, Global.COLOR_HOLE_BACK, 1, 3, 145, 34));
            default:
                //System.out.println("ERROR: "+item); //no es un error realmente
            break;
        }
    }
    
    public int getHeight(){
        return bounds[3]-bounds[2];
    }
    public int getWidth(){
        return bounds[1]-bounds[0];
    }
    public boolean colide(int x, int y){
        return x > bounds[0] && x < bounds[1]
            && y > bounds[2] && y < bounds[3];
    }
    public Element summon(int x, int y){
        switch(item){
            case Global.SUMMON_DECLARATRON: return new Declarator(x-241, y-64);
            case Global.SUMMON_INICIALIZER: return new Inicializer(x-140, y-59);
            case Global.SUMMON_EXTRAVAR: return new ExtraVar(x-159, y-59);
            case Global.SUMMON_TIP_IN: return new Tip(x-77,y-22,Global.TIP_VAR_IN);
            case Global.SUMMON_TIP_OUT: return new Tip(x-77,y-22,Global.TIP_VAR_OUT);
            case Global.SUMMON_TIP_SIGNAL: return new Tip(x-77,y-22,Global.TIP_VAR_SIGNAL);
            case Global.SUMMON_TIP_CONST: return new Tip(x-77,y-22,Global.TIP_VAR_CONST);
            case Global.SUMMON_CONSTRUCTOR_LITERAL_N: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LITERAL_N);
            case Global.SUMMON_CONSTRUCTOR_LITERAL_B: return new Tip(x-25,y-22,Global.TIP_CONSTRUCTOR_LITERAL_B);
            case Global.SUMMON_CONSTRUCTOR_LITERAL_A: return new Tip(x-96,y-22,Global.TIP_CONSTRUCTOR_LITERAL_A);
            case Global.SUMMON_CONSTRUCTOR_VAR_I: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_VAR_I);
            case Global.SUMMON_CONSTRUCTOR_VAR_B: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_VAR_B);
            case Global.SUMMON_CONSTRUCTOR_VAR_A: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_VAR_A);
            case Global.SUMMON_CONSTRUCTOR_VAR_C: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_VAR_C);
            case Global.SUMMON_CONSTRUCTOR_EQUALITY: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_EQUALITY);
            case Global.SUMMON_CONSTRUCTOR_CONCAT: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_CONCAT);
            case Global.SUMMON_CONSTRUCTOR_CLOSE: return new Tip(x-15,y-22,Global.TIP_CONSTRUCTOR_CLOSE);
            case Global.SUMMON_CONSTRUCTOR_OPEN: return new Tip(x-15,y-22,Global.TIP_CONSTRUCTOR_OPEN);
            case Global.SUMMON_CONSTRUCTOR_ARITH_ADD: return new Tip(x-20,y-22,Global.TIP_CONSTRUCTOR_ARITH_ADD);
            case Global.SUMMON_CONSTRUCTOR_ARITH_SUB: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_ARITH_SUB);
            case Global.SUMMON_CONSTRUCTOR_ARITH_TIM: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_ARITH_TIM);
            case Global.SUMMON_CONSTRUCTOR_ARITH_TAK: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_ARITH_TAK);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_AND: return new Tip(x-47,y-22,Global.TIP_CONSTRUCTOR_LOGIC_AND);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_OR: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LOGIC_OR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XOR: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LOGIC_XOR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NAND: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LOGIC_NAND);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOR: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LOGIC_NOR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XNOR: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LOGIC_XNOR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOT: return new Tip(x-77,y-22,Global.TIP_CONSTRUCTOR_LOGIC_NOT);
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
