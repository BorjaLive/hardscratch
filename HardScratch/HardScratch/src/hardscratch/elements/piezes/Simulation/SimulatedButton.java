package hardscratch.elements.piezes.Simulation;

import hardscratch.Global;
import hardscratch.base.shapes.Shape_BorderedBox;


public class SimulatedButton extends Simulated{

    public int state;
    private Shape_BorderedBox shape;
    
    public SimulatedButton(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = Global.SIM_BUTTON_OFF;
        
        shape = new Shape_BorderedBox(0, 0, Global.COLOR_CIRCUIT_DKGRAY, Global.COLOR_CIRCUIT_DKBROWN, 1, 3, unit, unit, 8);
        addShape(shape, 0, 0);
        addBoundingBox(0, unit,  0, unit, -1);
    }

    @Override
    protected void select_init(long ID) {
        shape.changeBackColor(Global.COLOR_CIRCUIT_BROWN);
        state = Global.SIM_BUTTON_ON;
    }

    @Override
    protected void select_end() {
        shape.changeBackColor(Global.COLOR_CIRCUIT_DKGRAY);
        state = Global.SIM_BUTTON_OFF;
    }

    @Override
    public void action(int action) {
    }
    
}