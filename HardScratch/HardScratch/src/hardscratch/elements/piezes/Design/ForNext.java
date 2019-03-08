package hardscratch.elements.piezes.Design;

import hardscratch.base.Element;
import hardscratch.inputs.Mouse;

public class ForNext extends Element{

    public ForNext(){
        this(Mouse.getX()-0, Mouse.getY()-0);
    }
    
    public ForNext(int x, int y){
        this(x, y, -1);
    }
    
    public ForNext(int x, int y, int id) {
        super(x, y, id, true, true, true);
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
        return true;
    }

    @Override
    public void drag_init() {
    }

    @Override
    public void drag_end() {
    }

    @Override
    public void action(int action) {
    }

    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public void latWish() {
    }
    
}
