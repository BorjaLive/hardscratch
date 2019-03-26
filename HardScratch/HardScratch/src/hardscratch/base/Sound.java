package hardscratch.base;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    
    private String file;
    private Clip clip;
    
    public Sound(String file){
        try {
            this.file = file;
            clip = AudioSystem.getClip();
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.stop();
                    clip.close();
                    load();
                }
            });
            load();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void load(){
        try {
            clip.open(AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException  ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play() {
        clip.start();
    }
    public void stop() {
        clip.stop();
    }
    
}
