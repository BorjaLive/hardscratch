package hardscratch.elements.piezes.GUIs;

import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.backend.BUCKLE;
import hardscratch.base.Element;
import hardscratch.base.shapes.Image;
import hardscratch.base.shapes.Shape_Square;
import hardscratch.elements.piezes.Simulation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SimulateGUI extends Element{
    
    private Shape_Square backColor, backBoard;
    private final int X, Y, width, height, unit;
    
    private boolean started, reset;
    private String memory;
    private String[][] implementation;

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
        addImage(new Image(0, 0, 2, Global.TEXTURE_BORELICIOUS, (5*unit)/254f, Global.COLOR_WHITE), Y+unit*15, X+(int) (unit*2.5f));
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
        
        Controller.addToBoard(new SimulatedClock(X+(12*unit), Y+(3*unit), unit, "CLOCK"));
    }
    
    private String vmess(String mode, String data){
        Runtime rt = Runtime.getRuntime();
        Process proc;
        String returned = "";
        
        try {
            proc = rt.exec(Global.VMESS_EXE+" "+mode+" "+Global.getProyectFolder()+" "+data);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s;
            while ((s = stdInput.readLine()) != null)
                returned += s+"\n";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return returned;
    }
    
    @Override
    public void action(int action) {
        switch(action){
            case Global.EVENT_DRAW_BACKGROUND:
                backColor.draw();
                backBoard.draw();
            break;
            case Global.EVENT_DRAW_FLOD:
                //No hay flod
            break;
            case Global.EVENT_GO_HOMO:
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
    
    public void simulationStep(String[][] data){
        String[][] original = new String[data.length][data[0].length];
        for(int i = 0; i < data.length; i++){
            original[i][0] = data[i][0];
            original[i][1] = data[i][1];
            //System.out.println(data[i][0]+"  "+data[i][1]);
        }
        
        boolean power = getInputsData(data, "POWER").equals("ON");
        boolean reseting = getInputsData(data, "RESET").equals("ON");
        String startedLog = null;
        
        if(started && !power){
            //Apagado.
                started = false;
                setInputsData(data, "LCD", "");
                for(int i = 1; i <= 8; i++)
                    setInputsData(data, "LED"+i, "OFF");
                setInputsData(data, "BCD1", "10");
                setInputsData(data, "BCD2", "10");
        }else if(!started && power){
            //Encendido
            started = true;
            setInputsData(data, "LCD", "STARTING BOARD\nBUILDING PROGRAM");
            startedLog = vmess("check","");
            if(startedLog != null){
                //Comprobar errores
            }
            startedLog = vmess("init",getInputs(data)+memory);
        }else{
            if(started && !reset && !reseting){
                if(!getInputsData(data, "LCD").equals("PROGRAM RUNNING"))
                    setInputsData(data, "LCD", "PROGRAM RUNNING");
                //ACT
                startedLog = vmess("sim",getInputs(data)+memory);
                System.out.println("MANDO: "+getInputs(data)+memory+"\nRECIVO: "+startedLog);
            }else if(started && !reset && reseting){
                reset = true;
            }else if(started && reset && !reseting){
                //Resetear
                reset = false;
                setInputsData(data, "LCD", "RESTARTING BOARD");
                startedLog = vmess("init",getInputs(data)+memory);
            }
        }
        
        
        //Comprobar fallos de startedLog
        if(startedLog != null){
            if(startedLog.contains("ERROR")){
                //Comprobar errores
            }
            
            String[] parts = startedLog.split(Pattern.quote("{}"));
            memory = parts[1];
            makeChanges(data, parts[0]);
        }
        
        
        if(hasChanges(original, data)){
            //System.out.println("CHANGES ARE MADE");
            Controller.simulationSet(data);
        }
    }
    private String getInputsData(String[][] data, String identifer){
        for(int i = 0; i < data.length; i++)
            if(data[i][0].equals(identifer)) return data[i][1];
        return null;
    }
    private void setInputsData(String[][] data, String identifer, String value){
        for(int i = 0; i < data.length; i++)
            if(data[i][0].equals(identifer)){
                data[i][1] = value;
                return;
            } 
    }
    private boolean hasChanges(String[][] data1, String[][] data2){
        /*
        System.out.println("START");
        for(String[] dat:data1)
            System.out.println(dat[0]+"  "+dat[1]);
        System.out.println("SECOND");
        for(String[] dat:data2)
            System.out.println(dat[0]+"  "+dat[1]);
        System.out.println("FINALE");
        */
        
        for(String[] dat:data1){
            if(!getInputsData(data2,dat[0]).equals(dat[1])) return true;
        }
        return false;
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
                    setInputsData(data,conn[0], value);
                }
            }
            
        }
    }
    private String getInputs(String[][] state){
        String data = "";
        ArrayList<String> agregados = new ArrayList<>();
        ArrayList<String[]> arrays = new ArrayList<>();
        
        for(String[] conn:implementation){
            if(!conn[1].equals("-1") && (conn[0].contains("SWITCH") || conn[0].contains("BUTTON") || conn[0].contains("CLOCK"))){
                if(conn[1].contains("SA:")){
                    String[] parts = conn[1].split(Pattern.quote(":"));
                    arrays = addPiece(arrays, parts[2], getInputsData(state, conn[0]).equals("ON")?"1":"0", Integer.parseInt(parts[1]));
                }else{
                    if(!agregados.contains(conn[1])){
                        data += conn[1]+":"+(getInputsData(state, conn[0]).equals("ON")?"'1'":"'0'")+"|";
                        agregados.add(conn[1]);
                    }
                }
            }
        }
        for(String[] array:arrays){
            data += array[0]+":\""+array[1]+"\"|";
        }
        
        return data;
    }
    private ArrayList<String[]> addPiece(ArrayList<String[]> arrays, String name, String value, int pos){
        for(String[] array:arrays){
            if(array[0].equals(name)){
                array[1] = addTinyPiece(array[1], pos, value);
                return arrays;
            }
        }
        
        String[] array = new String[]{name,addTinyPiece("", pos, value)};
        arrays.add(array);
        return arrays;
        
    }
    private String addTinyPiece(String text, int pos, String value){
        while(pos >= text.length())
            text += "U";
        
        char[] chars = text.toCharArray();
        chars[pos] = value.charAt(0);
        
        return Arrays.toString(chars);
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
