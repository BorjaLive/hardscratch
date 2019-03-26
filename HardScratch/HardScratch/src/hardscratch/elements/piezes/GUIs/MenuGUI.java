package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.base.Element;
import static hardscratch.Global.*;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuGUI extends Element{

    public MenuGUI() {
        super(0, 0, -1, true, true, false);
        
        addGUImenu(this);
        
        int paddingBorder = (int) (WINDOW_HEIGHT*0.05);
        float scale32H = FONT_MONOFONTO.getFixedScale(32f, 1.5f);
        addLabel(new TextLabel(0, 0, 4, scale32H, FONT_MONOFONTO, COLOR_GUI_5, VERSION_NAME, false), paddingBorder+5, (int) (WINDOW_HEIGHT-paddingBorder-(FONT_MONOFONTO.getHeight()*scale32H*1.5f)-5));
        getLabel(0).setScretch(1.5f);
        
        int widthButton = (int) (WINDOW_WIDTH*0.5), heightButton = (int) (WINDOW_HEIGHT*0.15);
        float scale10H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.12f,1.5f);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), (WINDOW_WIDTH-widthButton)/2, (int) (WINDOW_HEIGHT*0.43));
        addLabel(new TextLabel(0, 0, 3, scale10H, FONT_MONOFONTO, COLOR_GUI_5, "CREATE", true), WINDOW_WIDTH/2, (int) (WINDOW_HEIGHT*0.43)+(heightButton/2));
        labels.get(1).setScretch(1.5f);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), (WINDOW_WIDTH-widthButton)/2, (int) (WINDOW_HEIGHT*0.43)+heightButton+15);
        addLabel(new TextLabel(0, 0, 3, scale10H, FONT_MONOFONTO, COLOR_GUI_5, "OPEN", true), WINDOW_WIDTH/2, (int) (WINDOW_HEIGHT*0.43)+(heightButton/2)+heightButton+15);
        labels.get(2).setScretch(1.5f);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton/2, heightButton, (int) (WINDOW_WIDTH*0.01)), (WINDOW_WIDTH-widthButton/2)/2, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT);
        addLabel(new TextLabel(0, 0, 3, scale10H, FONT_MONOFONTO, COLOR_GUI_5, "HELP", true), WINDOW_WIDTH/2, (int) (-WINDOW_HEIGHT*0.22)+(heightButton/2)+WINDOW_HEIGHT);
        labels.get(3).setScretch(1.5f);
        
        int settingsSize = (int) (WINDOW_WIDTH*0.07);
        addImage(new Image(0, 0, 4, TEXTURE_SETTINGS, settingsSize/196f, COLOR_WHITE), WINDOW_WIDTH-settingsSize-((int)(WINDOW_HEIGHT*0.07)), WINDOW_HEIGHT-settingsSize-((int)(WINDOW_HEIGHT*0.07)));
        
        addBoundingBox((WINDOW_WIDTH-widthButton)/2, (WINDOW_WIDTH+widthButton)/2, (int) (WINDOW_HEIGHT*0.43), (int) (WINDOW_HEIGHT*0.43)+heightButton, EVENT_GO_CREATE);
        addBoundingBox((WINDOW_WIDTH-widthButton)/2, (WINDOW_WIDTH+widthButton)/2, (int) (WINDOW_HEIGHT*0.43)+heightButton+15, (int) (WINDOW_HEIGHT*0.43)+(heightButton*2)+15, EVENT_GO_OPEN);
        addBoundingBox((WINDOW_WIDTH-widthButton/2)/2, (WINDOW_WIDTH+widthButton/2)/2, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT+heightButton, EVENT_GO_HELP);
        addBoundingBox(WINDOW_WIDTH-settingsSize-((int)(WINDOW_HEIGHT*0.07)), WINDOW_WIDTH-((int)(WINDOW_HEIGHT*0.07)),  WINDOW_HEIGHT-settingsSize-((int)(WINDOW_HEIGHT*0.07)),  WINDOW_HEIGHT-((int)(WINDOW_HEIGHT*0.07)), EVENT_GO_SETTINGS);
    }
    
    @Override
    public void action(int action) {
        switch(action){
            case EVENT_GO_CREATE:
                Controller.changeRoom(ROOM_CREATE);
            break;
            case EVENT_GO_OPEN:
                Controller.changeRoom(ROOM_OPEN);
            break;
            case EVENT_GO_HELP:
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    SOUND_KEY_PRESS.play();
                    try {   //Poner la documentacion en esa URL
                        Desktop.getDesktop().browse(new URI("https://github.com/BorjaLive/hardscratch"));
                    } catch (URISyntaxException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            break;
            case EVENT_GO_SETTINGS:
                Controller.changeRoom(ROOM_SETTINGS);
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
