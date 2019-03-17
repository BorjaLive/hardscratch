package hardscratch.elements.piezes;

import hardscratch.elements.piezes.Design.*;
import hardscratch.Global;
import hardscratch.backend.Variable;
import hardscratch.base.Element;
import hardscratch.base.ElementBase;
import hardscratch.base.shapes.*;
import hardscratch.base.shapes.TextLabel;

public class Summoner extends ElementBase{
    
    private int item;
    private TextLabel name;
    private Shape_BorderedBox shape;
    private int[] bounds;
    private Variable var;
    
    public Summoner(int x, int y, int depth, float scale, int item) {
        super(x, y, -2, depth, scale);
        this.item = item;
        
        shape = new Shape_BorderedBox(x, y, Global.COLOR_SUMMONER_BACK, Global.COLOR_SUMMONER_BORDER, scale, depth, 350, 50, 5);
        name = new TextLabel(x+175, y+25, depth, 0.3f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "", true);
        bounds = new int[]{x,(int)(x+(350*scale)),y,(int)(y+(50*scale))};
        name.setScretch(1.5f);
        
        switch(item){
            case Global.SUMMON_DECLARATRON:             name.setText("DECLARATE VARIABLE");     shape.setPalete(Global.COLOR_DECLARATOR); break;
            case Global.SUMMON_INICIALIZER:             name.setText("INICIALIZE VARIABLE");    shape.setPalete(Global.COLOR_INICIALIZER); break;
            case Global.SUMMON_EXTRAVAR:                name.setText("EXTRA VARIABLE");         shape.setPalete(Global.COLOR_EXTRAVAR); break;
            case Global.SUMMON_TIP_IN:                  name.setText("IN");  break;
            case Global.SUMMON_TIP_OUT:                 name.setText("OUT"); break;// Poner color a todo esto
            case Global.SUMMON_TIP_SIGNAL:              name.setText("SIGNAL"); break;
            case Global.SUMMON_TIP_CONST:               name.setText("CONST"); break;
            case Global.SUMMON_CONSTRUCTOR_LITERAL_N:   name.setText("NUMBER"); break;
            case Global.SUMMON_CONSTRUCTOR_LITERAL_B:   name.setText("BIT"); break;
            case Global.SUMMON_CONSTRUCTOR_LITERAL_A:   name.setText("ARRAY"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_I:       name.setText("VAR INTEGER"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_B:       name.setText("VAR BIT"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_A:       name.setText("VAR ARRAY"); break;
            case Global.SUMMON_CONSTRUCTOR_VAR_C:       name.setText("CONSTANT"); break;
            case Global.SUMMON_CONSTRUCTOR_EQUALITY:    name.setText("EQUALITY"); break;
            case Global.SUMMON_CONSTRUCTOR_CONCAT:      name.setText("CONCATENATE"); break;
            case Global.SUMMON_CONSTRUCTOR_OPEN:        name.setText("(open"); break;
            case Global.SUMMON_CONSTRUCTOR_CLOSE:       name.setText(" close)"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_ADD:   name.setText("ADDITION"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_SUB:   name.setText("SUBSTRACT"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_TIM:   name.setText("MULTIPLY"); break;
            case Global.SUMMON_CONSTRUCTOR_ARITH_TAK:   name.setText("DIVIDE"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_AND:   name.setText("AND"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_OR:    name.setText("OR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XOR:   name.setText("XOR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NAND:  name.setText("NAND"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOR:   name.setText("NOR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XNOR:  name.setText("XNOR"); break;
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOT:   name.setText("NOT"); break;
            case Global.SUMMON_CONVERTER:               name.setText("CONVERT VARIABLES");      shape.setPalete(Global.COLOR_CONVERTER); break;
            case Global.SUMMON_ASIGNATOR:               name.setText("ASIGN VARIABLES");        shape.setPalete(Global.COLOR_ASIGNATOR); break;
            case Global.SUMMON_TIP_INT:                 name.setText("INTEGER"); break;
            case Global.SUMMON_TIP_BIT:                 name.setText("BIT"); break;
            case Global.SUMMON_TIP_ARRAY:               name.setText("BIT ARRAY"); break;
            case Global.SUMMON_SETIF:                   name.setText("CONDICIONATE ASIGNATION");shape.setPalete(Global.COLOR_SETIF); break;
            case Global.SUMMON_SETSWITCH:               name.setText("SWITCH ASIGNATION");      shape.setPalete(Global.COLOR_SETSWITCH); break;
            case Global.SUMMON_SEQUENTIAL:              name.setText("SEQUENTIAL BLOCK");       shape.setPalete(Global.COLOR_SEQUENTIAL); break;
            case Global.SUMMON_ASIGNATOR_SEQ:           name.setText("SEQUENTIAL ASIGNATION");  shape.setPalete(Global.COLOR_ASIGNATOR_SEQ); break;
            case Global.SUMMON_IFTHEN:                  name.setText("IF THEN ELSE");           shape.setPalete(Global.COLOR_IFTHEN); break;
            case Global.SUMMON_SWITCHCASE:              name.setText("SWITCH CASE");            shape.setPalete(Global.COLOR_SWITCHCASE); break;
            case Global.SUMMON_WAITFOR:                 name.setText("WAIT FOR");               shape.setPalete(Global.COLOR_WAITFOR); break;
            case Global.SUMMON_WAITON:                  name.setText("WAIT ON VARIABLE");       shape.setPalete(Global.COLOR_WAITON); break;
            case Global.SUMMON_FORNEXT:                 name.setText("DO FOR NEXT");            shape.setPalete(Global.COLOR_FORNEXT); break;
            case Global.SUMMON_TIP_RISSING:             name.setText("RISING EDGE"); break;
            case Global.SUMMON_TIP_LOWERING:            name.setText("LOWERING EDGE"); break;
        }
    }
    public void setVar(Variable v){
        if(v == null) return;
        var = v;
        switch (item) {
            case Global.SUMMON_VAR:
                name.setText(v.name);
            break;
            case Global.SUMMON_VAR_SUBARRAY:
                String nom = v.name;
                String spacer = "";
                if(nom.length() > 6) nom = nom.substring(0, 6);
                if(nom.length() < 6)
                    for(int i = nom.length(); i < 8; i++)
                        spacer += "";
                name.setText(nom+spacer+"[]");
            break;
            case Global.SUMMON_VAR_CLOCK:
                name.setText("CLOCK TICK "+v.name);
            break;
        }
        switch(v.inout){
            case Global.TIP_VAR_IN:     shape.setPalete(Global.COLOR_VAR_IN); break;
            case Global.TIP_VAR_OUT:    shape.setPalete(Global.COLOR_VAR_OUT); break;
            case Global.TIP_VAR_SIGNAL: shape.setPalete(Global.COLOR_VAR_SIGNAL); break;
            case Global.TIP_VAR_CONST:  shape.setPalete(Global.COLOR_VAR_CONST); break;
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
            case Global.SUMMON_TIP_IN:                  return new Tip(Global.TIP_VAR_IN);
            case Global.SUMMON_TIP_OUT:                 return new Tip(Global.TIP_VAR_OUT);
            case Global.SUMMON_TIP_SIGNAL:              return new Tip(Global.TIP_VAR_SIGNAL);
            case Global.SUMMON_TIP_CONST:               return new Tip(Global.TIP_VAR_CONST);
            case Global.SUMMON_CONSTRUCTOR_LITERAL_N:   return new Tip(Global.TIP_CONSTRUCTOR_LITERAL_N);
            case Global.SUMMON_CONSTRUCTOR_LITERAL_B:   return new Tip(Global.TIP_CONSTRUCTOR_LITERAL_B);
            case Global.SUMMON_CONSTRUCTOR_LITERAL_A:   return new Tip(Global.TIP_CONSTRUCTOR_LITERAL_A);
            //case Global.SUMMON_CONSTRUCTOR_VAR_I:       return new Tip(Global.TIP_CONSTRUCTOR_VAR_I);
            //case Global.SUMMON_CONSTRUCTOR_VAR_B:       return new Tip(Global.TIP_CONSTRUCTOR_VAR_B);
            //case Global.SUMMON_CONSTRUCTOR_VAR_A:       return new Tip(Global.TIP_CONSTRUCTOR_VAR_A);
            //case Global.SUMMON_CONSTRUCTOR_VAR_C:       return new Tip(Global.TIP_CONSTRUCTOR_VAR_C);
            case Global.SUMMON_CONSTRUCTOR_EQUALITY:    return new Tip(Global.TIP_CONSTRUCTOR_EQUALITY);
            case Global.SUMMON_CONSTRUCTOR_CONCAT:      return new Tip(Global.TIP_CONSTRUCTOR_CONCAT);
            case Global.SUMMON_CONSTRUCTOR_CLOSE:       return new Tip(Global.TIP_CONSTRUCTOR_CLOSE);
            case Global.SUMMON_CONSTRUCTOR_OPEN:        return new Tip(Global.TIP_CONSTRUCTOR_OPEN);
            case Global.SUMMON_CONSTRUCTOR_ARITH_ADD:   return new Tip(Global.TIP_CONSTRUCTOR_ARITH_ADD);
            case Global.SUMMON_CONSTRUCTOR_ARITH_SUB:   return new Tip(Global.TIP_CONSTRUCTOR_ARITH_SUB);
            case Global.SUMMON_CONSTRUCTOR_ARITH_TIM:   return new Tip(Global.TIP_CONSTRUCTOR_ARITH_TIM);
            case Global.SUMMON_CONSTRUCTOR_ARITH_TAK:   return new Tip(Global.TIP_CONSTRUCTOR_ARITH_TAK);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_AND:   return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_AND);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_OR:    return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_OR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XOR:   return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_XOR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NAND:  return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_NAND);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOR:   return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_NOR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_XNOR:  return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_XNOR);
            case Global.SUMMON_CONSTRUCTOR_LOGIC_NOT:   return new Tip(Global.TIP_CONSTRUCTOR_LOGIC_NOT);
            case Global.SUMMON_TIP_INT:                 return new Tip(Global.TIP_VAR_INT);
            case Global.SUMMON_TIP_BIT:                 return new Tip(Global.TIP_VAR_BIT);
            case Global.SUMMON_TIP_ARRAY:               return new Tip(Global.TIP_VAR_ARRAY);
            case Global.SUMMON_TIP_RISSING:             return new Tip(Global.TIP_EDGE_RISING);
            case Global.SUMMON_TIP_LOWERING:            return new Tip(Global.TIP_EDGE_LOWERING);
            case Global.SUMMON_VAR:                     return new Tip(Global.TIP_VAR).setVar(var);
            case Global.SUMMON_VAR_SUBARRAY:            return new Tip(Global.TIP_VAR_SUBARRAY).setVar(var);
            case Global.SUMMON_VAR_CLOCK:               return new Tip(Global.TIP_VAR_CLOCK).setVar(var);
            case Global.SUMMON_DECLARATRON:             return new Declarator();
            case Global.SUMMON_INICIALIZER:             return new Inicializer();
            case Global.SUMMON_EXTRAVAR:                return new ExtraVar();
            case Global.SUMMON_CONVERTER:               return new Converter();
            case Global.SUMMON_ASIGNATOR:               return new Asignator();
            case Global.SUMMON_SETIF:                   return new SetIf();
            case Global.SUMMON_SETSWITCH:               return new SetSwitch();
            case Global.SUMMON_SEQUENTIAL:              return new Sequential();
            case Global.SUMMON_ASIGNATOR_SEQ:           return new AsignatorSEQ();
            case Global.SUMMON_IFTHEN:                  return new IfThen();
            case Global.SUMMON_SWITCHCASE:              return new SwitchCase();
            case Global.SUMMON_WAITFOR:                 return new WaitFor();
            case Global.SUMMON_WAITON:                  return new WaitOn();
            case Global.SUMMON_FORNEXT:                 return new ForNext();
            default: System.err.println("\n\n Hast comentado mas de la cuenta.\n\n");
        }
        return null;
    }
    
    @Override
    public void move(int x, int y){
        name.move(x, y);
        bounds[0] += x; bounds[1] += x;
        bounds[2] += y; bounds[3] += y;
        moveAbsolute(position.getCordX()+x, position.getCordY()+y);
        shape.move(x, y);
    }
    
    @Override
    public void draw(){
        shape.draw();
        name.draw();
    }
    public int getItem(){
        return item;
    }
    
}
