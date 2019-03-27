package hardscratch.backend;
//HardWork - BUCKLE: B0vE's Unnecessary Complicated marKup LanguajE

import hardscratch.elements.piezes.Design.*;
import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.*;
import hardscratch.elements.piezes.Constructor;
import hardscratch.elements.piezes.Hole;
import hardscratch.elements.piezes.Implementation.Implementer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class BUCKLE {
    /*
        BUCKLE ENCODING RULES
        -Everithing is uppercase
        -Linebrakes are ignored
        -No recursive search
        -Only two separators
    
        ELEMENT SEPARATOR
        DIVIDE {}          {} ... {}
        ELEMNT NOMENCLATURE
        (prefix)|(data1)|(data2)| ... (dataN)       IE: "DEC:500|200|2|-1|-1"
    
        DECLARATOR
        data1:  IDENTIFICATOR       DEC
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  TIP Var inout       Constant code or -1
        data6:  TIP Var type        Constant code or -1
        data7:  TEXT name           String
        data8:  ID dock extravar    Integer or -1
        data9:  ID dock inicializer Integer or -1
    
        INICIALIZER
        data1:  IDENTIFICATOR       INITER
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  Constructor I       Constructor string
        data6:  ID dock declarator  Integer or -1
    
        EXTRAVAR
        data1:  IDENTIFICATOR       EXTRAVAR
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  Text name           String
        data6:  ID dock declarator  Integer or -1
        data7:  ID dock inicializer Integer or -1
        data8:  ID dock extravar    Integer or -1
    
        CONVERTER
        data1:  IDENTIFICATOR       CONV
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  Var 1               Var name string
        data6:  Var 2               Var name string
    
        ASIGNATOR
        data1:  IDENTIFICATOR       ASIG
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  Var                 Var name string
        data6:  Constructor A       Constructor string
    
        SET IF
        data1:  IDENTIFICATOR       SETIF
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  Var                 Var name string
        data6:  Constructor A else  Constructor string
        data7+i:Constructor E       Constructor string
        data8+i:Constructor A       Constructor string
    
        SET SWITCH
        data1:  IDENTIFICATOR       SETSWITCH
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  Var set             Var name string
        data6:  Var switch          Var name string
        data7:  Constructor A else  Constructor string
        data8+i:Constructor A       Constructor string
        data9+i:Constructor A       Constructor string
    
        SEQUENCER
        data1:  IDENTIFICATOR       SEQ
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  ID dock SEQ-P F     Integer or -1
    
        SEQ ASIGNATOR
        data1:  IDENTIFICATOR       SASIG
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  ID dock SEQ-P M     Integer or -1
        data6:  ID dock SEQ-P F     Integer or -1
        data7:  Var                 Var name string
        data8:  Constructor A       Constructor string
    
        SEQ IFTHEN
        data1:  IDENTIFICATOR       IFTHEN
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  ID dock SEQ-P M     Integer or -1
        data6:  ID dock SEQ-P F     Integer or -1
        data7:  Var                 Var name string
        data8:  ID dock SEQ-P else  Integer or -1
        data9+i:Constructor A       Constructor string
        data10+i: ID dock SEQ-P     Integer or -1
    
        SEQ SWICHCASE
        data1:  IDENTIFICATOR       SWITCH
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  ID dock SEQ-P M     Integer or -1
        data6:  ID dock SEQ-P F     Integer or -1
        data7:  Var                 Var name string
        data8:  ID dock SEQ-P else  Integer or -1
        data9+i: Constructor A      Constructor string
        data10+i: ID dock SEQ-P     Integer or -1
    
        SEQ WAITFOR
        data1:  IDENTIFICATOR       WFOR
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  ID dock SEQ-P M     Integer or -1
        data6:  ID dock SEQ-P F     Integer or -1
        data7:  Constructor A       Constructor string
    
        SEQ WAITFOR
        data1:  IDENTIFICATOR       WON
        data2:  ID self             Integer
        data3:  X Absolute cord     Integer
        data4:  Y Absolute cord     Integer
        data5:  ID dock SEQ-P M     Integer or -1
        data6:  ID dock SEQ-P F     Integer or -1
        data7:  Var                 Var name string
        data8:  TIP edge            Constant code or -1
    
        SEQ FORNEXT
        work in progress
    
    
        IMPLEMENTER
        data1:  IDENTIFICATOR       IMPL
        data2:  Number I            Integer
        data3:  Variable            String name
    */
    
    
    private static String tmp;
    
    private static String[] lines, data;
    
    public static void save(){
        switch(Controller.getRoom()){
            case Global.ROOM_DESIGN: saveDesign();BUMLAY.save();break;
            case Global.ROOM_IMPLEMENT: saveImplement();break;
        }
    }
    
    public static void saveDesign(){
        ArrayList<Element> list = Controller.getBoardAll();
        if(list == null || list.isEmpty()) return;
        wipe();
        
        String data[];
        for(Element e:list){
            if(e.getClass() == Declarator.class){
                data = new String[9];
                data[0] = "DEC";
                
                data[4] = hole2string(e.getHole(0));
                data[5] = hole2string(e.getHole(1));
                data[6] = e.getBox(0).getText();
                data[7] = port2string(e.getPort(0));
                data[8] = port2string(e.getPort(1));
            }else if(e.getClass() == Inicializer.class){
                data = new String[6];
                data[0] = "INITER";
                
                data[4] = creator2string(e.getCreator(0));
                data[5] = port2string(e.getPort(0));
            }else if(e.getClass() == ExtraVar.class){
                data = new String[8];
                data[0] = "EXTRAVAR";
                
                data[4] = e.getBox(0).getText();
                data[5] = port2string(e.getPort(0));
                data[6] = port2string(e.getPort(1));
                data[7] = port2string(e.getPort(2));
            }else if(e.getClass() == Converter.class){
                data = new String[6];
                data[0] = "CONV";
                
                data[4] = var2string(e.getHole(0));
                data[5] = var2string(e.getHole(1));
            }else if(e.getClass() == Asignator.class){
                data = new String[6];
                data[0] = "ASIG";
                
                data[4] = var2string(e.getHole(0));
                data[5] = creator2string(e.getCreator(0));
            }else if(e.getClass() == SetIf.class){
                SetIf s = (SetIf) e;
                
                data = new String[4+(s.getValues().size()*2)];
                data[0] = "SETIF";
                
                data[4] = var2string(e.getHole(0));
                String[] operations = convergent2string(s.getExpresions(), s.getValues());
                for(int i = 0; i < operations.length; i++)
                    data[i+5] = operations[i];
                
            }else if(e.getClass() == SetSwitch.class){
                SetSwitch s = (SetSwitch) e;
                
                data = new String[5+(s.getValues().size()*2)];
                data[0] = "SETSWITCH";
                
                data[4] = var2string(e.getHole(0));
                data[5] = var2string(e.getHole(1));
                String[] operations = convergent2string(s.getExpresions(), s.getValues());
                for(int i = 0; i < operations.length; i++)
                    data[i+6] = operations[i];
                
            }else if(e.getClass() == Sequential.class){
                data = new String[5];
                data[0] = "SEQ";
                
                data[4] = port2string(e.getPort(0));
            }else if(e.getClass() == AsignatorSEQ.class){
                data = new String[8];
                data[0] = "SASIG";
                data[4] = port2string(e.getPort(0));
                data[5] = port2string(e.getPort(1));
                
                data[6] = var2string(e.getHole(0));
                data[7] = creator2string(e.getCreator(0));
                
            }else if(e.getClass() == IfThen.class){
                IfThen s = (IfThen) e;
                
                data = new String[5+(s.getInstructionsPort().size()*2)];
                data[0] = "IFTHEN";
                data[4] = port2string(e.getPort(0));
                data[5] = port2string(e.getPort(1));
                
                String[] operations = convergentSeq2string(s.getConditions(), s.getInstructionsPort());//Fixme
                
                for(int i = 0; i < operations.length; i++)
                    data[i+6] = operations[i];
                
            }else if(e.getClass() == SwitchCase.class){
                SwitchCase s = (SwitchCase) e;
                
                data = new String[6+(s.getInstructionsPort().size()*2)];
                data[0] = "SWITCH";
                data[4] = port2string(e.getPort(0));
                data[5] = port2string(e.getPort(1));
                
                data[6] = var2string(e.getHole(0));
                String[] operations = convergentSeq2string(s.getConditions(), s.getInstructionsPort());
                for(int i = 0; i < operations.length; i++)
                    data[i+7] = operations[i];
                
            }else if(e.getClass() == WaitFor.class){
                data = new String[7];
                data[0] = "WFOR";
                
                data[4] = port2string(e.getPort(0));
                data[5] = port2string(e.getPort(1));
                
                data[6] = creator2string(e.getCreator(0));
            }else if(e.getClass() == WaitOn.class){
                data = new String[8];
                data[0] = "WON";
                
                data[4] = port2string(e.getPort(0));
                data[5] = port2string(e.getPort(1));
                
                data[6] = var2string(e.getHole(1));
                data[7] = hole2string(e.getHole(0));
            }else if(e.getClass() == ForNext.class){
                data = new String[4];
                //work in progress
            }else{
                data = new String[4];
                data[0] = "ERROR";
                System.out.println("SE NOS HA COLADO UN:" + e.getClass());
                break;
            }
            data[1] = Long.toString(e.getID());
            data[2] = Integer.toString(e.getX());
            data[3] = Integer.toString(e.getY());
            add(data);
        }
        
        write("board");
    }
    public static void saveImplement(){
        wipe();
        
        Implementer Im;
        int i = 0;
        Element e = Controller.getFromBoard(i);
        do{
            if(e.getClass() == Implementer.class){
                Im = (Implementer) e;
                add(new String[]{"IMPL",Im.getIdentifier(),var2string(Im.getHole(0))});
            }
            e = Controller.getFromBoard(i);
            i++;
        }while(e != null);
        
        write("implement");
    }
    
    private static void wipe(){tmp = "";}
    private static void add(String s[]){
        tmp += (tmp==""?"":"{}")+Global.concatenate(s,"|")+"\n";
    }
    
    private static String[] convergentSeq2string(ArrayList<Constructor> conditions, ArrayList<Port> instructions){
        String list[] = new String[(instructions.size()*2)-1];
        
        for(int i = 0; i < instructions.size(); i++){
            if(i != 0)
                list[(i*2)-1] = creator2string(conditions.get(i-1));
            list[i*2] = port2string(instructions.get(i));
        }
        
        return list;
    }
    private static String[] convergent2string(ArrayList<Constructor> expresions, ArrayList<Constructor> values){
        String list[] = new String[(values.size()*2)-1];
        
        for(int i = 0; i < values.size(); i++){
            if(i != 0)
                list[(i*2)-1] = creator2string(expresions.get(i-1));
            list[i*2] = creator2string(values.get(i));
        }
        
        return list;
    }
    private static String var2string(Hole h){
        if(h.isAsigned())
            if(h.getTipType() == Global.TIP_VAR)
                return h.getTip().getVar().name;
            else if(h.getTipType() == Global.TIP_VAR_SUBARRAY){
                String sub = h.getTip().getBox(0).getText();
                if(sub.isEmpty()) sub = "100";
                return "SA:"+sub+":"+h.getTip().getVar().name;
            }
        return "-1";
    }
    private static String hole2string(Hole h){
        if(h.isAsigned()){
            int type = h.getTipType();
            String value = Integer.toString(type);
            if(type == Global.TIP_VAR_ARRAY)
                value += ":"+h.getTip().getBox(0).getText();
            return value;
        }
        else return "-1";
    }
    private static String port2string(Port p){
        if(p.isOcupied())
            return Long.toString(p.getDock().getID());
        else return "-1";
    }
    private static String creator2string(Constructor c){
        if(c == null) return "-1";
        return c.retriveData();
    }
    
    
    private static void write(String file){
        //System.out.println(tmp);
        
        try {
            Files.write(Paths.get(Global.getProyectFolder()+"/"+file+".b0ve"), tmp.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    //Si el archivo que lee tiene errores dira "Somebody touched my spagety"
    public static void load(){
        switch(Controller.getRoom()){
            case Global.ROOM_DESIGN: loadDesign();break;
            case Global.ROOM_IMPLEMENT: loadImplement();break;
        }
    }
    
    public static void loadDesign(){
        try {
            File file = new File(Global.getProyectFolder()+"/board.b0ve");
            if(!file.exists()) return;
            
            lines = Files.readString(Paths.get(Global.getProyectFolder()+"/board.b0ve")).replace("\n", "").replace("\r", "").split(Pattern.quote("{}"));
            
            Controller.initBUCKLEload(lines.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void partLoadDesign(int stage, int nLine){
        switch(stage){
            case 1://Pre pasada: Comprobar que nadie toque mi espagueti
                   //Primera pasada: Crear los objetos y verificar la integridad
                data = lines[nLine].split(Pattern.quote("|"));
                //Ejecutar linea por linea
                if(data[0].equals("DEC")){  if(data.length != 9){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new Declarator(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1]),data[6], data[4], data[5]));
                }else if(data[0].equals("INITER")){  if(data.length != 6){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new Inicializer(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("EXTRAVAR")){  if(data.length != 8){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new ExtraVar(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1]), data[4]));
                }else if(data[0].equals("CONV")){  if(data.length != 6){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new Converter(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("ASIG")){  if(data.length != 6){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new Asignator(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("SETIF")){  if(data.length < 6 || data.length%2!=0){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new SetIf(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("SETSWITCH")){  if(data.length < 7 || data.length%2==0){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new SetSwitch(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("SEQ")){  if(data.length != 5){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new Sequential(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("SASIG")){  if(data.length != 8){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new AsignatorSEQ(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("IFTHEN")){  if(data.length < 7 || data.length%2==0){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new IfThen(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("SWITCH")){  if(data.length < 8 || data.length%2!=0){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new SwitchCase(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("WFOR")){  if(data.length != 7){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new WaitFor(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1])));
                }else if(data[0].equals("WON")){  if(data.length != 8){ somebodyTouchedMySpagget(); return;}
                    Controller.addToBoard(new WaitOn(Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                            Integer.parseInt(data[1]), data[7]));
                }else if(data[0].equals("FORNEXT")){
                    //Work in progress
                }
                break;
            case 2://Segunda pasada: Asignar variables y docking de puertos
                data = lines[nLine].split(Pattern.quote("|"));
                //Ejecutar linea por linea
                if(data[0].equals("DEC")){
                    Controller.autoDock(data[1], data[7], 0, 0);
                    Controller.autoDock(data[1], data[8], 1, 0);
                }else if(data[0].equals("INITER")){
                    Controller.autoDock(data[1], data[5], 0, 1);

                }else if(data[0].equals("EXTRAVAR")){
                    Controller.autoDock(data[1], data[5], 0, 0);
                    Controller.autoDock(data[1], data[6], 1, 0);
                    Controller.autoDock(data[1], data[7], 2, 0);

                }else if(data[0].equals("SEQ")){
                    Controller.autoDock(data[1], data[4], 0, 0);

                }else if(data[0].equals("SASIG") || data[0].equals("IFTHEN") || data[0].equals("SWITCH") ||
                         data[0].equals("WFOR") || data[0].equals("WON") || data[0].equals("FORNEXT")){
                    Controller.autoDock(data[1], data[5], 1, 0);
                }

                if(data[0].equals("IFTHEN")){
                }else if(data[0].equals("SWITCH")){
                }
                break;
            case 3://Detectar variables
                Controller.loadVars();
                break;
            case 4://Tercera pasada: Agregar Tips de variables y constructores
                data = lines[nLine].split(Pattern.quote("|"));
                //Ejecutar linea por linea
                if(data[0].equals("DEC")){
                    
                }else if(data[0].equals("INITER")){
                    Controller.getElementByID(data[1]).getCreator(0).forceSet(data[4]);
                    
                }else if(data[0].equals("CONV")){
                    Controller.getElementByID(data[1]).getHole(0).forceAsign(data[4]);
                    Controller.getElementByID(data[1]).getHole(1).forceAsign(data[5]);
                    
                }else if(data[0].equals("ASIG")){
                    Controller.getElementByID(data[1]).getHole(0).forceAsign(data[4]);
                    Controller.getElementByID(data[1]).getCreator(0).forceSet(data[5]);
                    
                }else if(data[0].equals("SETIF")){
                    Element e = Controller.getElementByID(data[1]);
                    
                    e.getHole(0).forceAsign(data[4]);
                    for(int i = 5; i < data.length; i++)
                        e.getCreator(i-5).forceSet(data[i]);
                    
                }else if(data[0].equals("SETSWITCH")){
                    Element e = Controller.getElementByID(data[1]);
                    
                    e.getHole(0).forceAsign(data[4]);
                    e.getHole(1).forceAsign(data[5]);
                    
                    e.getHole(0).forceAsign(data[4]);
                    for(int i = 6; i < data.length; i++)
                        e.getCreator(i-6).forceSet(data[i]);
                    
                    
                }else if(data[0].equals("SASIG")){
                    Controller.getElementByID(data[1]).getHole(0).forceAsign(data[6]);
                    Controller.getElementByID(data[1]).getCreator(0).forceSet(data[7]);
                    
                }else if(data[0].equals("IFTHEN")){
                    Element e = Controller.getElementByID(data[1]);
                    
                    /*
                        data6:  ID dock SEQ-P else  Integer or -1
                        data7+i:Constructor A       Constructor string
                        data8+i:ID dock SEQ-P       Integer or -1
                    */
                    for(int i = 5; i < data.length; i+=2){
                        if(i != 5)
                            e.getCreator((i-5)/2).forceSet(data[i]);
                        
                        Controller.autoDock(data[1], data[i+1], (i-1)/2, 0);
                    }
                        
                    
                }else if(data[0].equals("SWITCH")){
                    Element e = Controller.getElementByID(data[1]);
                    
                    Controller.getElementByID(data[1]).getHole(0).forceAsign(data[6]);
                    for(int i = 6; i < data.length; i+=2){
                        if(i != 6)
                            e.getCreator((i-6)/2).forceSet(data[i]);
                        
                        Controller.autoDock(data[1], data[i+1], (i-2)/2, 0);
                    }
                    
                }else if(data[0].equals("WFOR")){
                    Controller.getElementByID(data[1]).getCreator(0).forceSet(data[6]);
                    
                }else if(data[0].equals("WON")){
                    Controller.getElementByID(data[1]).getHole(1).forceAsign(data[6]);
                    Controller.getElementByID(data[1]).getHole(0).forceAsign(data[7]);
                }
                break;
            case 5:
                ArrayList<Element> elements = Controller.getBoardAll();
                Port[] ports1, ports2;
                boolean changes;
                do{
                    changes = false;
                    for(Element e1:elements){
                        for(Element e2:elements){
                            ports1 = e1.getPorts();
                            ports2 = e2.getPorts();
                            for(Port p1:ports1)
                                for(Port p2:ports2)
                                    if(Controller.docking(e1, e2, p1, p2, true))
                                        changes = true;
                        }
                    }
                }while(changes);
                
                Controller.ElementsHolesCorrectTipPos();
                break;//TODO: Eliminar todos los dockings anteriores, este se lleva la palma. Pero no lo hagas, algo podria romperse.
        }
    }
    
    public static void loadImplement(){
        try {
            String[] lines = Files.readString(Paths.get(Global.getProyectFolder()+"/implement.b0ve")).replace("\n", "").replace("\r", "").split(Pattern.quote("{}"));
            String[] data;
            
            for(String line:lines){
                data = line.split(Pattern.quote("|"));
                if(data[0].equals("IMPL")){ if(data.length != 3){ somebodyTouchedMySpagget();return;}
                    Controller.getImplementerFromIdentifier(data[1]).getHole(0).forceAsign(data[2]);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[][] getImplement(){
        File file = new File(Global.getProyectFolder()+"/implement.b0ve");
        if(!file.exists())
                return new String[0][2];
        
        try {
            String[] lines = Files.readString(Path.of(file.getPath())).replace("\n", "").replace("\r", "").split(Pattern.quote("{}"));
            String[][] data = new String[lines.length][2];
            String[] parts;
            
            for(int i = 0; i < lines.length; i++){
                parts = lines[i].split(Pattern.quote("|"));
                if(parts[0].equals("IMPL")){ if(parts.length != 3){ somebodyTouchedMySpagget();return null;}
                    data[i][0] = parts[1];
                    data[i][1] = parts[2];
                }
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0][2];
    }
    
    
    private static void somebodyTouchedMySpagget(){
        System.err.println("\n\nSOMEBODY TOUCHED MY SPAGGET\n\n");
    }
}
