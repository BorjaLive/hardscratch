package hardscratch.backend;
//HardWork - BUMLAY: B0vE's Useless Markup Languaje Ain't YAML (A.K. BUM: B0vE's Useless MarkupLanguaje)

import hardscratch.elements.piezes.Constructor;
import hardscratch.elements.piezes.Design.SetIf;
import hardscratch.elements.piezes.Design.Sequential;
import hardscratch.elements.piezes.Design.WaitOn;
import hardscratch.elements.piezes.Design.Asignator;
import hardscratch.elements.piezes.Design.AsignatorSEQ;
import hardscratch.elements.piezes.Design.SetSwitch;
import hardscratch.elements.piezes.Design.ForNext;
import hardscratch.elements.piezes.Design.Converter;
import hardscratch.elements.piezes.Design.ExtraVar;
import hardscratch.elements.piezes.Design.Declarator;
import hardscratch.elements.piezes.Design.IfThen;
import hardscratch.elements.piezes.Design.WaitFor;
import hardscratch.elements.piezes.Design.SwitchCase;
import hardscratch.Controller;
import hardscratch.Global;
import hardscratch.base.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
/*
    BUMLAY ENCODING RULES
    -Everithing is uppercase
    -Linebrakes are ignored

    ELEMENT SEPARATOR
    OPEN [ CLOSE ]          [ ... ]
    ELEMNT NOMENCLATURE
    (prefix):(data1)|(data2)| ... (dataN)       IE: "VAR:salida|1|2|-1|-1"
    
    VARIABLE
        prefix:     VAR
        data1:      NAME        Arbitary String
        data2:      INOUT       1 IN, 2 OUT, 3 SIGNAL, 4 CONST
        data3:      TYPE        1 INT, 2 BIT, 3 ARRAY
        data4:      ATRIBUTE    ARBITRARY NUMBER or -1
        data5:      INICIALICED CREATOR STRING or -1
    CONVERTER
        prefix:     CONV
        data1:      VAR1        String Varable name
        data2:      VAR2        String Varable name
    ASIGNATOR
        prefix:     ASIG
        data1:      VAR1        String Varable name
        data2:      VALUE       CREATOR STRING
    SETIF
        prefix:     SETIF
        data1:      VAR         String Varable name
        data(2n):   CONDITION   CREATOR STRING          --First one is ELSE
        data(2n+1): VALUE       CREATOR STRING
    SETSWITCH
        prefix:     SETSWITCH
        data1:      DESTINE     String Varable name
        data2:      SWITCH      String Varable name
        data(2n-1): CONDITION   CREATOR STRING          --First one is ELSE
        data(2n):   VALUE       CREATOR STRING
    SEQUENTIAL
        prefix:     SEQ
        data1:      INSTRUCTIONS    String Recursive search
    SEQ ASIGNATION
        prefix:     SASIG
        data1:      VAR1        String Varable name
        data2:      VALUE       CREATOR STRING
    SEQ IFTHEN
        prefix:     IFTHEN
        data(2n):   CONDITION   CREATOR STRING          --First one is ELSE
        data(2n+1): INSTRUCTION String Recursive search
    SEQ SWITCHCASE
        prefix:     SWITCH
        data1:      VAR         String Variable name
        data(2n-1): CONDITION   CREATOR STRING          --First one is ELSE
        data(2n):   INSTRUCTION String Recursive search
    SEQ WAITFOR
        prefix:     WFOR
        data1:      VALUE       CREATOR STRING
    SEQ WAITON
        prefix:     WON
        data1:      VAR         String Variable name
        data2:      EDGE        1 Rising, 2 Lowering
    SEQ FORNEXT
        work in progress


*/

public class BUMLAY {
    
    private static String tmp = "";
    
    public static void save(){
        wipe();
        
        ArrayList<Element> piezes = Controller.getBoard();
        if(piezes == null || piezes.size() == 0) write(Global.SAVE_NAME);
        
        for(Element e:piezes){
            if(e.getClass() == Declarator.class){
                //Buscar las variables
                if(!e.isComplete()) break;
                
                int inout = e.getHole(0).getTipType();
                int type = e.getHole(1).getTipType()-26;
                int param = -1;
                if(type == 3) param = Integer.parseInt(e.getHole(1).getTip().getInputText(0));
                String inicialized = "-1";
                if(e.getPort(1).isOcupied()) inicialized = creator2String(e.getPort(1).getDock().getCreator(0));
                
                ArrayList<String> extraName = null;
                ArrayList<Constructor> extraInicialized = null;
                if(e.getPort(0).isOcupied()){
                    ExtraVar extra = (ExtraVar) e.getPort(0).getDock();
                    extraName = extra.getVars();
                    extraInicialized = extra.getCreators();
                }
                if(extraName != null && extraInicialized != null && extraName.size() == extraInicialized.size()){
                    for(int i = 0; i < extraName.size(); i++)
                        if(extraName.get(i) != null)
                            addVAR(extraName.get(i), inout, type, param, creator2String(extraInicialized.get(i)));
                }
                
                addVAR(e.getBox(0).getText(), inout, type, param, inicialized);
            }else if(e.getClass() == Converter.class){
                //Buscar conversiones
                if(!e.isComplete()) break;
                
                addCONV(e.getHole(0).getTip().getVar().name, e.getHole(1).getTip().getVar().name);
            }else if(e.getClass() == Asignator.class){
                //Buscar asignaciones
                if(!e.isComplete()) break;
                
                addASIG(e.getHole(0).getTip().getVar().name, creator2String(e.getCreator(0)));
            }else if(e.getClass() == SetIf.class){
                //Buscar SETIFs
                SetIf s = (SetIf) e;
                
                addASETIF(s.getHole(0).getTip().getVar().name,creator2String(s.getExpresions()),creator2String(s.getValues()));
            }else if(e.getClass() == SetSwitch.class){
                //Buscar SETSWITCHs
                SetSwitch s = (SetSwitch) e;
                
                addASETSWITCH(s.getHole(0).getTip().getVar().name,s.getHole(1).getTip().getVar().name,
                              creator2String(s.getExpresions()),creator2String(s.getValues()));
            }else if(e.getClass() == Sequential.class){
                //Buscar secuenciales
                if(!e.getPort(0).isOcupied()) break;
                
                ArrayList<String> list = seqInterprete(e.getPort(0).getDock());
                Collections.reverse(list);
                String seq = "";
                for(String s:list)
                    seq += s;
                
                addSEQ(seq);
            }
        }
        
        
        
        
        
        
        write("circuit");
    }
    
    
    private static void wipe(){tmp = "";}
    private static void add(String prefix, String data){tmp += "[" + prefix + ":" + data +"]\n";};
    
    private static void addVAR(String name, int inout, int type, int param, String inicialized){
        add("VAR",name+"|"+inout+"|"+type+"|"+param+"|"+inicialized);
    }
    private static void addCONV(String var1, String var2){
        add("CONV",var1+"|"+var2);
    }
    private static void addASIG(String var, String value){
        add("ASIG",var+"|"+value);
    }
    private static void addASETIF(String var, ArrayList<String> expresions, ArrayList<String> values){
        String setif = var+"|ELSE|"+values.get(0);
        for(int i = 0; i < expresions.size(); i++)
            setif += "|"+expresions.get(i)+"|"+values.get(i+1);
        add("SETIF",setif);
    }
    private static void addASETSWITCH(String destend, String switchh, ArrayList<String> expresions, ArrayList<String> values){
        String setswitch = destend+"|"+switchh+"|ELSE|"+values.get(0);
        for(int i = 0; i < expresions.size(); i++)
            setswitch += "|"+expresions.get(i)+"|"+values.get(i+1);
        add("SETSWITCH",setswitch);
    }
    private static void addSEQ(String inner){
        add("SEQ",inner);
    }
    private static String genSEQASIG(String var, String value){
        return "[SASIG:"+var+"|"+value+"]";
    }
    private static String genIFTHEN(String body){
        return "[IFTHEN:"+body+"]";
    }
    private static String genSWITCHCASE(String body){
        return "[SWITCH:"+body+"]";
    }
    private static String genWAITFOR(String expresion){
        return "[WAITFOR:"+expresion+"]";
    }
    private static String genWAITON(String var, int edge){
        return "[WAITON:"+var+"|"+edge+"]";
    }
    
    private static String creator2String(Constructor c){
        if(c == null) return "-1";
        return c.retriveData();
    }
    private static ArrayList<String> creator2String(ArrayList<Constructor> list){
        ArrayList<String> res = new ArrayList<>();
        
        for(Constructor c:list)
            res.add(creator2String(c));
        
        return res;
    }
    
    private static ArrayList<String> seqInterprete(Element e){
        if(e == null) return null;
        ArrayList<String> list;
        
        if(e.getPort(1).isOcupied()){
            list = seqInterprete(e.getPort(1).getDock());
        }else{
            list = new ArrayList<>();
        }
        
        if(e.getClass() == AsignatorSEQ.class){
            if(e.isComplete())
                list.add(genSEQASIG(e.getHole(0).getTip().getVar().name, creator2String(e.getCreator(0))));
        }else if(e.getClass() == IfThen.class){
            IfThen it = (IfThen) e;
            ArrayList<Constructor> conds = it.getConditions();
            ArrayList<Element> insts = it.getInstructions();
            String ifthen = "ELSE|"+Global.concatenate(seqInterprete(insts.get(0)));
            
            for(int i = 1; i < insts.size(); i++){
                if(!conds.get(i-1).isEmpty() && insts.get(i) != null)
                    ifthen += creator2String(conds.get(i-1))+"|"+Global.concatenate(seqInterprete(insts.get(i)));
            }
            
            list.add(genIFTHEN(ifthen));
        }else if(e.getClass() == SwitchCase.class){
            if(!e.getHole(0).isAsigned() || !e.getPort(2).isOcupied()) return list;
            
            SwitchCase sc = (SwitchCase) e;
            ArrayList<Constructor> conds = sc.getConditions();
            ArrayList<Element> insts = sc.getInstructions();
            String switchcase = sc.getHole(0).getTip().getVar().name+"|ELSE|"+Global.concatenate(seqInterprete(insts.get(0)));
            
            for(int i = 1; i < insts.size(); i++){
                if(!conds.get(i-1).isEmpty() && insts.get(i) != null)
                    switchcase += creator2String(conds.get(i-1))+"|"+Global.concatenate(seqInterprete(insts.get(i)));
            }
            
            list.add(genSWITCHCASE(switchcase));
        }else if(e.getClass() == WaitFor.class){
            if(!e.isComplete()) return list;
            list.add(genWAITFOR(creator2String(e.getCreator(0))));
        }else if(e.getClass() == WaitOn.class){
            if(!e.isComplete()) return list;
            list.add(genWAITON(e.getHole(0).getTip().getVar().name,e.getHole(0).getTipType()-29));
        }else if(e.getClass() == ForNext.class){
            //Work in progress
        }
        
        return list;
    }
    
    private static void write(String file){
        //System.out.println(tmp);
        try {
            Files.write(Paths.get(Global.getProyectFolder()+"/"+file+".b0ve"), tmp.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //do stuff
    }
}
