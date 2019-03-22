package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.base.Element;
import static hardscratch.Global.*;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.*;
import hardscratch.elements.piezes.WaitCourtain;

public class CreateGUI extends Element{

    public CreateGUI() {
        super(0, 0, -1, true, true, false);
        
        addGUImenu(this);
        
        
        addLabel(new TextLabel(0, 0, 4, WINDOW_WIDTH/1080f, FONT_MONOFONTO, COLOR_GUI_5, "CREATE NEW PROYECT", true), WINDOW_WIDTH/2, (int) ((WINDOW_HEIGHT*0.4)+(32*(WINDOW_WIDTH/1080f))));
        labels.get(0).setScretch(1.5f);
        
        float scale10H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.10f,1.5f);
        float scale15H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.15f,1.5f);
        int widthInput = (int) (WINDOW_WIDTH*0.8f), heightInput = (int) (WINDOW_HEIGHT*0.13);
        addTextBox(new TextBox(0, 0, 4, scale15H, FONT_MONOFONTO, "PROYECT NAME", null, null, null, null, null, widthInput, heightInput, 25, false, true, true), (WINDOW_WIDTH-widthInput)/2, (int) (WINDOW_HEIGHT*0.52f));
        
        int widthButton = (int) (WINDOW_WIDTH*0.5), heightButton = (int) (WINDOW_HEIGHT*0.15);
        addShape(new Shape_BorderedBox(0, 0, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01)), (WINDOW_WIDTH-widthButton)/2, (int) (-WINDOW_HEIGHT*0.30)+WINDOW_HEIGHT);
        addLabel(new TextLabel(0, 0, 3, scale10H, FONT_MONOFONTO, COLOR_GUI_5, "CREATE", true), WINDOW_WIDTH/2, (int) (-WINDOW_HEIGHT*0.30)+(heightButton/2)+WINDOW_HEIGHT);
        labels.get(1).setScretch(1.5f);
        
        int paddingBorder = (int) (WINDOW_HEIGHT*0.05);
        float scaleMenu = (WINDOW_HEIGHT*0.10f)/70f;
        addImage(new Image(0, 0, 3, TEXTURE_HOUSE, scaleMenu, COLOR_WHITE), (int) (WINDOW_WIDTH-paddingBorder-(scaleMenu*75f)), (int) (WINDOW_HEIGHT-paddingBorder-(scaleMenu*70f)));
        
        addBoundingBox((WINDOW_WIDTH-widthButton)/2, (WINDOW_WIDTH+widthButton)/2, (int) (-WINDOW_HEIGHT*0.30)+WINDOW_HEIGHT, (int) (-WINDOW_HEIGHT*0.30)+WINDOW_HEIGHT+heightButton, EVENT_GO_DESIGN);
        addBoundingBox((int) (WINDOW_WIDTH-paddingBorder-(scaleMenu*75f)), (int) (WINDOW_WIDTH-paddingBorder-(scaleMenu*5f)),
                (int) (WINDOW_HEIGHT-paddingBorder-(scaleMenu*75f)), WINDOW_HEIGHT-paddingBorder, EVENT_GO_HOMO);
    }
    
    
    @Override
    public void action(int action) {
        switch(action){
            case EVENT_GO_DESIGN:
                createProyect(boxen.get(0).getText().replace(" ", "_"));
                WaitCourtain.drawFrame();
                Controller.changeRoom(ROOM_DESIGN);
            break;
            case EVENT_GO_HOMO:
                Controller.changeRoom(ROOM_MENU);
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
