package hardscratch.backend;

public class Variable {
    
    public String name;
    public int type;
    public int inout;
    public int param;
    
    public Variable(String name, int type, int inout, int param){
        this.name = name;
        this.type = type;
        this.inout = inout;
        this. param = param;
    }
    public Variable(){
        name = "";
        type = -1;
        inout = -1;
        param = -1;
    }
}
