package hardscratch.elements.piezes.Simulation;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.base.shapes.Shape_BorderedBox;
import hardscratch.base.shapes.Shape_Square;

public class SimulateSwitch extends Simulated{

    private int state;
    
    public SimulateSwitch(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        
        state = SIM_POWER_OFF;
        
        addShape(new Shape_BorderedBox(0, 0, COLOR_CIRCUIT_DKGREEN, COLOR_CIRCUIT_DKBROWN, 1, 3, unit, (int) (unit*2.5f), 5), 0, 0);
        addShape(new Shape_Square(0, 0, COLOR_CIRCUIT_DKGRAY, scale, 2, unit-10, unit), 5, (int) ((unit*1.5f)-5));
        
        addBoundingBox(0, unit, 0, (int) (unit*2.5f), 1);
    }

    
    @Override
    protected void select_init(long ID) {
    }

    @Override
    protected void select_end() {
    }

    @Override
    public void action(int action) {
        if(action == 1){
            if(state == SIM_POWER_ON){
                state = SIM_POWER_OFF;
                SOUND_SWITCH_DOWN.play();
            }else{
                state = SIM_POWER_ON;
                SOUND_SWITCH_UP.play();
            }
        }
        shapes.get(1).move(0, (int) (((unit*1.5)-10)*(state==SIM_POWER_OFF?1:-1)));
        
        
        Controller.simulationChange();
    }

    @Override
    public String getValue() {
        return state == SIM_POWER_ON?"ON":"OFF";
    }

    @Override
    public void setValue(String value) {
        if((value.equals("ON") && state == SIM_POWER_OFF) || (value.equals("OFF") && state == SIM_POWER_ON)) action(1);
    }
    
}
