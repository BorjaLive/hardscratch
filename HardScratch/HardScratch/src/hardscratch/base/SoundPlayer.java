package hardscratch.base;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {
    
    private static Clip clip;
    
    public static void play(String sound){
        stop();
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(0f);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void stop(){
        if (clip != null && clip.isOpen()) clip.close();
    }
}
