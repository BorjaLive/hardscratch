package hardscratch.backend;

import hardscratch.Global;
import static hardscratch.Global.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class CONF {
    
    public static final int
            THEME = 0,
            WINDOW = 1,
            RESOLUTION = 2
            ;
    
    private static int DATA[];
    
    public static void load(){
        checkFolder();
        
        File file = new File(System.getenv("APPDATA")+"/HardScratch/conf.B0vE");
        if(file.exists()){
            try {
                String[] lines = Files.readString(Paths.get(file.getPath())).replace("\n", "").replace("\r", "").split(Pattern.quote("|"));
                DATA = new int[lines.length];
                for(int i = 0; i < lines.length; i++)
                    DATA[i] = Integer.parseInt(lines[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            inicializate();
            write();
        }
    }
    private static void inicializate(){
        DATA = new int[]{1, 1, 0};
    }
    
    public static int get(int key){
        return DATA[key];
    }
    public static void set(int key, int value){
        DATA[key] = value;
    }
    
    
    public static void write(){
        checkFolder();
        
        File file = new File(System.getenv("APPDATA")+"/HardScratch/conf.B0vE");
        if(file.exists())
            file.delete();
        
        String data = concatenate(DATA, "|");
        try {
            Files.write(Path.of(file.getPath()), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private static void checkFolder(){
        File folder = new File(System.getenv("APPDATA")+"/HardScratch");
        if(!folder.exists())
            folder.mkdirs();
    }
}
