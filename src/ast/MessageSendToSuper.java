/*
 * @author Rodrigo Nascimento de Carvalho 380067
 * @author Philippe César Ramos 380415
 * */

package ast;

public class MessageSendToSuper extends MessageSend { 

	public MessageSendToSuper(Method m, ExprList p, KraClass s){
		this.message = m;
		this.parameters = p;
		this.superClass = s;
	}
	
    public Type getType() { 
        return message.getType();
    }

    public void genC( PW pw, boolean putParenthesis ) {
    	if(putParenthesis)
    		pw.print("(");
        pw.print("_"+superClass.getName()+"_"+message.getName()+"(( "+superClass.getCname()+"*) this" );
        if(parameters != null){
        	pw.print(", ");
        	parameters.genC(pw);
        }
        pw.print(")");
        if(putParenthesis)
    		pw.print(")");
    }
    
    private Method message;
    private ExprList parameters;
    private KraClass superClass;
}