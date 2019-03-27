package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.base.Element;
import hardscratch.base.Sound;
import hardscratch.base.SoundPlayer;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.base.shapes.TextLabel;
import hardscratch.inputs.Keyboard;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static org.lwjgl.glfw.GLFW.*;

public class EasterGUI  extends Element{
    
    private String audioFile = RESOURCES+"sound/easterSong.wav";
    private String wordsFile = RESOURCES+"sound/easter.wav";
    private String inetSource = "https://www.filehosting.org/file/details/789842/videoplayback.wav";
    private float[] COLOR_BACK = new float[]{255/255f, 98/255f, 64/255f};
    private String[] words;
    private long[] times;
    private int counter;
    
    private long StarterTime;
    private Sound song;
    private Shape_Square back;
    private TextLabel text;
    
    public EasterGUI() {
        super(0, 0, -1, true, true, false);
        
        if((new File(audioFile)).exists()){
            load();
        }else{
            action(EVENT_TOGGLE);
        }
        
        back = new Shape_Square(0, 0, COLOR_BACK, 1, 5, WINDOW_WIDTH, WINDOW_HEIGHT);
        text = new TextLabel(WINDOW_WIDTH/2, WINDOW_HEIGHT/2, 4, WINDOW_WIDTH/700f, COLOR_TEXT_INPUT, "LOADING", true);
    }

    
    @Override
    public void action(int action) {
         switch(action){
            case EVENT_TOGGLE:  //Descargar
                new Thread(){
                    public void run() {
                        try {
                            BufferedInputStream in = new BufferedInputStream(new URL(inetSource).openStream());
                            FileOutputStream fileOutputStream = new FileOutputStream(audioFile);
                            byte dataBuffer[] = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                                fileOutputStream.write(dataBuffer, 0, bytesRead);
                            }
                            load();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            break;
            case EVENT_TURN_ON: //Ejecutar
                if(Keyboard.getClick(GLFW_KEY_F1)){
                    action(EVENT_GO_HOMO);
                }
                if(counter >= words.length) return;
                
                long time = (long) ((System.currentTimeMillis()-StarterTime)/33.3587f); //Tiempo en ms que lleva sonando
                //System.out.println("TIME: "+time+" PERIODE: "+times[counter*2]+"  "+times[(counter*2)+1]+" WORD: "+words[counter]);
                               
                if(time > times[counter*2]){
                    text.setAlpha(1f);
                }else if(time+1 > times[counter*2]){
                    text.setAlpha(0.5f);
                }else if(time+2 > times[counter*2]){
                    text.setText(words[counter]);
                    text.setAlpha(0.25f);
                }
                
                if(time > times[(counter*2)+1]){
                    text.setText("");
                    counter++;
                }else if(time+1 > times[(counter*2)+1])
                    text.setAlpha(0.5f);
                else if(time+2 > times[(counter*2)+1]){
                    text.setAlpha(0.25f);
                }
                
            break;
            case EVENT_GO_HOMO:
                SoundPlayer.stop(song);
                Controller.setEasterCheck(false);
                Controller.changeRoom(ROOM_MENU);
            break;
        }
    }

    private void load(){
        try {
            String[] lines = Files.readString(Paths.get(wordsFile)).split(Pattern.quote("\r\n"));
            words = new String[lines.length];
            times = new long[lines.length*2];
            for(int i = 0; i < lines.length; i++){
                String[] parts = lines[i].split(Pattern.quote(" "));
                words[i] = parts[0].replace("_", " ").replace("|", "\n");
                times[(i*2)] = Long.parseLong(parts[1]);
                times[(i*2)+1] = Long.parseLong(parts[2]);
            }
        } catch (IOException ex) {
            Logger.getLogger(EasterGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        counter = 0;
        
        Controller.setEasterCheck(true);
        song = SoundPlayer.play(audioFile);
        StarterTime = System.currentTimeMillis();
    }
    
    
    @Override
    public void drag_end() {
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
        back.draw();
        text.draw();
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
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }
    

    
}
