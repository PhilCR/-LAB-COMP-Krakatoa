/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class Variable {

    public Variable( String name, Type type ) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }

    public Type getType() {
        return type;
    }
    
    public void genC(PW pw){
    	if(type.getCname().compareTo("int") == 0 || type.getCname().compareTo("char *") == 0 || type.getCname().compareTo("void") == 0)
    		pw.print(type.getCname()+" _"+name);
    	else
    		pw.print(type.getCname()+"* _"+name);
    }
    
    
    private String name;
    private Type type;
}