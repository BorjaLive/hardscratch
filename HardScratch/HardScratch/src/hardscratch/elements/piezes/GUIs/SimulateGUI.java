package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.backend.BUCKLE;
import hardscratch.base.Element;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.elements.piezes.Simulation.*;

public class SimulateGUI extends Element{
    
    private Shape_Square backColor, backBoard;
    private final int X, Y, width, height, unit;

    public SimulateGUI() {
        super(0, 0, -1, true, true, false);
        depth = 1;
        
        Global.addGUIframe(this);
        backColor = new Shape_Square(0, 0, Global.COLOR_GUI_1, 1, 5, Global.WINDOW_WIDTH, Global.WINDOW_WIDTH);
        
        height = (int) ((Global.WINDOW_HEIGHT-70)*0.9);
        width = (int) ((Global.WINDOW_HEIGHT-70)*1.4);
        Y = 70+(Global.WINDOW_HEIGHT-height-70)/2;
        X = (Global.WINDOW_WIDTH-width)/2;
        unit = width/20;
        
        backBoard = new Shape_Square(X, Y, Global.COLOR_CIRCUIT_GREEN, 1, 5, width, height);
        addImage(new Image(0, 0, 2, Global.TEXTURE_BORELICIOUS, (5*unit)/254f, Global.COLOR_WHITE), Y+unit*15, X+(int) (unit*2.5f));
    }
    
    private void createElectronics(){
        Controller.addToBoard(new SimulateSwitch(X+(unit), Y+(unit), unit, "POWER"));
        for(int i = 0; i < 4; i++)
            Controller.addToBoard(new SimulateSwitch((int) (X+(unit*(14+(i*1.5)))), Y+height-(unit*3), unit, "SWITCH"+(4-i)));
        
        Controller.addToBoard(new SimulatedButton((int) (X+(unit*2.25f)), (int) (Y+height-(unit*4.5f)), unit, "BUTTON1"));
        Controller.addToBoard(new SimulatedButton((int) (X+(unit*3.5f)), (int) (Y+height-(unit*3.25f)), unit, "BUTTON2"));
        Controller.addToBoard(new SimulatedButton((int) (X+(unit*2.25f)), (int) (Y+height-(unit*2)), unit, "BUTTON3"));
        Controller.addToBoard(new SimulatedButton((int) (X+unit), (int) (Y+height-(unit*3.25f)), unit, "BUTTON4"));
        
        for(int i = 0; i < 8; i++)
            Controller.addToBoard(new SimulatedLed(X+width-(unit*(i+1)), Y+height-(unit*6), unit, "LED"+(i+1)));
        
        Controller.addToBoard(new SimulatedBCD(X+(unit*3), Y+(unit*4), unit, "BCD1"));
        Controller.addToBoard(new SimulatedBCD(X+(unit*6), Y+(unit*4), unit, "BCD2"));
        
        Controller.addToBoard(new SimulatedLCD( X+(unit*6), Y+height-(unit*3), unit, "LCD"));
        
        Controller.addToBoard(new SimulatedButton((int) X+(unit*18), Y+unit, unit, "RESET"));
        
        Controller.addToBoard(new SimulatedClock(X+(12*unit), Y+(3*unit), unit, "CLOCK"));
    }

    @Override
    protected long colideExtra(int x, int y) {
        return -1;
    }

    @Override
    protected void moveExtra(int x, int y) {
    }

    @Override
    protected void drawExtra() {
    }

    @Override
    protected void select_init(long ID) {
    }

    @Override
    protected void select_end() {
    }

    @Override
    protected void selected_loop() {
    }

    @Override
    public boolean drag_loop() {
        return false;
    }

    @Override
    public void drag_init() {
    }

    @Override
    public void drag_end() {
    }

    @Override
    public void action(int action) {
        switch(action){
            case Global.EVENT_DRAW_BACKGROUND:
                backColor.draw();
                backBoard.draw();
            break;
            case Global.EVENT_DRAW_FLOD:
                //No hay flod
            break;
            case Global.EVENT_GO_HOMO:
            break;
            case Global.EVENT_SAVE:
                BUCKLE.save();
            break;
            case Global.EVENT_GO_DESIGN:
                Controller.changeRoom(Global.ROOM_DESIGN);
            break;
            case Global.EVENT_GO_IMPLEMENT:
                Controller.changeRoom(Global.ROOM_IMPLEMENT);
            break;
            case Global.EVENT_GO_SIMULATE:
            break;
            case Global.EVENT_CREATE_SPARTAN:
                createElectronics();
            break;
        }
    }

    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }
    
}
