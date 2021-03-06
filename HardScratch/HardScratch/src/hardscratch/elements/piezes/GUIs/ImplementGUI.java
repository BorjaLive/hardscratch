package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.backend.BUCKLE;
import hardscratch.base.Element;
import hardscratch.base.shapes.Shape_Square;

public class ImplementGUI extends Element{

    private Shape_Square bar, backColor, backColorFinder;
    
    public ImplementGUI() {
        super(0, 0, -1, true, true, false);
        depth = 1;
        
        addGUIframe(this);
        bar = new Shape_Square(400, 70, COLOR_GUI_2, 1, 4, 20, WINDOW_HEIGHT-70);
        backColor = new Shape_Square(0, 0, COLOR_GUI_1, 1, 5, WINDOW_WIDTH, WINDOW_WIDTH);
        backColorFinder = new Shape_Square(0, 0, COLOR_GUI_6, 1, 5, 400, WINDOW_WIDTH);
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
            case EVENT_DRAW_BACKGROUND:
                backColor.draw();
            break;
            case EVENT_DRAW_FLOD:
                backColorFinder.draw();
                bar.draw();
            break;
            case EVENT_GO_HOMO:
                Controller.changeRoom(ROOM_MENU);
            break;
            case EVENT_SAVE:
                BUCKLE.save();
            break;
            case EVENT_GO_DESIGN:
                Controller.changeRoom(ROOM_DESIGN);
            break;
            case EVENT_GO_IMPLEMENT:
            break;
            case EVENT_GO_SIMULATE:
                Controller.changeRoom(ROOM_SIMULATE);
            break;
            case EVENT_FINDER_TOGGLE:
                //No toggle para este finder
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
