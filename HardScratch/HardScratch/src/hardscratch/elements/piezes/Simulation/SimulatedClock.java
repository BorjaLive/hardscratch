package hardscratch.elements.piezes.Simulation;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.shapes.Shape_BorderedBox;

public class SimulatedClock extends Simulated{
    
    private int state;
    private Shape_BorderedBox shape;
    
    public SimulatedClock(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = Global.SIM_LED_ON;
        
        shape = new Shape_BorderedBox(0, 0, Global.COLOR_CIRCUIT_BROWN, Global.COLOR_CIRCUIT_DKBROWN, 1, 3, (int) (unit*1.5f), (int) unit, 8);
        addShape(shape,0, 0);
    }

    @Override
    public void action(int action) {
        switch(action){
            case Global.EVENT_TURN_ON:
                state = Global.SIM_LED_ON;
                shape.changeBackColor(Global.COLOR_CIRCUIT_BROWN);
            break;
            case Global.EVENT_TURN_OFF:
                state = Global.SIM_LED_OFF;
                shape.changeBackColor(Global.COLOR_CIRCUIT_DKBROWN);
            break;
        }
        Controller.simulationChange();
    }

    @Override
    public String getValue() {
        return state == Global.SIM_LED_ON?"ON":"OFF";
    }

    @Override
    public void setValue(String value) {
        if(value.equals("ON"))
            action(Global.EVENT_TURN_ON);
        else
            action(Global.EVENT_TURN_OFF);
    }
}
