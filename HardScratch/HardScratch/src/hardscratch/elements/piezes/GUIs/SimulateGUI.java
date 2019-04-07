package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.backend.BUCKLE;
import hardscratch.base.Element;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.elements.piezes.Simulation.*;
import static hardscratch.Global.*;
import hardscratch.backend.VMESS;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SimulateGUI extends Element{
    
    private Shape_Square backColor, backBoard;
    private Image boreliciousLogo;
    private final int X, Y, width, height, unit;
    
    private boolean started, reset;
    private String memory;
    private String[][] implementation;
    private int todo;           //Accion a realizar
    private String[][] data;

    public SimulateGUI() {
        super(0, 0, -1, true, true, false);
        depth = 1;
        
        Global.addGUIframe(this);
        backColor = new Shape_Square(0, 0, Global.COLOR_GUI_1, 1, 5, Global.WINDOW_WIDTH, Global.WINDOW_WIDTH);
        
        height = (int) ((Global.WINDOW_HEIGHT-70)*0.9);
        width = (int) ((Global.WINDOW_HEIGHT-70)*1.4);
        Y = 70+(Global.WINDOW_HEIGHT-height-70)/2;
        X = (Global.WINDOW_WIDTH-width)/2;
        unit = width/20;
        
        started = false;reset = false;
        implementation = BUCKLE.getImplement();
        
        backBoard = new Shape_Square(X, Y, Global.COLOR_CIRCUIT_GREEN, 1, 5, width, height);
        boreliciousLogo = new Image(Y+unit*15, X+(int) (unit*2.5f), 2, Global.TEXTURE_BORELICIOUS, (5*unit)/254f, Global.COLOR_WHITE);
    }
    
    private void createElectronics(){
        Controller.addToBoard(new SimulateSwitch(X+(unit), Y+(unit), unit, "POWER"));
        for(int i = 0; i < 4; i++)
            Controller.addToBoard(new SimulateSwitch((int) (X+(unit*(14+(i*1.5)))), Y+height-(unit*3), unit, "SWITCH"+(4-i)));
        
        Controller.addToBoard(new SimulatedButton((int) (X+(unit*2.25f)), (int) (Y+height-(unit*4.5f)), unit, "BUTTON1"));
        Controller.addToBoard(new SimulatedButton((int) (X+(unit*3.5f)), (int) (Y+height-(unit*3.25f)), unit, "BUTTON2"));
        Controller.addToBoard(new SimulatedButton((int) (X+(unit*2.25f)), (int) (Y+height-(unit*2)), unit, "BUTTON3"));
        Controller.addToBoard(new SimulatedButton((int) (X+unit), (int) (Y+height-(unit*3.25f)), unit, "BUTTON4"));
        
        for(int i = 0; i < 8; i++)
            Controller.addToBoard(new SimulatedLed(X+width-(unit*(i+1)), Y+height-(unit*6), unit, "LED"+(i+1)));
        
        Controller.addToBoard(new SimulatedBCD(X+(unit*3), Y+(unit*4), unit, "BCD1"));
        Controller.addToBoard(new SimulatedBCD(X+(unit*6), Y+(unit*4), unit, "BCD2"));
        
        Controller.addToBoard(new SimulatedLCD( X+(unit*6), Y+height-(unit*3), unit, "LCD"));
        
        Controller.addToBoard(new SimulatedButton((int) X+(unit*18), Y+unit, unit, "RESET"));
        
        Controller.addToBoard(new SimulatedClock(X+(12*unit), Y+(3*unit), unit, "CLOCK1"));
    }
    
    
    @Override
    public void action(int action) {
        switch(action){
            case Global.EVENT_DRAW_BACKGROUND:
                backColor.draw();
                backBoard.draw();
                boreliciousLogo.draw();
            break;
            case Global.EVENT_DRAW_FLOD:
                //No hay flod
            break;
            case Global.EVENT_GO_HOMO:
                Controller.changeRoom(ROOM_MENU);
            break;
            case Global.EVENT_SAVE:
                BUCKLE.save();
            break;
            case Global.EVENT_GO_DESIGN:
                Controller.changeRoom(Global.ROOM_DESIGN);
            break;
            case Global.EVENT_GO_IMPLEMENT:
                Controller.changeRoom(Global.ROOM_IMPLEMENT);
            break;
            case Global.EVENT_GO_SIMULATE:
            break;
            case Global.EVENT_CREATE_SPARTAN:
                createElectronics();
            break;
        }
    }
    
    public void simulationStep(String[][] dat){
        data = dat;
        
        boolean power = getInputsData("POWER").equals("ON");
        boolean reseting = getInputsData("RESET").equals("ON");
        
        if(started && !power){
            //Apagado.
                started = false;
                todo = 0;
                setInputsData("LCD", "");
                for(int i = 1; i <= 8; i++)
                    setInputsData("LED"+i, "OFF");
                setInputsData("BCD1", "10");
                setInputsData("BCD2", "10");
        }else if(!started && power){
            //Encendido
            started = true;
            todo = 1;
        }else{
            if(started && !reset && !reseting){
                //ACT
            }else if(started && !reset && reseting){
                reset = true;
            }else if(started && reset && !reseting){
                //Resetear
                reset = false;
                todo = 4;
            }
        }
        
        //System.out.println("TODO: "+todo);
        if(started && todo == 0){
            setInputsData("LCD", "COMPUTING...");
            //Mouse.setBlock(true);
            todo = 7;
            //System.out.println("CHANGES ARE MADE");
            //Controller.simulationSet(data)
        }else if(started){
            switch(todo){
                case 1:     //Inicio de Check
                    setInputsData("LCD", "BUILDING\nPROGRAM");
                    todo = 2;
                break;
                case 2:
                    memory = null;
                    //detectError(vmess("check",""));
                    new VMESS("check","", this, 3).start();
                    todo = 0;
                break;
                case 3:
                    setInputsData("LCD", "BUILD\nSUCCESSFUL");
                    todo = 4;
                break;
                case 4:     //Inicio de Init
                    setInputsData("LCD", "STARTING\nSIMULATION");
                    todo = 5;
                break;
                case 5:
                    memory = null;
                    //detectError(vmess("init",""));
                    new VMESS("init","", this, 6).start();
                    todo = 0;
                break;
                case 6:
                    setInputsData("LCD", "SIMULATION\nSTARTED");
                    todo = 8;
                break;
                case 7:
                   todo = 8;//Parece que esto no hace nada, pero es la solucion a todos los problemas
                break;
                case 8:
                    String send = getInputs()+memory;
                    //String log = vmess("sim",send);
                    
                    new VMESS("sim",send, this, 0).start();
                    
                    
            
                    todo = 0;//Acaba aqu'i
                break;
                case 9: //Este no se usa
                    setInputsData("LCD", "SIMULATION\nRUNNING");
                    //Mouse.setBlock(false);
                    todo = 0;
                break;
            }
        }
        
        Controller.simulationSet(data);
    }
    
    private String getInputsData(String identifer){
        for(int i = 0; i < data.length; i++)
            if(data[i][0].equals(identifer)) return data[i][1];
        return null;
    }
    private void setInputsData(String identifer, String value){
        for(int i = 0; i < data.length; i++)
            if(data[i][0].equals(identifer)){
                data[i][1] = value;
                return;
            } 
    }
    public void detectError(String text){
        if(text == null || text.isEmpty()) return;
        if(text.contains("Roaming/HardScratch"))
            throwError(666, -1); //Error inesperado
        String[] parts = text.split(Pattern.quote(":"));
        if(parts.length != 3 || !parts[0].equals("ERROR")) return;
        try{
            throwError(Integer.valueOf(parts[1]),Integer.valueOf(parts[2]));
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            System.out.println("ERROR INESPERADO: "+parts[1]);
            throwError(666, -1);//Error inesperado
        }
    }
    private void throwError(int code, int id){
        if(code == 0 && id == 0) return; //BOKEY
        if(code < ERROR_NAME.length){
            Controller.setError(code, id);
        }else{
            System.out.println("Este error inesperado es: "+code+" para ID "+id);
        }
       //TODO: Throw Error
    }
    public void simResoult(String log){
        if(log.contains("ERROR")){
            detectError(log);
            return;
        }
        
        //Trim log hasta []
        if(log.contains("[]")){
            log = log.substring(log.indexOf("[]")+2);
            if(log.contains("{}")){
                String[] parts = log.split(Pattern.quote("{}"));
                if(!parts[1].equals("-1"))
                    memory = parts[1];
                if(!parts[0].equals("-1"))
                    makeChanges(data, parts[0]);
            }
        }
        
        setInputsData("LCD", "SIMULATION\nRUNNING");
        
        Controller.simulationSet(data);
    }
    
    public int hasTodo(){
        return todo;
    }
    public void setTodo(int t){
        todo = t;
    }
    private void makeChanges(String[][] data, String output){
        String[] outputs = output.split(Pattern.quote("|"));
        
        for(String[] conn:implementation){
            if(!conn[1].equals("-1")){
                String varName = "";//Nombre a buscar
                int pos = -1;       //Posicion en caso de subArray
                String value = "";  //valor a asignar en data
                if(conn[1].contains("SA:")){
                    String[] parts = conn[1].split(Pattern.quote(":"));
                    varName = parts[2];
                    pos = Integer.parseInt(parts[1]);
                }else{
                    varName = conn[1];
                }
                for(String out:outputs){
                    String[] parts = out.split(Pattern.quote(":"));
                    if(parts[0].equals(varName)){
                        value = parts[1];
                        break;
                    }
                }
                if(!value.isEmpty()){
                    if(conn[0].contains("LED") || conn[0].contains("CLOCK")){
                        if(pos == -1){
                            if(value.equals("'1'"))
                                value = "ON";
                            else
                                value = "OFF";
                        }else{
                            if(value.charAt(pos+1) == '1')
                                value = "ON";
                            else
                                value = "OFF";
                        }
                    }
                    setInputsData(conn[0], value);
                }
            }
            
        }
    }
    private String getInputs(){
        String test = "";
        ArrayList<String> agregados = new ArrayList<>();
        ArrayList<String[]> arrays = new ArrayList<>();
        
        for(String[] conn:implementation){
            if(conn == null) continue;
            
            if(!conn[1].equals("-1") && (conn[0].contains("SWITCH") || conn[0].contains("BUTTON") || conn[0].contains("CLOCK"))){
                if(conn[1].contains("SA:")){
                    String[] parts = conn[1].split(Pattern.quote(":"));
                    addPiece(arrays, parts[2], getInputsData(conn[0]).equals("ON")?"1":"0", Integer.parseInt(parts[1]));
                }else{
                    if(!agregados.contains(conn[1])){
                        test += conn[1]+":"+(getInputsData(conn[0]).equals("ON")?"'1'":"'0'")+"|";
                        agregados.add(conn[1]);
                    }
                }
            }
        }
        for(String[] array:arrays){
            test += array[0]+":\""+array[1]+"\"|";
        }
        
        
        return test.replace("\"", "'");
    }
    private void addPiece(ArrayList<String[]> arrays, String name, String value, int pos){
        //System.out.println("ME DICEN QUE AGREGE A "+name+" un "+value+" en "+pos);
        for(String[] array:arrays){
            if(array[0].equals(name)){
                array[1] = addTinyPiece(array[1], pos, value);
                return;
            }
        }
        
        arrays.add(new String[]{name,addTinyPiece("", pos, value)});
        
    }
    private String addTinyPiece(String text, int pos, String value){
        //System.out.print("TENGO "+text+" Y LE AGREGO "+value+" EN "+pos);
        while(pos >= text.length())
            text += "U";
        
        char[] chars = text.toCharArray();
        chars[pos] = value.charAt(0);
        
        //System.out.println("  RESOLTADO: "+(new String(chars)));
        return new String(chars);
    }
    
    @Override
    protected long colideExtra(int x, int y) {
        return -1;
    }
    @Override
    public void updateEvent(int event, int data1, int data2, String data3) {
    }
    @Override
    public void latWish() {
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
}