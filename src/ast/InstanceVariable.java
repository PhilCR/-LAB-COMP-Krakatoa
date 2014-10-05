package ast;

public class InstanceVariable extends Variable {

    public InstanceVariable( String name, Type type ) {
        super(name, type);
    }
    
    public void genK(PW pw){
    	super.genK(pw);;
    }

}