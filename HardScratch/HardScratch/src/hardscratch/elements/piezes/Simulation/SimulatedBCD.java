package hardscratch.elements.piezes.Simulation;

import hardscratch.Global;
import hardscratch.base.shapes.Image;

public class SimulatedBCD extends Simulated{

    private int state;
    private final Image image;
    
    public SimulatedBCD(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = 10;
        
        image = new Image(0, 0, 2, Global.TEXTURE_BCD[state], (unit*2.2f)/200f, Global.COLOR_WAITON);
        addImage(image, 0, 0);
    }

    @Override
    public void action(int action) {
        image.setTexture(Global.TEXTURE_BCD[action]);
        state = action;
    }
    
}
