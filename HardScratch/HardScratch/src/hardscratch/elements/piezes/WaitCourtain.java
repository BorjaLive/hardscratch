package hardscratch.elements.piezes;

import hardscratch.base.shapes.Texture;
import static hardscratch.Global.*;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.Shape_Square;

public class WaitCourtain{
    
    private static Image images[];
    private static Shape_Square background;
    private static int counter, Nmax;
    
    public static void load(String folder, int Nfiles, float[] bgColor){
        background = new Shape_Square(0, 70, bgColor, 1, 5, WINDOW_WIDTH, WINDOW_HEIGHT-70);
        Nmax = Nfiles;
        
        int size = (int) (WINDOW_HEIGHT*0.6f);
        int posX = (WINDOW_WIDTH-size)/2, posY = (int) (WINDOW_HEIGHT*0.2f);
        
        images = new Image[Nfiles];
        for(int i = 0; i < Nfiles; i++)
            images[i] = new Image(posX, posY, 3, new Texture(folder+i+".png"), size/512f, COLOR_WHITE);
    }
    
    public static void drawFrame(){
        background.draw();
        images[counter].draw();
        counter++;
        if(counter == Nmax)
            counter = 0;
    }
    
}
