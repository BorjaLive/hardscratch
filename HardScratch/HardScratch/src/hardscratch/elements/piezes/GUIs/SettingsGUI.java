package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.base.Element;
import static hardscratch.Global.*;
import hardscratch.backend.CONF;
import hardscratch.base.shapes.*;

public class SettingsGUI extends Element{

    private int theme, window, resolution;
    
    public SettingsGUI() {
        super(0, 0, -1, true, true, false);
        
        addGUImenu(this);
        
        int paddingBorder = (int) (WINDOW_HEIGHT*0.05);
        float scale6H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.06f, 1.5f);
        float scale10H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.1f, 1.5f);
        float scale12H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.12f, 1.5f);
        
        int topheight = (int) (WINDOW_HEIGHT*0.38f);
        int widthButton = (int) (WINDOW_WIDTH*0.23), heightButton = (int) (WINDOW_HEIGHT*0.12);
        
        addLabel(new TextLabel(0, 0, 4, scale12H, COLOR_GUI_5, "THEME", false), (int) (WINDOW_WIDTH*0.05f), topheight+(WINDOW_HEIGHT/100));
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton, topheight);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, topheight);
        addLabel(new TextLabel(0, 0, 3, scale10H, COLOR_GUI_5, "DARK", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(widthButton/2), topheight+(heightButton/2));
        addLabel(new TextLabel(0, 0, 3, scale10H, COLOR_GUI_5, "LIGHT", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton-(widthButton/2)-15, topheight+(heightButton/2));
        
        addLabel(new TextLabel(0, 0, 4, scale12H, COLOR_GUI_5, "VIEW MODE", false), (int) (WINDOW_WIDTH*0.05f), topheight+heightButton+15+(WINDOW_HEIGHT/100));
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton, topheight+heightButton+15);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, topheight+heightButton+15);
        addLabel(new TextLabel(0, 0, 3, scale6H, COLOR_GUI_5, "WINTOWERED", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(widthButton/2), topheight+(heightButton/2)+heightButton+15);
        addLabel(new TextLabel(0, 0, 3, scale6H, COLOR_GUI_5, "FULLSCREEN", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton-(widthButton/2)-15, topheight+(heightButton/2)+heightButton+15);
        
        addLabel(new TextLabel(0, 0, 4, scale12H, COLOR_GUI_5, "RESOLUTION", false), (int) (WINDOW_WIDTH*0.05f), topheight+(2*heightButton)+30+(WINDOW_HEIGHT/100));
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, heightButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-heightButton, topheight+(2*heightButton)+30);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, heightButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, topheight+(2*heightButton)+30);
        addLabel(new TextLabel(0, 0, 3, scale12H, COLOR_GUI_5, "+", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(heightButton/2), topheight+(heightButton/2)+(2*heightButton)+30);
        addLabel(new TextLabel(0, 0, 3, scale12H, COLOR_GUI_5, "-", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)+(heightButton/2)-15, topheight+(heightButton/2)+(2*heightButton)+30);
        addLabel(new TextLabel(0, 0, 4, scale10H, COLOR_GUI_5, "ERROR", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton-7, topheight+(2*heightButton)+(heightButton/2)+30+(WINDOW_HEIGHT/100));
        
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton, WINDOW_WIDTH-(WINDOW_WIDTH/20),
                topheight, topheight+heightButton, 101);
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, WINDOW_WIDTH-widthButton-(WINDOW_WIDTH/20)-15,
                topheight, topheight+heightButton, 102);
        
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton, WINDOW_WIDTH-(WINDOW_WIDTH/20),
                topheight+heightButton+15, topheight+(2*heightButton)+15, 103);
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, WINDOW_WIDTH-widthButton-(WINDOW_WIDTH/20)-15,
                topheight+heightButton+15, topheight+(2*heightButton)+15, 104);
        
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-heightButton, WINDOW_WIDTH-(WINDOW_WIDTH/20),
                topheight+(2*heightButton)+30, topheight+(3*heightButton)+30, 105);
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, WINDOW_WIDTH-(2*widthButton)+heightButton-(WINDOW_WIDTH/20)-15,
                topheight+(2*heightButton)+30, topheight+(3*heightButton)+30, 106);
        
        
        //Botones aplicar y restaurar
        addShape(new Shape_BorderedBox(0, 0, COLOR_NOOK, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton, WINDOW_HEIGHT-paddingBorder-heightButton-15);
        addShape(new Shape_BorderedBox(0, 0, COLOR_OK, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, WINDOW_HEIGHT-paddingBorder-heightButton-15);
        addLabel(new TextLabel(0, 0, 3, scale10H*0.95f, COLOR_GUI_5, "DISCARD", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton-(widthButton/2)-15, WINDOW_HEIGHT-paddingBorder-(heightButton/2)-15);
        addLabel(new TextLabel(0, 0, 3, scale10H, COLOR_GUI_5, "APPLY", true), WINDOW_WIDTH-(WINDOW_WIDTH/20)-(widthButton/2), WINDOW_HEIGHT-paddingBorder-(heightButton/2)-15);
        
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-(2*widthButton)-15, WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton-15,
                WINDOW_HEIGHT-paddingBorder-heightButton-15, WINDOW_HEIGHT-paddingBorder-15, EVENT_GO_HOMO);
        addBoundingBox(WINDOW_WIDTH-(WINDOW_WIDTH/20)-widthButton, WINDOW_WIDTH-(WINDOW_WIDTH/20),
                WINDOW_HEIGHT-paddingBorder-heightButton-15, WINDOW_HEIGHT-paddingBorder-15, EVENT_SAVE);
        
        
        //Cargar valores guardados
        theme = CONF.get(CONF.THEME);
        if(theme == 1)      ((Shape_BorderedBox) shapes.get(3)).changeBorderColor(COLOR_BORDER_SELECTED);
        else if(theme == 2) ((Shape_BorderedBox) shapes.get(2)).changeBorderColor(COLOR_BORDER_SELECTED);
        
        window = CONF.get(CONF.WINDOW);
        if(window == 1)     ((Shape_BorderedBox) shapes.get(4)).changeBorderColor(COLOR_BORDER_SELECTED);
        else if(window == 2)((Shape_BorderedBox) shapes.get(5)).changeBorderColor(COLOR_BORDER_SELECTED);
        
        resolution = CONF.get(CONF.RESOLUTION);
        labels.get(9).setText(RESOLUTION_LIST[resolution].name());
    }
    
    
    @Override
    public void action(int action) {
        switch(action){
            case EVENT_GO_CREATE:
                Controller.changeRoom(ROOM_CREATE);
            break;
            case EVENT_SAVE:
                CONF.write();
                planedRestart();
            break;
            case EVENT_GO_HOMO:
                Controller.changeRoom(ROOM_MENU);
            break;
            case 101://Theme - Dark
                theme = 2;
                CONF.set(CONF.THEME, theme);
                ((Shape_BorderedBox) shapes.get(2)).changeBorderColor(COLOR_BORDER_SELECTED);
                ((Shape_BorderedBox) shapes.get(3)).changeBorderColor(COLOR_BORDER_UNSELECTED);
            break;
            case 102://Theme - Light
                theme = 1;
                CONF.set(CONF.THEME, theme);
                ((Shape_BorderedBox) shapes.get(2)).changeBorderColor(COLOR_BORDER_UNSELECTED);
                ((Shape_BorderedBox) shapes.get(3)).changeBorderColor(COLOR_BORDER_SELECTED);
            break;
            case 103://WINOW - Wintowered
                window = 1;
                CONF.set(CONF.WINDOW, window);
                ((Shape_BorderedBox) shapes.get(4)).changeBorderColor(COLOR_BORDER_SELECTED);
                ((Shape_BorderedBox) shapes.get(5)).changeBorderColor(COLOR_BORDER_UNSELECTED);
            break;
            case 104://WINDOW - Fullscreen
                window = 2;
                CONF.set(CONF.WINDOW, window);
                ((Shape_BorderedBox) shapes.get(4)).changeBorderColor(COLOR_BORDER_UNSELECTED);
                ((Shape_BorderedBox) shapes.get(5)).changeBorderColor(COLOR_BORDER_SELECTED);
            break;
            case 105://RESOLUTION - ADD
                if(resolution+1 < RESOLUTION_LIST.length){
                    resolution++;
                    CONF.set(CONF.RESOLUTION, resolution);
                    labels.get(9).setText(RESOLUTION_LIST[resolution].name());
                    ((Shape_BorderedBox) shapes.get(6)).changeBorderColor(COLOR_BORDER_SELECTED);
                }
            break;
            case 106://RESOLUTION - SUB
                if(resolution-1 >= 0){
                    resolution--;
                    CONF.set(CONF.RESOLUTION, resolution);
                    labels.get(9).setText(RESOLUTION_LIST[resolution].name());
                    ((Shape_BorderedBox) shapes.get(7)).changeBorderColor(COLOR_BORDER_SELECTED);
                }
            break;
            case 107://RESOLUTION - DESELECT ALL
                ((Shape_BorderedBox) shapes.get(6)).changeBorderColor(COLOR_BORDER_UNSELECTED);
                ((Shape_BorderedBox) shapes.get(7)).changeBorderColor(COLOR_BORDER_UNSELECTED);
            break;
        }
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
    public void updateEvent(int event, int data1, int data2, String data3) {
    }
    @Override
    public void latWish() {
    }
}
