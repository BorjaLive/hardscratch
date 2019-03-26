package hardscratch.elements.piezes.Simulation;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.base.shapes.Shape_BorderedBox;

public class SimulatedClock extends Simulated{
    
    private int state;
    private Shape_BorderedBox shape;
    
    public SimulatedClock(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = SIM_LED_OFF;
        
        shape = new Shape_BorderedBox(0, 0, COLOR_CIRCUIT_BROWN, COLOR_CIRCUIT_BROWN, 1, 3, (int) (unit*1.5f), (int) unit, 8);
        addShape(shape,0, 0);
        addBoundingBox(0, (int) (unit*1.5f),  0, (int) (unit*1.5f), EVENT_TOGGLE);
    }

    @Override
    public void action(int action) {
        switch(action){
            case EVENT_TOGGLE:
                SOUND_KEY_PRESS.play();
                if(state == SIM_LED_ON){
                    state = SIM_LED_OFF;
                    shape.changeBackColor(COLOR_CIRCUIT_BROWN);
                }else{
                    state = SIM_LED_ON;
                    shape.changeBackColor(COLOR_CIRCUIT_DKBROWN);
                }
                
            break;
        }
        Controller.simulationChange();
    }

    @Override
    public String getValue() {
        return state == SIM_LED_ON?"ON":"OFF";
    }

    @Override
    public void setValue(String value) {
        action(EVENT_TOGGLE);
    }
}
