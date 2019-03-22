package hardscratch.elements.piezes.Simulation;

import static hardscratch.Global.*;
import hardscratch.base.shapes.Image;

public class SimulatedBCD extends Simulated{

    private int state;
    private final Image image;
    
    public SimulatedBCD(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = 10;
        
        image = new Image(0, 0, 2, TEXTURE_BCD[state], (unit*2.2f)/200f, COLOR_WAITON);
        addImage(image, 0, 0);
    }

    @Override
    public void action(int action) {
        if(action <= 10){
            image.setTexture(TEXTURE_BCD[action]);
            state = action;
        }
    }

    @Override
    public String getValue() {
        return String.valueOf(state);
    }

    @Override
    public void setValue(String value) {
        if(value.contains("U")){
            action(10);
        }else if(value.contains("'")){
            if(value.equals("'1'"))
                action(1);
            else if(value.equals("'0'"))
                action(0);
        }else if(value.contains("\"")){
            action(Integer.parseInt(value.substring(1, value.length()-1),2));
        }else{
            action(Integer.parseInt(value));
        }
    }
    
}
