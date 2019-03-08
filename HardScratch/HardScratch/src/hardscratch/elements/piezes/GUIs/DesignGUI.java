package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.backend.*;
import hardscratch.base.Element;
import hardscratch.base.shapes.*;

public class DesignGUI extends Element{
    
    private Shape_Square bar, backColor, backColorFinder;
    private Image toggle;

    public DesignGUI() {
        super(0, 0, -1, true, true, false);
        depth = 1;
        
        addShape(new Shape_Square(0, 0, Global.COLOR_GUI_2, 1, 4, Global.WINDOW_WIDTH, 70), 0, 0);
        addImage(new Image(0, 0, 3, Global.TEXTURE_HOUSE, 1, Global.COLOR_GUI_3), Global.WINDOW_WIDTH-80, 0);
        addImage(new Image(0, 0, 3, Global.TEXTURE_SAVE, 1, Global.COLOR_GUI_3), Global.WINDOW_WIDTH-160, 0);
        addShape(new Shape_BorderedBox(0, 0, Global.COLOR_GUI_4, Global.COLOR_GUI_5, 1, 3, 200, 52, 5), 36, 10);
        addShape(new Shape_BorderedBox(0, 0, Global.COLOR_GUI_4, Global.COLOR_GUI_5, 1, 3, 200, 52, 5), 246, 10);
        addShape(new Shape_BorderedBox(0, 0, Global.COLOR_GUI_4, Global.COLOR_GUI_5, 1, 3, 200, 52, 5), 456, 10);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "DESIGN", true), 136, 36);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "IMPLEMENT", true), 346, 36);
        addLabel(new TextLabel(0, 0, 2, 0.42f, Global.FONT_MONOFONTO, Global.COLOR_WHITE, "SIMULATE", true), 556, 36);
        labels.get(0).setScretch(1.8f);
        labels.get(1).setScretch(1.8f);
        labels.get(2).setScretch(1.8f);
        
        addBoundingBox(Global.WINDOW_WIDTH-80, Global.WINDOW_WIDTH-10, 0, 70, Global.EVENT_GO_HOMO);
        addBoundingBox(Global.WINDOW_WIDTH-160, Global.WINDOW_WIDTH-90, 0, 70, Global.EVENT_SAVE);
        addBoundingBox(36, 236, 10, 62, Global.EVENT_GO_DESIGN);
        addBoundingBox(246, 446, 10, 62, Global.EVENT_GO_IMPLEMENT);
        addBoundingBox(456, 656, 10, 62, Global.EVENT_GO_SIMULATE);
        
        bar = new Shape_Square(400, 70, Global.COLOR_GUI_2, 1, 4, 20, Global.WINDOW_HEIGHT-70);
        toggle = new Image(390,70+((Global.WINDOW_HEIGHT-150)/2), 2, Global.TEXTURE_FINDERSLIDER, 1, Global.COLOR_WHITE);
        addBoundingBox(390, 430, 70+((Global.WINDOW_HEIGHT-150)/2), 150+((Global.WINDOW_HEIGHT-150)/2), Global.EVENT_FINDER_TOGGLE);
        backColor = new Shape_Square(0, 0, Global.COLOR_GUI_1, 1, 5, Global.WINDOW_WIDTH, Global.WINDOW_WIDTH);
        backColorFinder = new Shape_Square(0, 0, Global.COLOR_GUI_6, 1, 5, 400, Global.WINDOW_WIDTH);
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
            break;
            case Global.EVENT_DRAW_FLOD:
                backColorFinder.draw();
                bar.draw();
                toggle.draw();
            break;
            case Global.EVENT_GO_HOMO:
            break;
            case Global.EVENT_SAVE:
                BUCKLE.save();
            break;
            case Global.EVENT_GO_DESIGN:
            break;
            case Global.EVENT_GO_IMPLEMENT:
                Controller.changeRoom(Global.ROOM_IMPLEMENT);
            break;
            case Global.EVENT_GO_SIMULATE:
                Controller.changeRoom(Global.ROOM_SIMULATE);
            break;
            case Global.EVENT_FINDER_TOGGLE:
                Controller.finderToggle();
            break;
            case Global.EVENT_TOOGLE_TOGGLE:
                toggle.setInvertW();
            break;
        }
    }

    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
        switch(event){
            case Global.EVENT_FINDER_MOVE:
                bar.move(data1, 0);
                toggle.move(data1, 0);
                boundingBoxMove(5, data1, 0);
                backColorFinder.move(data1, 0);
            break;
        }
    }

    @Override
    public void latWish() {
    }
    
}
