package hardscratch.elements.piezes;

import hardscratch.Controller;
import hardscratch.base.Element;
import static hardscratch.Global.*;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;

public class Notifier extends Element{

    public Notifier(int errorCode) {
        super(0, 0, -1, true, false, false);
        depth = 1;
        
        addShape(new Shape_Square(0, 0, COLOR_GUI_4, 1, 5, 500, 140), 0, 0);
        addShape(new Shape_Square(0, 0, COLOR_GUI_1, 1, 4, 480, 100), 10, 30);
        addImage(new Image(0, 0, 3, TEXTURE_CLOSE_CROSS, 1, COLOR_WHITE), 470, 0);
        addBoundingBox(470, 500, 0, 30, 1);
        addLabel(new TextLabel(0, 0, 2, 0.5f, FONT_MONOFONTO, COLOR_TEXT_INPUT,
                ERROR_NAME[errorCode].toUpperCase(), true), 250, 80);
        labels.get(0).setScretch(1.4f);
        
        move(((WINDOW_WIDTH-900)/2)+400, WINDOW_HEIGHT-140);
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
        if(action == 1)
            Controller.deleteElement(ID);
    }

    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
        Controller.errorTerminate();
    }
    
}
