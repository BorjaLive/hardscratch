package hardscratch.base;

import java.util.ArrayList;

public class SoundPlayer {
    
    private static ArrayList<Sound> sounds = new ArrayList<>();
    
    public static Sound play(String name){
        Sound s = new Sound(name);
        sounds.add(s);
        return s;
    }
    
    public static void stop(){
        for(Sound s:sounds)
            s.stopPlaying();
        sounds.clear();
    }
    
    public static void stop(Sound s){
        if(s.isPlaying())
            s.stopPlaying();
        sounds.remove(s);
    }
}
