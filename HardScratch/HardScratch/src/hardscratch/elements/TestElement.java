package hardscratch.elements;

import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.base.shapes.*;

public class TestElement extends Element{
    
    public TestElement(int x, int y, int depth, boolean drawable, boolean dragable) {
        super(x, y, depth, drawable, dragable);
        
        addShape(new Shape_Square(0,0,Global.COLOR_AQUA,1,1,50,50),0,0);
        addShape(new Shape_Square(0,0,Global.COLOR_RED,1,0,30,30),10,10);
        addImage(new Image(0,0,2,Global.TEXTURE_TEST, 0.2f, Global.COLOR_WHITE), 50, 0);
        addImage(new Image(0,0,2,Global.TEXTURE_CAT, 0.25f, Global.COLOR_WHITE), -50, 50);
        addLabel(new TextLabel(100,100,0,1,Global.FONT_TEST,Global.COLOR_WHITE,"Hola mundo!"), 0, -30);
        addTextBox(new TextBox(100,100,0,1,Global.FONT_TEST,"Escribeme",Global.COLOR_WHITE,Global.COLOR_RED,Global.COLOR_AQUA,Global.COLOR_RED,Global.COLOR_PURPLE,250,90,10), 0, 150);
        addBoundingBox(10, 40, 10, 40, -1);
        addBoundingBox(-50, 50, 50, 125, 1);
    }
    
    
    @Override
    public void drag_init(){
        SoundPlayer.play(Global.SOUND_TEST);
        changeColorShape(0, Global.COLOR_PURPLE);
    }
    @Override
    public void drag_end(){
        SoundPlayer.stop();
        changeColorShape(0, Global.COLOR_AQUA);
    }
    @Override
    public boolean drag_loop() {
        return true;
    }

    @Override
    public void action(int action) {
        switch(action){
            case 1:
                SoundPlayer.play(Global.SOUND_TEST2);
            break;
        }
    }
    
    
}
