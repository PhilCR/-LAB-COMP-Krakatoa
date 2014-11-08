/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */


package ast;

public class InstanceVariable extends Variable {

    public InstanceVariable( String name, Type type, KraClass thisClass ) {
        super(name, type);
        this.thisClass = thisClass;
    }
    
    public void genC(PW pw, boolean staticFlag){
    	Type t = super.getType();
    	if(staticFlag){
    		if(t.getCname().compareTo("int") == 0 || t.getCname().compareTo("char *") == 0 || t.getCname().compareTo("void") == 0)
        		pw.print(t.getCname()+" _static_"+thisClass.getName()+"_"+super.getName());
        	else
        		pw.print(t.getCname()+"* _static_"+thisClass.getName()+"_"+super.getName());
    	}else{
    		if(t.getCname().compareTo("int") == 0 || t.getCname().compareTo("char *") == 0 || t.getCname().compareTo("void") == 0)
        		pw.print(t.getCname()+" _"+thisClass.getName()+"_"+super.getName());
        	else
        		pw.print(t.getCname()+"* _"+thisClass.getName()+"_"+super.getName());
    	}
    	
    }
    
    private KraClass thisClass;
}