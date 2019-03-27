package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.base.Element;
import static hardscratch.Global.*;
import hardscratch.base.Sound;
import hardscratch.base.SoundPlayer;
import hardscratch.base.shapes.*;
import hardscratch.elements.piezes.ProyectSelecter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class OpenGUI extends Element{
    
    private Shape[] cover;
    private Shape back, border, button;
    private TextLabel buttonText;
    private Image logo, trashcan, menu;
    private ArrayList<ProyectSelecter> proyects;
    private int selectedI;

    public OpenGUI() {
        super(0, 0, -1, true, true, false);
        
        //Adaptacion de la GUIprincipal
        border = new Shape_Square(0, 0, COLOR_GUI_2, 1, 5, WINDOW_WIDTH, WINDOW_HEIGHT);
        int widthLogo = (int) (WINDOW_WIDTH*0.9);
        logo = new Image((WINDOW_WIDTH-widthLogo)/2, (int) (WINDOW_HEIGHT*0.12), 4, TEXTURE_LOGO, widthLogo/1920f, COLOR_WHITE);
        
        //Agregar el boton para regresar al menu
        
        float scale10H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.1f, 1.5f);
        float scale12H = FONT_MONOFONTO.getFixedScale(WINDOW_HEIGHT*0.12f, 1.5f);
        //TODO: listar carpetas
        int paddingBorder = (int) (WINDOW_HEIGHT*0.05);
        cover = new Shape[6];
        cover[0] = new Shape_Square(paddingBorder, paddingBorder, COLOR_GUI_1, 1, 3, WINDOW_WIDTH-paddingBorder*2, (int) ((WINDOW_HEIGHT*0.36)-paddingBorder));
        cover[1] = new Shape_Square(paddingBorder, (int) (WINDOW_HEIGHT*0.76), COLOR_GUI_1, 1, 3, WINDOW_WIDTH-paddingBorder*2, (int) ((WINDOW_HEIGHT*0.25))-paddingBorder);
        cover[2] = new Shape_Square(paddingBorder, (int) (WINDOW_HEIGHT*0.36), COLOR_GUI_1, 1, 3, (int) (WINDOW_WIDTH*0.0755)-paddingBorder, (int) (WINDOW_HEIGHT*0.40));//Cambiar ese 33 a 50
        cover[3] = new Shape_Square((int) (WINDOW_WIDTH*0.925), (int) (WINDOW_HEIGHT*0.36), COLOR_GUI_1, 1, 3, (int) (WINDOW_WIDTH*0.0755)-paddingBorder, (int) (WINDOW_HEIGHT*0.40));
        cover[4] = new Shape_Square(0, 0, COLOR_GUI_2, 1, 3, WINDOW_WIDTH, paddingBorder);
        cover[5] = new Shape_Square(0, WINDOW_HEIGHT-paddingBorder, COLOR_GUI_2, 1, 3, WINDOW_WIDTH, paddingBorder);
        back = new Shape_Square(paddingBorder, paddingBorder, COLOR_GUI_6, 1, 3, WINDOW_WIDTH-paddingBorder*2, WINDOW_HEIGHT-paddingBorder*2);
        
        int widthSelecter = (int) (WINDOW_WIDTH*0.65f);
        int heightSelecter = (int) (widthSelecter/8);
        proyects = new ArrayList<>();
        File file = new File(System.getenv("APPDATA")+"/HardScratch/");
        String[] directories = file.list(new FilenameFilter() {
          @Override
          public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
          }
        });
        for(int i = 0; i < directories.length; i++)
            proyects.add(new ProyectSelecter((WINDOW_WIDTH-widthSelecter)/2, (int) ((WINDOW_HEIGHT*0.38)+((heightSelecter+10)*i)), directories[i].replace("_", " "), widthSelecter, heightSelecter, scale10H));
        selectedI = -1;
        
        
        int widthButton = (int) (WINDOW_WIDTH*0.5), heightButton = (int) (WINDOW_HEIGHT*0.15);
        button = new Shape_BorderedBox((WINDOW_WIDTH-widthButton)/2, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT, COLOR_GUI_4, COLOR_GUI_5, 1, 4, widthButton, heightButton, (int) (WINDOW_WIDTH*0.01));
        buttonText = new TextLabel(WINDOW_WIDTH/2, (int) (-WINDOW_HEIGHT*0.22)+(heightButton/2)+WINDOW_HEIGHT, 3, scale12H, FONT_MONOFONTO, COLOR_GUI_5, "OPEN", true);
        buttonText.setScretch(1.5f);
        
        float scaleImage = heightButton/350f;
        trashcan = new Image((int) (((WINDOW_WIDTH-widthButton)/2)-(scaleImage*350f)), (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT, 3, TEXTURE_TRASHCAN, scaleImage, COLOR_GUI_5);
        float scaleMenu = (WINDOW_HEIGHT*0.10f)/70f;
        menu = new Image((int) (WINDOW_WIDTH-paddingBorder-(scaleMenu*75f)), (int) (WINDOW_HEIGHT-paddingBorder-(scaleMenu*70f)), 3, TEXTURE_HOUSE, scaleMenu, COLOR_WHITE);
        
        addBoundingBox((WINDOW_WIDTH-widthButton)/2, (WINDOW_WIDTH+widthButton)/2, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT+heightButton, EVENT_GO_DESIGN);
        addBoundingBox((int) (((WINDOW_WIDTH-widthButton)/2)-(scaleImage*350f)), (int) (((WINDOW_WIDTH-widthButton)/2)-(scaleImage*49f))
                , (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT, (int) (-WINDOW_HEIGHT*0.22)+WINDOW_HEIGHT+heightButton, EVENT_DELETE);
        addBoundingBox((int) (WINDOW_WIDTH-paddingBorder-(scaleMenu*75f)), (int) (WINDOW_WIDTH-paddingBorder-(scaleMenu*5f)),
                (int) (WINDOW_HEIGHT-paddingBorder-(scaleMenu*75f)), WINDOW_HEIGHT-paddingBorder, EVENT_GO_HOMO);
    }
    
    
    @Override
    public void action(int action) {
        switch(action){
            case EVENT_GO_DESIGN:
                if(selectedI != -1){
                    Controller.setOpenOnNext(proyects.get(selectedI).getName());
                }
            break;
            case EVENT_GO_HOMO:
                Controller.changeRoom(ROOM_MENU);
            break;
            case EVENT_DELETE:
                if(selectedI != -1){
                    deleteProyect(proyects.get(selectedI).getName());
                    Controller.changeRoom(ROOM_OPEN);
                }
            break;
        }
    }

    @Override
    protected long colideExtra(int x, int y) {
        //Comprobar que este dentro de los bounds
        if(y > WINDOW_HEIGHT*0.76 || y < WINDOW_HEIGHT*0.36) return -1;
        
        if(selectedI != -1)
            proyects.get(selectedI).deSelect();
        selectedI = -1;
        for(ProyectSelecter p:proyects)
            if(p.colide(x, y)){
                selectedI = proyects.indexOf(p);//Baya tonteria tosca
                p.select();
                SoundPlayer.play(SOUND_KEY_PRESS);
                return p.getID();
            }
        return -1;
    }
    @Override
    protected void moveExtra(int x, int y) {
    }
    @Override
    protected void drawExtra() {
        border.draw();
        back.draw();
        for(ProyectSelecter p:proyects)
                p.draw();
        for(Shape s:cover)
                s.draw();
        logo.draw();
        button.draw();
        buttonText.draw();
        trashcan.draw();
        menu.draw();
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
        switch(event){
            case EVENT_SCROLL:
                //data 1 es la cantidad
                if(data1 > 0){
                    //Bajar: El primero no debe bajar de x POS
                    if(proyects.get(0).getY() > WINDOW_HEIGHT*0.38) break;
                }else{
                    //Subir: El ultimo nodebe subir de x POS
                    if(proyects.get(proyects.size()-1).getY() < (WINDOW_HEIGHT*0.76f)-proyects.get(0).getHeight()) break;
                }
                for(ProyectSelecter p:proyects)
                    p.move(0, data1);   //Quizas haya que reducir un poco la velocidad
            break;
        }
    }
    @Override
    public void latWish() {
    }
}
