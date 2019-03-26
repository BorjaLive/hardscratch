package hardscratch.backend;

import hardscratch.Controller;
import static hardscratch.Global.*;
import hardscratch.elements.piezes.GUIs.SimulateGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VMESS extends Thread{
    
    private String command;
    private int callBack;
    private SimulateGUI element;
    
    public VMESS(String mode, String data, SimulateGUI e){
        command = VMESS_EXE+" "+mode+" "+getProyectFolder()+" "+data.replace("\"", "'");
        if(mode.equals("check") || mode.equals("init"))
            callBack = 1;
        else
            callBack = 2;
        element = e;
    }
    
    @Override
    public void run() {
        Runtime rt = Runtime.getRuntime();
        Process proc;
        String returned = "";
        long time = System.currentTimeMillis();
        
        try {
            System.out.println("MANDO: "+command);
            proc = rt.exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s;
            while ((s = stdInput.readLine()) != null){
                returned += s;
                Thread.sleep(10);
                //System.out.println("TIME: "+(System.currentTimeMillis()-time)/1000f);
                if((System.currentTimeMillis()-time)/1000f > 1){
                    System.out.println("VMESS NO RESPONDIA Y DEJE DE INTENTARLO");
                    proc.destroy();
                    break;
                }
            }
            proc.destroy();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("RECIVO: "+returned);
        
        //returned
        if(callBack == 1)
            element.detectError(returned);
        else if(callBack == 2);
            element.simResoult(returned);
            
        
    }
}
