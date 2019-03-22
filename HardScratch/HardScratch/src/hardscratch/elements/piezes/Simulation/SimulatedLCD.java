package hardscratch.elements.piezes.Simulation;

import hardscratch.Global;
import hardscratch.base.shapes.Shape_BorderedBox;
import hardscratch.base.shapes.TextLabel;


public class SimulatedLCD extends Simulated{
    
    private String state;
    private boolean backlight;
    private TextLabel label;
    private Shape_BorderedBox shape;

    public SimulatedLCD(int x, int y, int unit, String name) {
        super(x, y, unit, name);
        state = "";
        backlight = false;
        
        shape = new Shape_BorderedBox(0, 0, Global.COLOR_CIRCUIT_LCD_OFF, Global.COLOR_CIRCUIT_DKBROWN, 1, 5, unit*7, unit*2, 5);
        addShape(shape , 0, 0);
        label = new TextLabel(0, 0, depth, unit/80f, Global.FONT_LCD, Global.COLOR_CIRCUIT_LCD_LETTER, state, true);
        addLabel(label, (int) (unit*3.5f),  unit);
    }

    @Override
    public void action(int action) {
    }
    
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        switch(event){
            case Global.EVENT_TURN_ON:
                shape.changeBackColor(Global.COLOR_CIRCUIT_LCD_ON);
                backlight = false;
            break;
            case Global.EVENT_TURN_OFF:
                shape.changeBackColor(Global.COLOR_CIRCUIT_LCD_OFF);
                backlight = true;
            break;
            default:
                state = data3;
            
        }
        
    }

    @Override
    public String getValue() {
        return label.getText();
    }

    @Override
    public void setValue(String value) {
        label.setText(value);
        
        if(value.isEmpty())
            updateEvent(Global.EVENT_TURN_OFF, 0, 0 ,"");
        else
            updateEvent(Global.EVENT_TURN_ON, 0, 0 ,"");
    }
    
    
}
