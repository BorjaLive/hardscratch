package hardscratch.elements.piezes.Simulation;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.base.Sound;
import hardscratch.base.SoundPlayer;
import hardscratch.base.shapes.Shape_BorderedBox;


public class SimulatedButton extends Simulated{

    public int state;
    private Shape_BorderedBox shape;
    
    public SimulatedButton(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = SIM_BUTTON_OFF;
        setMute(true);
        
        shape = new Shape_BorderedBox(0, 0, COLOR_CIRCUIT_DKGRAY, COLOR_CIRCUIT_DKBROWN, 1, 3, unit, unit, 8);
        addShape(shape, 0, 0);
        addBoundingBox(0, unit,  0, unit, -1);
    }

    @Override
    protected void select_init(long ID) {
        shape.changeBackColor(COLOR_CIRCUIT_BROWN);
        state = SIM_BUTTON_ON;
        SoundPlayer.play(SOUND_BUTTON_UP);
                    
        action(1);
    }

    @Override
    protected void select_end() {
        shape.changeBackColor(COLOR_CIRCUIT_DKGRAY);
        state = SIM_BUTTON_OFF;
        SoundPlayer.play(SOUND_BUTTON_DOWN);
        action(1);
    }

    @Override
    public void action(int action) {
        Controller.simulationChange();
    }

    @Override
    public String getValue() {
        return state == SIM_BUTTON_ON?"ON":"OFF";
    }

    @Override
    public void setValue(String value) {
        if(value.equals("ON"))
            select_init(ID);
        else select_end();
    }
    
}
