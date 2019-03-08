package hardscratch.elements.piezes.Simulation;

import hardscratch.base.Element;

public abstract class Simulated extends Element{

    protected final int unit;
    private String name;
    
    public Simulated(int x, int y, int unit, String name) {
        super(x, y, -1, true, true, false);
        this.unit = unit;
        this.name = name;
    }
    
    @Override
    public final void move(int x, int y){
        //No te muevas
    }
    
    public final boolean is(String t){
        return name.equals(t);
    }

    @Override
    protected final long colideExtra(int x, int y) {
        return -1;
    }

    @Override
    protected final void moveExtra(int x, int y) {
    }

    @Override
    protected final void drawExtra() {
    }

    @Override
    protected void select_init(long ID) {
    }

    @Override
    protected void select_end() {
    }

    @Override
    protected final void selected_loop() {
    }

    @Override
    public final boolean drag_loop() {
        return true;
    }

    @Override
    public final void drag_init() {
        select_init(-1);
    }

    @Override
    public final void drag_end() {
        select_end();
    }

    @Override
    public abstract void action(int action);

    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }

    @Override
    public final void latWish() {
    }
    
}
